package uz.cluster.entity.references.abstract_;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import uz.cluster.entity.Auditable;
import uz.cluster.types.Nls;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@Audited(targetAuditMode = RelationTargetAuditMode.AUDITED)
public abstract class  AbstractReferenceModel extends Auditable {

    @Id
    @Hidden
    @GeneratedValue
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private int id;

    @Type(type = "uz.cluster.types.Nls")
    @Column(columnDefinition = "t_nls", name = "name")
    private Nls name;

    @Column(name = "status")
    private String status;

    public AbstractReferenceModel(Nls name, String status) {
        this.name = name;
        this.status = status;
    }

    public AbstractReferenceModel(int id, Nls name) {
        this.id = id;
        this.name = name;
    }
}
