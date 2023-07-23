package uz.cluster.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

//lombok annotations
@Getter
@Setter
//spring auditing annotations
//annotation designates a class whose mapping information is applied to the
//entities that inherit from it. A mapped superclass has no separate table defined
//for it
@MappedSuperclass
//@Audited
//specifies the callback listener classes to be used for an entity or mapped
//superclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {
    //updatable flag helps to avoid the override of
    //column's value during the update operation
    @CreatedBy
    @Hidden
    @JsonIgnore
    @Column(name = "created_by", updatable = false)
    private Integer createdBy;

    //updatable flag helps to avoid the override of
    //column's value during the update operation
    @Hidden
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    @JsonIgnore
    @Column(name = "created_on", updatable = false)
    private LocalDateTime createdOn = LocalDateTime.now();

    @LastModifiedBy
    @Hidden
    @JsonIgnore
    @Column(name = "modified_by")
    private Integer modifiedBy;

    @Hidden
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    @JsonIgnore
    @LastModifiedDate
    @Column(name = "modified_on")
    private LocalDateTime modifiedOn = LocalDateTime.now();

    public Auditable() {
        createdOn = LocalDateTime.now();
        modifiedOn = LocalDateTime.now();
        modifiedBy = Integer.MIN_VALUE;
        createdBy = Integer.MIN_VALUE;
    }
}