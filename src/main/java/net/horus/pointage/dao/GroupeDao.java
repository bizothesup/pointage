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
import net.horus.pointage.models.Groupe;
import net.horus.pointage.models.PointageParam;
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
public class GroupeDao  implements Serializable{
    private HibernateUtils hibernateUtils;
    
    private Groupe groupe;
    
    public  GroupeDao(){
        hibernateUtils = new HibernateUtils();
    }
    
    public Groupe insertGroupe(Groupe groupe) throws NamingException{
        Session  session= this.hibernateUtils.getSession();
        Transaction transaction= null;
        try {transaction= session.beginTransaction();
        session.save(groupe);
        transaction.commit();
        }catch(HibernateException hibernateException)
        {
            if(transaction != null)
                transaction.rollback();
            throw hibernateException;
        }
        finally{
            
        }
        return groupe;
    }
    
    
    public Groupe MiseAjourGroupe(Groupe groupe) throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.update(groupe);
            transaction.commit();       
        }catch(HibernateException hibernateException){
            if(transaction != null)
                transaction.rollback();
            throw hibernateException;
            
        }
        finally{
            
        }
        return groupe;
    }
    
    public int deleteGroupe(String paramString) throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        int result;
        try{
            transaction = session.beginTransaction();
             result = session.createQuery("delete from groupe where id=:idGroupe").setString("idGroupe",paramString).executeUpdate();
            transaction.commit();       
        }catch(HibernateException hibernateException){
            if(transaction != null)
                transaction.rollback();
            throw hibernateException;
            
        }
        finally{
            hibernateUtils.closeSession();
        }
        return result;
    }
    
    
    public List<Groupe> selectGroupes() throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        List listGroupe;
        try{
            transaction = session.beginTransaction();
             listGroupe = session.createQuery("from "+ Groupe.class.getName()).list();     
        }
        finally{
            hibernateUtils.closeSession();
        }
        return listGroupe;
    }
    
    
     public List<SelectItem> selectGroupesItems() throws NamingException{
        Session session = this.hibernateUtils.getSession();
        List listGroupe;
        ArrayList arraylist= new ArrayList();
        try{
             listGroupe = session.createQuery("from groupe").list();  
             if(listGroupe.size()!=0){
                 for (byte b=0; b<listGroupe.size(); b++)
                     arraylist.add(new SelectItem(((Groupe)listGroupe.get(b)).getName()));
             }      
        }
        finally{
            hibernateUtils.closeSession();
        }
        return arraylist;
    }
     
     public List<Groupe> paginate(Filter<Groupe> filter)  {
        List<Groupe> pagedGroupe = new ArrayList<>();
        if(has(filter.getSortOrder()) && !SortOrder.UNSORTED.equals(filter.getSortOrder())) {
            try {
                pagedGroupe = selectGroupes().stream().
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
                pagedGroupe = pagedGroupe.subList(filter.getFirst(), page > selectGroupes().size() ? selectGroupes().size() : page);
            } catch (NamingException e) {
                e.printStackTrace();
            }
            return pagedGroupe;
        }

        List<Predicate<Groupe>> predicates = configFilter(filter);

        List<Groupe> pagedList = null;
        try {
            pagedList = selectGroupes().stream().filter(predicates
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
    
    private List<Predicate<Groupe>> configFilter(Filter<Groupe> filter) {
        List<Predicate<Groupe>> predicates = new ArrayList<>();
        if (filter.hasParam("id")) {
            Predicate<Groupe> idPredicate = (Groupe c) -> c.getId().equals(filter.getParam("id"));
            predicates.add(idPredicate);
        }

        if (has(filter.getEntity())) {
            Groupe filterEntity = filter.getEntity();


        if (has(filterEntity.getName())) {
             Predicate<Groupe> NamePredicate = (Groupe c) -> c.getName().toLowerCase().contains(filterEntity.getName().toLowerCase());
             predicates.add(NamePredicate);
            }
        
    }
        return predicates;
    }
    
    public long count(Filter<Groupe> filter) throws NamingException {
        return selectGroupes().stream()
                .filter(configFilter(filter).stream()
                        .reduce(Predicate::or).orElse(t -> true))
                .count();
    }

    public Groupe findById(Integer id) {
        try {
            return selectGroupes().stream()
                    .filter(c -> c.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new BusinessException(" User not found with id " + id));
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }  
    
    
    
    
}
