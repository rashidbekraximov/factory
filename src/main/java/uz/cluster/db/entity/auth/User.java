package uz.cluster.db.entity.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.cluster.db.entity.references.model.*;
import uz.cluster.db.model.Auditable;
import uz.cluster.enums.auth.SystemRoleName;
import uz.cluster.util.DateUtil;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Audited(targetAuditMode = RelationTargetAuditMode.AUDITED)
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"login"})})
public class User extends Auditable  implements UserDetails {
    @Hidden
    @Id
    @SequenceGenerator(allocationSize = 1, name = "users_sq", sequenceName = "users_sq")
    @GeneratedValue(generator = "users_sq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private int id;

    @Schema(example = "name", description = "Fill the name")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name", nullable = false)
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

//    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "email_code")
    private String emailCode;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "identity_document_type_id")
//    @Schema(example = "0", defaultValue = "0")
//    private IdentityDocumentType documentType;

    @Column(name = "document_serial_number", unique = true)
    private String documentSerialNumber;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    @Column(name = "birthday")
//    private LocalDate birthday;

//     @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "degree_id")
//    private Degree degree;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "gender_id")
//    private Gender gender;

//    @Schema(example = "0", defaultValue = "0")
//    @ManyToOne
//    @JoinColumn(name = "role_id")
//    @JsonIgnore
//    private Rol-e role;

//    @Column(name = "notes")
//    private String notes;
//
//    @JsonIgnore
//    @OneToOne
//    @JoinColumn(name = "user_image_id")
//    private Attachment userImage;
//
//    @JsonIgnore
//    @OneToOne
//    @JoinColumn(name = "document_image_id")
//    private Attachment documentImage;
//
//    @JsonIgnore
//    @OneToOne
//    @JoinColumn(name = "certificate_image_id")
//    private Attachment certificateImage;

    @Column
    private boolean enabled = true; //Mail checking turned off

    @Column
    private boolean accountNonExpired = true;

    @Column
    private boolean accountNonLocked;

    @Column
    private boolean credentialsNonExpired = true;


    @Enumerated(value = EnumType.STRING)
    @Column(name = "system_role_name")
    SystemRoleName systemRoleName;

    @Column(name = "cluster_id", columnDefinition = " real default 1 ", insertable = true, updatable = false)
    private int clusterId = 1;

    public User(String firstName, String lastName, String middleName, IdentityDocumentType documentType, String documentSerialNumber, LocalDate birthday, Degree degree, String login, String password, String email, String emailCode, Gender gender, Role role, String notes, SystemRoleName systemRoleName, boolean accountNonLocked, int clusterId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
//        this.documentType = documentType;
        this.documentSerialNumber = documentSerialNumber;
//        this.birthday = birthday;
//        this.degree = degree;
        this.login = login;
        this.password = password;
        this.email = email;
        this.emailCode = emailCode;
//        this.gender = gender;
//        this.role = role;
//        this.notes = notes;
        this.systemRoleName = systemRoleName;
        this.accountNonLocked = accountNonLocked;
        this.enabled = false;
        this.clusterId = clusterId;
    }

    public User(
            String firstName,
            String lastName,
            String middleName,
            IdentityDocumentType documentType,
            String documentSerialNumber,
            LocalDate birthday,
            Degree degree,
            String login,
            String password,
            String email,
            String emailCode,
            Gender gender,
            String notes,
            SystemRoleName systemRoleName,
            boolean accountNonLocked,
            int clusterId
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
//        this.documentType = documentType;
        this.documentSerialNumber = documentSerialNumber;
//        this.birthday = birthday;
//        this.degree = degree;
        this.login = login;
        this.password = password;
        this.email = email;
        this.emailCode = emailCode;
//        this.gender = gender;
//        this.notes = notes;
        this.systemRoleName = systemRoleName;
        this.accountNonLocked = accountNonLocked;
        this.enabled = false;
        this.clusterId = clusterId;
    }

    public void copy(User user) {
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.middleName = user.middleName;
//        this.documentType = user.documentType;
        this.documentSerialNumber = user.documentSerialNumber;
//        this.birthday = user.birthday;
//        this.degree = user.degree;
        this.login = user.login;
        this.password = user.password;
        this.email = user.email;
//        this.gender = user.gender;
//        this.role = user.role;
//        this.notes = user.notes;
//        this.clusterId = user.clusterId;
    }

//    @Hidden
//    public String getBirthdayString() {
//        return DateUtil.convertToDateString(birthday);
//    }

//    @Hidden
//    public String getRoleName() {
//        if (role == null)
//            return "";
//        return role.getName();
//    }

//    public int getRoleId() {
//        if (role == null)
//            return 0;
//        return role.getId();
//    }

    public String getFio() {
        return lastName + " " + firstName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.systemRoleName == null) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(SystemRoleName.SYSTEM_ROLE_SUPER_ADMIN.name());
            return Collections.singleton(simpleGrantedAuthority);
        }
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(this.systemRoleName.name());
        return Collections.singleton(simpleGrantedAuthority);
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    public String getAuthority() {
        return null;
    }

}