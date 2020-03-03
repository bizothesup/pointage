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
import net.horus.pointage.models.Role;
import net.horus.pointage.models.Services;
import net.horus.pointage.utils.HibernateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author A. TRAORE
 */
@Stateless
public class ServiceDao  implements Serializable{
    private HibernateUtils hibernateUtils;
    
    private Services services;
    
    public  ServiceDao(){
        hibernateUtils = new HibernateUtils();
    }
    
    public Services insertService(Services service) throws NamingException{
        Session  session= this.hibernateUtils.getSession();
        Transaction transaction= null;
        try {transaction= session.beginTransaction();
        session.save(service);
        transaction.commit();
        }catch(HibernateException hibernateException)
        {
            if(transaction != null)
                transaction.rollback();
            throw hibernateException;
        }
        finally{
            
        }
        return service;
    }
    
    
    public Services MiseAjourService(Services services) throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.update(services);
            transaction.commit();       
        }catch(HibernateException hibernateException){
            if(transaction != null)
                transaction.rollback();
            throw hibernateException;
            
        }
        finally{
            
        }
        return services;
    }
    
    public int deleteService(String paramString) throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        int result;
        try{
            transaction = session.beginTransaction();
             result = session.createQuery("delete from services where id=:idService").setString("idService",paramString).executeUpdate();
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
    
    
    public List<Services> selectServices() throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        List listServices;
        try{
            transaction = session.beginTransaction();
             listServices = session.createQuery("from "+Services.class.getName()).list();     
        }
        finally{
            hibernateUtils.closeSession();
        }
        return listServices;
    }
    
    
     public List<SelectItem> selectServicesItems() throws NamingException{
        Session session = this.hibernateUtils.getSession();
        List listServices;
        ArrayList arraylist= new ArrayList();
        try{
             listServices = session.createQuery("from services").list();  
             if(listServices.size()!=0){
                 for (byte b=0; b<listServices.size(); b++)
                     arraylist.add(new SelectItem(((Services)listServices.get(b)).getName()));
             }      
        }
        finally{
            
        }
        return arraylist;
    }
     
     public List<Services> paginate(Filter<Services> filter)  {
        List<Services> pagedServices = new ArrayList<>();
        if(has(filter.getSortOrder()) && !SortOrder.UNSORTED.equals(filter.getSortOrder())) {
            try {
                pagedServices = selectServices().stream().
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
                pagedServices = pagedServices.subList(filter.getFirst(), page > selectServices().size() ? selectServices().size() : page);
            } catch (NamingException e) {
                e.printStackTrace();
            }
            return pagedServices;
        }

        List<Predicate<Services>> predicates = configFilter(filter);

        List<Services> pagedList = null;
        try {
            pagedList = selectServices().stream().filter(predicates
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
    
    private List<Predicate<Services>> configFilter(Filter<Services> filter) {
        List<Predicate<Services>> predicates = new ArrayList<>();
        if (filter.hasParam("id")) {
            Predicate<Services> idPredicate = (Services c) -> c.getId().equals(filter.getParam("id"));
            predicates.add(idPredicate);
        }

        if (has(filter.getEntity())) {
            Services filterEntity = filter.getEntity();


        if (has(filterEntity.getName())) {
             Predicate<Services> NamePredicate = (Services c) -> c.getName().toLowerCase().contains(filterEntity.getName().toLowerCase());
             predicates.add(NamePredicate);
            }
        
    }
        return predicates;
    }
    
    public long count(Filter<Services> filter) throws NamingException {
        return selectServices().stream()
                .filter(configFilter(filter).stream()
                        .reduce(Predicate::or).orElse(t -> true))
                .count();
    }

    public Services findById(Integer id) {
        try {
            return selectServices().stream()
                    .filter(c -> c.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new BusinessException(" User not found with id " + id));
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }  
    
    
    
}
