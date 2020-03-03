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
import javax.naming.NamingException;
import net.horus.pointage.models.Employes;
import net.horus.pointage.models.Users;
import net.horus.pointage.utils.HibernateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author A. TRAORE
 */
@Stateless
public class EmployesDao  implements Serializable{
    private HibernateUtils hibernateUtils;
    
    private Employes employes;
    
    public  EmployesDao(){
        hibernateUtils = new HibernateUtils();
    }
    
    public Employes insertEmployes(Employes employes) throws NamingException{
        Session  session= this.hibernateUtils.getSession();
        Transaction transaction= null;
        try {transaction= session.beginTransaction();
        session.save(employes);
        transaction.commit();
        }catch(HibernateException hibernateException)
        {
            if(transaction != null)
                transaction.rollback();
            throw hibernateException;
        }
        finally{
            
        }
        return employes;
    }
    
    
    public Employes MiseAjourEmployes(Employes employes) throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.update(employes);
            transaction.commit();       
        }catch(HibernateException hibernateException){
            if(transaction != null)
                transaction.rollback();
            throw hibernateException;
            
        }
        finally{
            
        }
        return employes;
    }
    
    public int deleteEmploye(String paramString) throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        int result;
        try{
            transaction = session.beginTransaction();
             result = session.createQuery("delete from employes where id=:idEmploye").setString("idEmploye",paramString).executeUpdate();
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
    
    
    public List<Employes> selectEmployes() throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        List listEmployes;
        try{
            transaction = session.beginTransaction();
             listEmployes = session.createQuery("from "+Employes.class.getName()).list();     
        }
        finally{
            hibernateUtils.closeSession();
        }
        return listEmployes;
    } 
    
    public List<Employes> paginate(Filter<Employes> filter)  {
        List<Employes> pagedEmploye = new ArrayList<>();
        if(has(filter.getSortOrder()) && !SortOrder.UNSORTED.equals(filter.getSortOrder())) {
            try {
                pagedEmploye = selectEmployes().stream().
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
                pagedEmploye = pagedEmploye.subList(filter.getFirst(), page > selectEmployes().size() ? selectEmployes().size() : page);
            } catch (NamingException e) {
                e.printStackTrace();
            }
            return pagedEmploye;
        }

        List<Predicate<Employes>> predicates = configFilter(filter);

        List<Employes> pagedList = null;
        try {
            pagedList = selectEmployes().stream().filter(predicates
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
    
    private List<Predicate<Employes>> configFilter(Filter<Employes> filter) {
        List<Predicate<Employes>> predicates = new ArrayList<>();
        if (filter.hasParam("id")) {
            Predicate<Employes> idPredicate = (Employes c) -> c.getId().equals(filter.getParam("id"));
            predicates.add(idPredicate);
        }

        if (has(filter.getEntity())) {
            Employes filterEntity = filter.getEntity();


        if (has(filterEntity.getMarticule())) {
             Predicate<Employes> matrPredicate = (Employes c) -> c.getMarticule().toLowerCase().contains(filterEntity.getMarticule().toLowerCase());
             predicates.add(matrPredicate);
            }
        
        if (has(filterEntity.getNom())) {
             Predicate<Employes> nomPredicate = (Employes c) -> c.getNom().toLowerCase().contains(filterEntity.getNom().toLowerCase());
             predicates.add(nomPredicate);
            }
        if (has(filterEntity.getService())) {
             Predicate<Employes> servicePredicate = (Employes c) -> c.getService().toLowerCase().contains(filterEntity.getService().toLowerCase());
             predicates.add(servicePredicate);
            }
        }
        return predicates;
    }
    
    public long count(Filter<Employes> filter) throws NamingException {
        return selectEmployes().stream()
                .filter(configFilter(filter).stream()
                        .reduce(Predicate::or).orElse(t -> true))
                .count();
    }

    public Employes findById(Integer id) {
        try {
            return selectEmployes().stream()
                    .filter(c -> c.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new BusinessException(" User not found with id " + id));
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }  

}



