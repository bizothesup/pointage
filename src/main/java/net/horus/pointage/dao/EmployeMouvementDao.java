/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.horus.pointage.dao;

import com.github.adminfaces.starter.infra.model.Filter;
import com.github.adminfaces.starter.infra.model.SortOrder;
import com.github.adminfaces.template.exception.BusinessException;
import static com.github.adminfaces.template.util.Assert.has;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;
import net.horus.pointage.models.EmployeSortiePointage;
import net.horus.pointage.models.Employes;
import net.horus.pointage.models.Role;
import net.horus.pointage.utils.HibernateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author A. TRAORE
 */
@Stateless
public class EmployeMouvementDao  implements Serializable{
    private HibernateUtils hibernateUtils;
    
    private EmployeSortiePointage employeSortiePointage;
    
    public  EmployeMouvementDao(){
        hibernateUtils = new HibernateUtils();
    }
    
    public EmployeSortiePointage insertMouvement(EmployeSortiePointage employeSortiePointage) throws NamingException{
        Session  session= this.hibernateUtils.getSession();
        Transaction transaction= null;
        try {transaction= session.beginTransaction();
        session.save(employeSortiePointage);
        transaction.commit();
        }catch(HibernateException hibernateException)
        {
            if(transaction != null)
                transaction.rollback();
            throw hibernateException;
        }
        finally{
            
        }
        return employeSortiePointage;
    }
    
    
    public EmployeSortiePointage MiseAjourEmployeMouvement(EmployeSortiePointage employeSortiePointage) throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.update(employeSortiePointage);
            transaction.commit();       
        }catch(HibernateException hibernateException){
            if(transaction != null)
                transaction.rollback();
            throw hibernateException;
            
        }
        finally{
            
        }
        return employeSortiePointage;
    }
    
    public int deleteEmployeMouvement(String paramString) throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        int result;
        try{
            transaction = session.beginTransaction();
             result = session.createQuery("delete from employe_sortie_pointage where id=:idEmployeMouvement").setString("idEmployeMouvement",paramString).executeUpdate();
            transaction.commit();       
        }catch(HibernateException hibernateException){
            if(transaction != null)
                transaction.rollback();
            throw hibernateException;
            
        }
        finally{
            
        }
        return result;
    }
    
    
    public List<EmployeSortiePointage> selectEmployeMouvement() throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        List listEmployeMouvement;
        try{
            transaction = session.beginTransaction();
             listEmployeMouvement = session.createQuery("from "+EmployeSortiePointage.class.getName()).list();     
        }
        finally{
            hibernateUtils.closeSession();
        }
        return listEmployeMouvement;
    }
    
    
    public List<EmployeSortiePointage> paginate(Filter<EmployeSortiePointage> filter)  {
        List<EmployeSortiePointage> pagedMouvement = new ArrayList<>();
        if(has(filter.getSortOrder()) && !SortOrder.UNSORTED.equals(filter.getSortOrder())) {
            try {
                pagedMouvement = selectEmployeMouvement().stream().
                        sorted((c1, c2) -> {
                            if (filter.getSortOrder().isAscending()) {
                                return c1.getId().compareTo(c2.getId());
                            } else {
                                return c2.getId().compareTo(c1.getId());
                            }
                        })
                        .collect(Collectors.toList());
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }

        int page = filter.getFirst() + filter.getPageSize();
        if (filter.getParams().isEmpty()) {
            try {
                pagedMouvement = pagedMouvement.subList(filter.getFirst(), page > selectEmployeMouvement().size() ? selectEmployeMouvement().size() : page);
            } catch (NamingException e) {
                e.printStackTrace();
            }
            return pagedMouvement;
        }

        List<Predicate<EmployeSortiePointage>> predicates = configFilter(filter);

        List<EmployeSortiePointage> pagedList = null;
        try {
            pagedList = selectEmployeMouvement().stream().filter(predicates
                    .stream().reduce(Predicate::or).orElse(t -> true))
                    .collect(Collectors.toList());
        } catch (NamingException e) {
            e.printStackTrace();
        }

        if (page < pagedList.size()) {
            pagedList = pagedList.subList(filter.getFirst(), page);
        }

        if (has(filter.getSortField())) {
            pagedList = pagedList.stream().
                    sorted((c1, c2) -> {
                        boolean asc = SortOrder.ASCENDING.equals(filter.getSortOrder());
                        if (asc) {
                            return c1.getId().compareTo(c2.getId());
                        } else {
                            return c2.getId().compareTo(c1.getId());
                        }
                    })
                    .collect(Collectors.toList());
        }
        return pagedList;
    }
    
    private List<Predicate<EmployeSortiePointage>> configFilter(Filter<EmployeSortiePointage> filter) {
        List<Predicate<EmployeSortiePointage>> predicates = new ArrayList<>();
        if (filter.hasParam("id")) {
            Predicate<EmployeSortiePointage> idPredicate = (EmployeSortiePointage c) -> c.getId().equals(filter.getParam("id"));
            predicates.add(idPredicate);
        }

        if (has(filter.getEntity())) {
            EmployeSortiePointage filterEntity = filter.getEntity();


        if (has(filterEntity.getMarticuleEmploye())) {
             Predicate<EmployeSortiePointage> matrPredicate = (EmployeSortiePointage c) -> c.getMarticuleEmploye().toLowerCase().contains(filterEntity.getMarticuleEmploye().toLowerCase());
             predicates.add(matrPredicate);
            }
        
        if (has(filterEntity.getJour())) {
             Predicate<EmployeSortiePointage> jourPredicate = (EmployeSortiePointage c) -> c.getJour().equals(filterEntity.getJour());
             predicates.add(jourPredicate);
            }
        if (has(filterEntity.getNumeroCarte())) {
             Predicate<EmployeSortiePointage> numCardPredicate = (EmployeSortiePointage c) -> c.getNumeroCarte().toLowerCase().contains(filterEntity.getNumeroCarte().toLowerCase());
             predicates.add(numCardPredicate);
            }
        }
        return predicates;
    }
    
    public long count(Filter<EmployeSortiePointage> filter) throws NamingException {
        return selectEmployeMouvement().stream()
                .filter(configFilter(filter).stream()
                        .reduce(Predicate::or).orElse(t -> true))
                .count();
    }

    public EmployeSortiePointage findById(Integer id) {
        try {
            return selectEmployeMouvement().stream()
                    .filter(c -> c.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new BusinessException(" User not found with id " + id));
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }  
    /*
     public List<SelectItem> selectRolesItems() throws NamingException{
        Session session = this.hibernateUtils.getSession();
        List listRole;
        ArrayList arraylist= new ArrayList();
        try{
             listRole = session.createQuery("from role").list();  
             if(listRole.size()!=0){
                 for (byte b=0; b<listRole.size(); b++)
                     arraylist.add(new SelectItem(((Role)listRole.get(b)).getName()));
             }      
        }
        finally{
            
        }
        return arraylist;
    }
    */
    
    
    
}
