/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.horus.pointage.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

import com.github.adminfaces.starter.infra.model.Filter;
import com.github.adminfaces.starter.infra.model.SortOrder;
import com.github.adminfaces.starter.model.Car;
import com.github.adminfaces.template.exception.BusinessException;
import net.horus.pointage.models.Role;
import net.horus.pointage.utils.HibernateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import static com.github.adminfaces.template.util.Assert.has;

/**
 *
 * @author A. TRAORE
 */
@Stateless
public class RoleDao  implements Serializable{
    private final HibernateUtils hibernateUtils;
    
   
    private Role role;
    
    public  RoleDao(){
        hibernateUtils = new HibernateUtils();
    }
    
    public Role insertRole(Role role) throws NamingException{
        Session  session= this.hibernateUtils.getSession();
        Transaction transaction= null;
        try {transaction= session.beginTransaction();
        session.save(role);
        transaction.commit();
        }catch(HibernateException hibernateException)
        {
            if(transaction != null)
                transaction.rollback();
            throw hibernateException;
        }
        finally{
            this.hibernateUtils.closeSession();
        }
        return role;
    }
    
    
    public Role MiseAjourRole(Role role) throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.update(role);
            transaction.commit();       
        }catch(HibernateException hibernateException){
            if(transaction != null)
                transaction.rollback();
            throw hibernateException;
            
        }
        finally{
            this.hibernateUtils.closeSession();
        }
        return role;
    }
    
    public int deleteRole(Integer paramString) throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        int result;
        try{
            transaction = session.beginTransaction();
             result = session.createQuery("delete from "+Role.class.getName()+" where id=:id").setInteger("id",paramString).executeUpdate();
            transaction.commit();       
        }catch(HibernateException hibernateException){
            if(transaction != null)
                transaction.rollback();
            throw hibernateException;
            
        }
        finally{
            this.hibernateUtils.closeSession();
        }
        return result;
    }
    
    
    public List<Role> selectRoles() throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        List listRole;
        try{
            transaction = session.beginTransaction();
             listRole = session.createQuery("from "+Role.class.getName()).list();     
        }
        finally{
            this.hibernateUtils.closeSession();
        }
        return listRole;
    }
    
    
     public List<SelectItem> selectRolesItems() throws NamingException{
        Session session = this.hibernateUtils.getSession();
        List listRole;
        ArrayList arraylist= new ArrayList();
        try{
             listRole = session.createQuery("from "+Role.class.getName()).list();  
             if(listRole.size()!=0){
                 for (byte b=0; b<listRole.size(); b++)
                     arraylist.add(new SelectItem(((Role)listRole.get(b)).getName()));
             }      
        }
        finally{
            this.hibernateUtils.closeSession();
        }
        return arraylist;
    }

    public List<Role> paginate(Filter<Role> filter)  {
        List<Role> pagedRole= new ArrayList<>();
        if(has(filter.getSortOrder()) && !SortOrder.UNSORTED.equals(filter.getSortOrder())) {
            try {
                pagedRole = selectRoles().stream().
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
                pagedRole = pagedRole.subList(filter.getFirst(), page > selectRoles().size() ? selectRoles().size() : page);
            } catch (NamingException e) {
                e.printStackTrace();
            }
            return pagedRole;
        }

        List<Predicate<Role>> predicates = configFilter(filter);

        List<Role> pagedList = null;
        try {
            pagedList = selectRoles().stream().filter(predicates
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
    private List<Predicate<Role>> configFilter(Filter<Role> filter) {
        List<Predicate<Role>> predicates = new ArrayList<>();
        if (filter.hasParam("id")) {
            Predicate<Role> idPredicate = (Role c) -> c.getId().equals(filter.getParam("id"));
            predicates.add(idPredicate);
        }


        if (has(filter.getEntity())) {
            Role filterEntity = filter.getEntity();


            if (has(filterEntity.getName())) {
                Predicate<Role> namePredicate = (Role c) -> c.getName().toLowerCase().contains(filterEntity.getName().toLowerCase());
                predicates.add(namePredicate);
            }
        }
        return predicates;
    }
    public long count(Filter<Role> filter) throws NamingException {
        return selectRoles().stream()
                .filter(configFilter(filter).stream()
                        .reduce(Predicate::or).orElse(t -> true))
                .count();
    }

    public Role findById(Integer id) {
        try {
            return selectRoles().stream()
                    .filter(c -> c.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new BusinessException("Role not found with id " + id));
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
