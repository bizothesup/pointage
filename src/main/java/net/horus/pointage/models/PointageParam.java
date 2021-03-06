package net.horus.pointage.models;



import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PointageParam generated by hbm2java
 */
@Entity
@Table(name="pointage_param"
    ,catalog="pointage"
)
public class PointageParam  implements java.io.Serializable {


     private Integer id;
     private Date heureDebutService;
     private Date heureFinService;
     private Integer nombreHeureTravaille;

    public PointageParam() {
    }

    public PointageParam(Date heureDebutService, Date heureFinService, Integer nombreHeureTravaille) {
       this.heureDebutService = heureDebutService;
       this.heureFinService = heureFinService;
       this.nombreHeureTravaille = nombreHeureTravaille;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    @Temporal(TemporalType.TIME)
    @Column(name="heure_debut_service", length=8)
    public Date getHeureDebutService() {
        return this.heureDebutService;
    }
    
    public void setHeureDebutService(Date heureDebutService) {
        this.heureDebutService = heureDebutService;
    }

    @Temporal(TemporalType.TIME)
    @Column(name="heure_fin_service", length=8)
    public Date getHeureFinService() {
        return this.heureFinService;
    }
    
    public void setHeureFinService(Date heureFinService) {
        this.heureFinService = heureFinService;
    }

    
    @Column(name="nombre_heure_travaille")
    public Integer getNombreHeureTravaille() {
        return this.nombreHeureTravaille;
    }
    
    public void setNombreHeureTravaille(Integer nombreHeureTravaille) {
        this.nombreHeureTravaille = nombreHeureTravaille;
    }




}


