package uz.cluster.db.entity.references.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import uz.cluster.db.model.Auditable;
import uz.cluster.db.types.Nls;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.AUDITED)
@Table(name = "r_status")
public class Status extends Auditable {

    @Id
    private String status;

    @Type(type = "uz.cluster.db.types.Nls")
    @Column(columnDefinition = "t_nls", name = "name")
    private Nls name;

    public Status() {
        name = new Nls();
        status = "";
    }
    public Status(String status) {
        name = new Nls();
        this.status = status;
    }
}
