package net.horus.pointage.models;
// Generated 22 f�vr. 2020 � 01:57:26 by Hibernate Tools 4.3.1


import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * EmployeePointage generated by hbm2java
 */
@Entity
@Table(name="employee_pointage"
    ,catalog="pointage"
)
public class EmployeePointage  implements java.io.Serializable {


     private Integer id;
     private Employes employes;
     private Date dateService;
     private String numeroCarte;
     private String matriculeEmploye;
     private Integer mois;
     private Integer annees;
     private Date heurDebutService;
     private Date heurFinService;
     private boolean heureSupAutorize;

    public EmployeePointage() {
    }

	
    public EmployeePointage(boolean heureSupAutorize) {
        this.heureSupAutorize = heureSupAutorize;
    }
    public EmployeePointage(Employes employes, Date dateService, String numeroCarte, String matriculeEmploye, Integer mois, Integer annees, Date heurDebutService, Date heurFinService, boolean heureSupAutorize) {
       this.employes = employes;
       this.dateService = dateService;
       this.numeroCarte = numeroCarte;
       this.matriculeEmploye = matriculeEmploye;
       this.mois = mois;
       this.annees = annees;
       this.heurDebutService = heurDebutService;
       this.heurFinService = heurFinService;
       this.heureSupAutorize = heureSupAutorize;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="employe_id")
    public Employes getEmployes() {
        return this.employes;
    }
    
    public void setEmployes(Employes employes) {
        this.employes = employes;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="date_service", length=10)
    public Date getDateService() {
        return this.dateService;
    }
    
    public void setDateService(Date dateService) {
        this.dateService = dateService;
    }

    
    @Column(name="numero_carte", length=50)
    public String getNumeroCarte() {
        return this.numeroCarte;
    }
    
    public void setNumeroCarte(String numeroCarte) {
        this.numeroCarte = numeroCarte;
    }

    
    @Column(name="matricule_employe", length=50)
    public String getMatriculeEmploye() {
        return this.matriculeEmploye;
    }
    
    public void setMatriculeEmploye(String matriculeEmploye) {
        this.matriculeEmploye = matriculeEmploye;
    }

    
    @Column(name="mois")
    public Integer getMois() {
        return this.mois;
    }
    
    public void setMois(Integer mois) {
        this.mois = mois;
    }

    
    @Column(name="annees")
    public Integer getAnnees() {
        return this.annees;
    }
    
    public void setAnnees(Integer annees) {
        this.annees = annees;
    }

    @Temporal(TemporalType.TIME)
    @Column(name="heur_debut_service", length=8)
    public Date getHeurDebutService() {
        return this.heurDebutService;
    }
    
    public void setHeurDebutService(Date heurDebutService) {
        this.heurDebutService = heurDebutService;
    }

    @Temporal(TemporalType.TIME)
    @Column(name="heur_fin_service", length=8)
    public Date getHeurFinService() {
        return this.heurFinService;
    }
    
    public void setHeurFinService(Date heurFinService) {
        this.heurFinService = heurFinService;
    }

    
    @Column(name="heure_sup_autorize", nullable=false)
    public boolean isHeureSupAutorize() {
        return this.heureSupAutorize;
    }
    
    public void setHeureSupAutorize(boolean heureSupAutorize) {
        this.heureSupAutorize = heureSupAutorize;
    }




}


