package uz.cluster.entity.forms;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import uz.cluster.entity.Auditable;
import uz.cluster.entity.references.model.Measurement;
import uz.cluster.entity.references.model.MechanicalProduct;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.AUDITED)
@Table(name = "spare_part",schema = "forms")
public class SparePart extends Auditable {

    @Hidden
    @Id
    @GeneratedValue(generator = "spare_part_sq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "spare_part_sq", sequenceName = "spare_part_sq", allocationSize = 1)
    @JsonProperty("formId")
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private long id;

    @Hidden
    @Column(name = "document_id", updatable = false)
    private long documentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mechanical_product_id")
    private MechanicalProduct mechanicalProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "measurement_id")
    private Measurement measurement;

    @Column(name = "weight")
    private double weight;

    @Column(name = "price")
    private double price;

    @Column(name = "amount")
    private double amount;

    @Column(name = "value_added_tax")
    private double valueAddedTax;

    @JsonProperty
    @Column(name = "is_calc_value_added_tax")
    private boolean isCalcValueAddedTax;

    @Column(name = "all_amount")
    private double allAmount;

    @Transient
    private int measurementId;

    @Transient
    private int mechanicalProductId;
}
