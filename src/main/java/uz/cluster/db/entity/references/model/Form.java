package uz.cluster.db.entity.references.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.format.annotation.DateTimeFormat;
import uz.cluster.db.entity.forms.RecentForm;
import uz.cluster.db.model.Auditable;
import uz.cluster.db.types.Nls;

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
    public static final int FORM_5 = 5;
    public static final int FORM_6 = 6;
    public static final int FORM_7 = 7;
    public static final int FORM_8 = 8;
    public static final int FORM_9 = 9;
    public static final int FORM_10 = 10;
    public static final int FORM_11 = 11;
    public static final int FORM_12 = 12;
    public static final int FORM_13 = 13;
    public static final int FORM_14 = 14;
    public static final int FORM_15 = 15;
    public static final int FORM_16 = 16;
    public static final int FORM_17 = 17;
    public static final int FORM_18 = 18;
    public static final int FORM_19 = 19;
    public static final int FORM_20 = 20;
    public static final int FORM_21 = 21;
    public static final int FORM_22 = 22;
    public static final int FORM_23 = 23;
    public static final int FORM_24 = 24;
    public static final int FORM_25 = 25;
    public static final int FORM_26 = 26;
    public static final int FORM_27 = 27;
    public static final int FORM_28 = 28;
    public static final int FORM_29 = 29;

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private int id;

    @Column(name = "form_number", length = 30)
    private String formNumber;

    @Column(name = "name", columnDefinition = "t_nls")
    private Nls name;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "status")
    private Status status;

    @Column(name = "order_number")
    private int orderNumber;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private Form parentForm;

    @OneToMany(mappedBy = "parentForm", cascade = CascadeType.PERSIST)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Form> childForms;

    @NotAudited
    @JsonIgnore
    @Transient
    @OneToMany(mappedBy = "form",fetch = FetchType.LAZY)
    private List<RecentForm> recentForms;

    @Column(name = "href")
    private String hrefAddress;

    @Transient
    private String uname;

    @Transient
    private String formActiveName;

    @Transient
    private boolean isStarred = false;

    @Transient
    private LocalDateTime starredAt = LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0, 0);

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
            String formActiveName,
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
        this.isStarred = isStarred;
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

    public String getStatusName() {
        return status.getName().getActiveLanguage();
    }

//    public String getFormActiveName() {
//        return name.getActiveLanguage();
//    }

    public void setSearchKey() {
        this.searchKey = name.getRu() +
                " " + name.getUz_cl() +
                " " + name.getUz_lat() +
                " " + name.getEn() +
                " " + name.getQr();
    }

    public String getSearchKey() {
        return searchKey;
    }
}
