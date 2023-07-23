package uz.cluster.entity.forms;

import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import uz.cluster.entity.auth.User;
import uz.cluster.entity.references.model.Form;
import uz.cluster.entity.Auditable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.AUDITED)
@Table(name = "f_recent_form")
public class RecentForm extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private User user;

    @NotAudited
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne
    private Form form;

    @Column
    private LocalDateTime visitedTime;

    @Column
    private int numberOfVisit;

}
