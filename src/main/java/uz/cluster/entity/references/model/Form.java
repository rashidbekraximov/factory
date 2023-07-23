package uz.cluster.entity.references.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.format.annotation.DateTimeFormat;
import uz.cluster.entity.Auditable;
import uz.cluster.enums.Status;
import uz.cluster.types.Nls;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;


@Getter
@Setter
@Hidden
@Entity
@Table(name = "r_forms")
@Audited(targetAuditMode = RelationTargetAuditMode.AUDITED)
public class Form extends Auditable {

    public static final int FORM_1 = 1;
    public static final int FORM_2 = 2;
    public static final int FORM_3 = 3;
    public static final int FORM_4 = 4;

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private int id;

    @Column(name = "form_number", length = 30)
    private String formNumber;

    @Column(name = "name", columnDefinition = "t_nls")
    private Nls name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "varchar(10) default 'ACTIVE'")
    private Status status = Status.ACTIVE;

    @Column(name = "order_number")
    private int orderNumber;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private Form parentForm;

    @OneToMany(mappedBy = "parentForm", cascade = CascadeType.PERSIST)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Form> childForms;

    @Column(name = "href")
    private String hrefAddress;

    @Transient
    private String uname;

    @Transient
    private String formActiveName;

    @Transient
    private boolean isStarred = false;

    @Transient
    private Integer parentFormId;

    @Transient
    private String searchKey;

    @Transient
    @DateTimeFormat(pattern = "HH:mm  (dd.MM)")
    @JsonFormat(pattern = "HH:mm (dd.MM)")
    private LocalDateTime lastVisited = LocalDateTime.of(2019, Month.JANUARY, 1, 0, 0, 0);

    public Form() {
        this.formNumber = null;
        this.name = getName();
        this.status = null;
    }

    public Form(int id, String formNumber, Nls name, Status status, int order) {
        this.id = id;
        this.formNumber = formNumber;
        this.name = name;
        this.status = status;
        this.orderNumber = order;
    }

    public Form(
            int id,
            String formNumber,
            Nls name,
            Status status,
            int orderNumber,
            Form parentForm,
            List<Form> childForms,
            String hrefAddress,
            String formActiveName,
            LocalDateTime lastVisited,
            boolean isStarred
    ) {
        this.id = id;
        this.formNumber = formNumber;
        this.name = name;
        this.status = status;
        this.orderNumber = orderNumber;
        this.parentForm = parentForm;
        this.childForms = childForms;
        this.hrefAddress = hrefAddress;
        this.formActiveName = formActiveName;
        this.parentFormId = (parentForm == null) ? null : parentForm.getId();
        this.lastVisited = lastVisited;
        this.isStarred = isStarred;
    }

    public Form(
            int id,
            String formNumber,
            Nls name,
            Status status,
            int orderNumber,
            Form parentForm,
            List<Form> childForms,
            String hrefAddress,
            String formActiveName
    ) {
        this.id = id;
        this.formNumber = formNumber;
        this.name = name;
        this.status = status;
        this.orderNumber = orderNumber;
        this.parentForm = parentForm;
        this.childForms = childForms;
        this.hrefAddress = hrefAddress;
        this.formActiveName = formActiveName;
        this.parentFormId = (parentForm == null) ? null : parentForm.getId();
    }

    public Form(
            int id,
            String formNumber,
            Nls name,
            Status status,
            int orderNumber,
            Form parentForm,
            String hrefAddress,
            String formActiveName
    ) {
        this.id = id;
        this.formNumber = formNumber;
        this.name = name;
        this.status = status;
        this.orderNumber = orderNumber;
        this.parentForm = parentForm;
        this.hrefAddress = hrefAddress;
        this.formActiveName = formActiveName;
    }

    public Form(String formNumber, Nls name, Status status, int order) {
        this.formNumber = formNumber;
        this.name = name;
        this.status = status;
        this.orderNumber = order;
    }

    public void copy(Form form) {
        this.id = form.id;
        this.formNumber = form.formNumber;
        this.name = form.name;
        this.status = form.status;
        this.orderNumber = form.orderNumber;
        this.uname = form.uname;
        this.parentForm = form.parentForm;
    }

    public void setSearchKey() {
        this.searchKey = name.getRu() +
                " " + name.getUz_cl() +
                " " + name.getUz_lat();
    }

    public String getSearchKey() {
        return searchKey;
    }
}
