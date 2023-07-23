package uz.cluster.db.entity.references.model;


import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import uz.cluster.db.entity.references.abstract_.AbstractReferenceModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.AUDITED)
@Table(name = "r_gender")
public class Gender extends AbstractReferenceModel {

}