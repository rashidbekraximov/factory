package uz.cluster.entity.references.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import uz.cluster.entity.auth.RoleFormPermission;
import uz.cluster.entity.Auditable;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.AUDITED)
@Table(name = "roles")
public class Role extends Auditable {
    /**
     * +1000 to view
     * +100 to insert
     * +10 to edit
     * 1 to delete
     */
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private boolean active;

    @Column
    private String description;

    @Column(name = "cluster_id", columnDefinition = " real default 1 ")
    private int clusterId = 1;

    @OneToMany(  mappedBy = "role", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)//cannot simultaneously fetch multiple that's why added
    private List<RoleFormPermission> roleFormPermissions;

    public Role(String name, boolean active, String description) {
        this.name = name;
        this.active = active;
        this.description = description;
    }

    public Role(String name, boolean active, String description, List<RoleFormPermission> roleFormPermissions) {
        this.name = name;
        this.active = active;
        this.description = description;
        this.roleFormPermissions = roleFormPermissions;
    }
}
