package net.horus.pointage.models;



import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Employes generated by hbm2java
 */
@Entity
@Table(name="employes"
    ,catalog="pointage"
    , uniqueConstraints = {@UniqueConstraint(columnNames="marticule"), @UniqueConstraint(columnNames="email")} 
)
public class Employes  implements java.io.Serializable {


     private Integer id;
     private String marticule;
     private String nom;
     private String prenom;
     private Date dateNaissance;
     private String lieuNaissance;
     private String profession;
     private String service;
     private String email;
     private String adresse;
     private String photo;
     private Set<Groupe> groupes = new HashSet<Groupe>(0);
     private Set<EmployeePointage> employeePointages = new HashSet<EmployeePointage>(0);
     private Set<CardRfid> cardRfids = new HashSet<CardRfid>(0);
     private Set<EmployeSortiePointage> employeSortiePointages = new HashSet<EmployeSortiePointage>(0);

    public Employes() {
    }

	
    public Employes(Integer id) {
        this.id = id;
    }
    public Employes(Integer id, String marticule, String nom, String prenom, Date dateNaissance, String lieuNaissance, String profession, String service, String email, String adresse, String photo, Set<Groupe> groupes, Set<EmployeePointage> employeePointages, Set<CardRfid> cardRfids, Set<EmployeSortiePointage> employeSortiePointages) {
       this.id = id;
       this.marticule = marticule;
       this.nom = nom;
       this.prenom = prenom;
       this.dateNaissance = dateNaissance;
       this.lieuNaissance = lieuNaissance;
       this.profession = profession;
       this.service = service;
       this.email = email;
       this.adresse = adresse;
       this.photo = photo;
       this.groupes = groupes;
       this.employeePointages = employeePointages;
       this.cardRfids = cardRfids;
       this.employeSortiePointages = employeSortiePointages;
    }
   
     @Id 

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    
    @Column(name="marticule", unique=true, length=50)
    public String getMarticule() {
        return this.marticule;
    }
    
    public void setMarticule(String marticule) {
        this.marticule = marticule;
    }

    
    @Column(name="nom", length=50)
    public String getNom() {
        return this.nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }

    
    @Column(name="prenom", length=50)
    public String getPrenom() {
        return this.prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="date_naissance", length=10)
    public Date getDateNaissance() {
        return this.dateNaissance;
    }
    
    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    
    @Column(name="lieu_naissance", length=50)
    public String getLieuNaissance() {
        return this.lieuNaissance;
    }
    
    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    
    @Column(name="profession", length=50)
    public String getProfession() {
        return this.profession;
    }
    
    public void setProfession(String profession) {
        this.profession = profession;
    }

    
    @Column(name="service", length=50)
    public String getService() {
        return this.service;
    }
    
    public void setService(String service) {
        this.service = service;
    }

    
    @Column(name="email", unique=true, length=50)
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    
    @Column(name="adresse", length=50)
    public String getAdresse() {
        return this.adresse;
    }
    
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    
    @Column(name="photo", length=50)
    public String getPhoto() {
        return this.photo;
    }
    
    public void setPhoto(String photo) {
        this.photo = photo;
    }

@ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="employe_groupe", catalog="pointage", joinColumns = { 
        @JoinColumn(name="employe_id", nullable=false, updatable=false) }, inverseJoinColumns = { 
        @JoinColumn(name="groupe_id", nullable=false, updatable=false) })
    public Set<Groupe> getGroupes() {
        return this.groupes;
    }
    
    public void setGroupes(Set<Groupe> groupes) {
        this.groupes = groupes;
    }

@OneToMany(fetch=FetchType.EAGER, mappedBy="employes")
    public Set<EmployeePointage> getEmployeePointages() {
        return this.employeePointages;
    }
    
    public void setEmployeePointages(Set<EmployeePointage> employeePointages) {
        this.employeePointages = employeePointages;
    }

@OneToMany(fetch=FetchType.EAGER, mappedBy="employes")
    public Set<CardRfid> getCardRfids() {
        return this.cardRfids;
    }
    
    public void setCardRfids(Set<CardRfid> cardRfids) {
        this.cardRfids = cardRfids;
    }

@OneToMany(fetch=FetchType.EAGER, mappedBy="employes")
    public Set<EmployeSortiePointage> getEmployeSortiePointages() {
        return this.employeSortiePointages;
    }
    
    public void setEmployeSortiePointages(Set<EmployeSortiePointage> employeSortiePointages) {
        this.employeSortiePointages = employeSortiePointages;
    }

}


