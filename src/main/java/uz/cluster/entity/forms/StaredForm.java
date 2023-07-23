package uz.cluster.entity.forms;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import uz.cluster.entity.auth.User;
import uz.cluster.entity.references.model.Form;
import uz.cluster.entity.Auditable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.AUDITED)
@Table(name = "f_stared_form")
public class StaredForm extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Form form;

    @Column
    private LocalDateTime staredAt;

    @Builder(builderMethodName = "builder")
    public StaredForm(int id, User user, Form form, LocalDateTime staredAt) {
        this.id = id;
        this.user = user;
        this.form = form;
        this.staredAt = staredAt;
    }
}
