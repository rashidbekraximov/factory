package uz.cluster.entity;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;
import org.hibernate.envers.*;
import uz.cluster.entity.references.model.CostGroup;
import uz.cluster.util.*;

import javax.persistence.*;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "documents")
@Audited(targetAuditMode = RelationTargetAuditMode.AUDITED)
public class  Document extends Auditable {

    @Hidden
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documents_sq")
    @SequenceGenerator(sequenceName = "documents_sq", name = "documents_sq", allocationSize = 1)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private long documentId;

    @Column(name = "document_code")
    private String documentCode;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "document_date")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDate documentDate;

    @JsonProperty("formTypeId")
    @Column(name = "form_id", updatable = false)
    private int formId;

    @Hidden
    @Column(name = "amount")
    private double amount;

    @Hidden
    @Column(name = "value_added_tax")
    private double valueAddedTax;

    @Hidden
    @Column(name = "usd_amount")
    private double usdAmount;

    @Hidden
    @Column(name = "usd_course", updatable = false)
    private double usdCourse;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cost_group_id", referencedColumnName = "id")
    private CostGroup costGroup;

    @Column(name = "season_id")
    private int seasonId;

    @Column(name = "department_id")
    private int departmentId;

    @JsonIgnore
    @Column(name = "factory_id", columnDefinition = " real default 1 ")
    private int factoryId = GlobalParams.getCurrentClusterId();

    @Transient
    private String formName;

    @Transient
    private int costGroupId;

    public Document(String documentCode, LocalDate documentDate, int seasonId,int formId,
                     CostGroup costGroup, int departmentId, double amount, double valueAddedTax, String description) {
        this.documentCode = documentCode;
        this.documentDate = documentDate;
        this.seasonId = seasonId;
        this.formId = formId;
        this.costGroup = costGroup;
        this.departmentId = departmentId;
        this.amount = amount;
        this.usdCourse = GlobalParams.getUsdCourse();
        this.valueAddedTax = valueAddedTax;
        this.description = description;
        this.factoryId =  GlobalParams.getCurrentClusterId();
    }


    public Document(Document document) {
        if (document == null)
            return;
        init(document);

    }
    private void init(Document document)
    {
        this.documentId = document.getDocumentId();
        this.documentCode = document.documentCode;
        this.documentDate = document.documentDate;
        this.seasonId = document.seasonId;
        this.costGroupId = document.costGroupId;
        this.costGroup = document.costGroup;
        this.formId = document.formId;
        this.amount = document.amount;
        this.valueAddedTax = document.valueAddedTax;
        this.factoryId = GlobalParams.getCurrentClusterId();

        if (document.usdCourse == 0) {
            this.usdCourse = GlobalParams.getUsdCourse();
            calcUsdAmount();
        } else {
            this.usdCourse = document.usdCourse;
            this.usdAmount = document.usdAmount;
        }
        this.description = document.description;

        if (!"".equals(document.formName))
            formName = document.formName;
    }
    public Document(Document document,int formId,double amount,double valueAddedTax) {
        document.formId = formId;
        document.amount =amount;
        document.valueAddedTax = valueAddedTax;
        document.factoryId =  GlobalParams.getCurrentClusterId();
        init(document);
    }

    public Document(int formTypeId) {
        this.formId = formTypeId;
    }

    private void calcUsdAmount() {
        this.usdAmount = ( Math.round((100 * 100 * (this.amount + this.valueAddedTax)) / usdCourse)) / 100;
    }

    public String getDocumentDateString() {
        return DateUtil.convertToDateString(documentDate);
    }

    public String getCreatedOnString() {
        return DateUtil.convertToDateTimeString(getCreatedOn());
    }

    @Transient
    public void setFormName(String formName, int formOrder) {
        if (formName.length() > 20) {
            this.formName = formOrder + ". " + formName.substring(0, 20) + "...";
        } else
            this.formName = formOrder + ". " + formName;
    }
}