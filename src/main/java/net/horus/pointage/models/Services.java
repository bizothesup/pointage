package net.horus.pointage.models;
// Generated 25 f�vr. 2020 � 09:43:02 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Services generated by hbm2java
 */
@Entity
@Table(name="services"
    ,catalog="pointage"
)
public class Services  implements java.io.Serializable {


     private Integer id;
     private String name;

    public Services() {
    }

    public Services(String name) {
       this.name = name;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    
    @Column(name="name", length=50)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }




}


