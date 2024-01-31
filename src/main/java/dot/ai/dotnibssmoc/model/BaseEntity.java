package dot.ai.dotnibssmoc.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    protected Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt")
    protected Date createdAt;


    @PrePersist
    public void createdAt(){

        this.createdAt = new Date();
    }

}
