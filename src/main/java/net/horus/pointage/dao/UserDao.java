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
public class UserDao  implements Serializable{
    private HibernateUtils hibernateUtils;
    
    private Users users;
    
    public  UserDao(){
        hibernateUtils = new HibernateUtils();
    }
    
    public Users insertUsers(Users users) throws NamingException{
        Session  session= this.hibernateUtils.getSession();
        Transaction transaction= null;
        try {transaction= session.beginTransaction();
        users.setPassword(hibernateUtils.hashPassword(users.getPassword()));
        session.save(users);
        transaction.commit();
        }catch(HibernateException hibernateException)
        {
            if(transaction != null)
                transaction.rollback();
            throw hibernateException;
        }
        finally{
            hibernateUtils.closeSession();
        }
        return users;
    }
    
    
    public Users MiseAjourUsers(Users users) throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            users.setPassword(hibernateUtils.hashPassword(users.getPassword()));
            session.update(users);
            transaction.commit();       
        }catch(HibernateException hibernateException){
            if(transaction != null)
                transaction.rollback();
            throw hibernateException;
            
        }
        finally{
            
        }
        return users;
    }
    
    public int deleteUsers(Integer paramString) throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        int result;
        try{
            transaction = session.beginTransaction();
             result = session.createQuery("delete from users where id=:idUser").setInteger("idUser",paramString).executeUpdate();
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
    
    
    public List<Users> selectUsers() throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        List listUsers;
        try{
            transaction = session.beginTransaction();
             listUsers = session.createQuery("from "+Users.class.getName()).list();     
        }
        finally{
            hibernateUtils.closeSession();
        }
        return listUsers;
    }
    
    
    /* public List<SelectItem> selectUsersItems() throws NamingException{
        Session session = this.hibernateUtils.getSession();
        List listUsers;
        ArrayList arraylist= new ArrayList();
        try{
             listUsers = session.createQuery("from users").list();  
             if(listUsers.size()!=0){
                 for (byte b=0; b<listUsers.size(); b++)
                     arraylist.add(new SelectItem(((Users)listUsers.get(b)).getNom()));
             }      
        }
        finally{
            
        }
        return arraylist;
    }*/

    public List<Users> paginate(Filter<Users> filter) {
         List<Users> pagedUser= new ArrayList<>();
        if(has(filter.getSortOrder()) && !SortOrder.UNSORTED.equals(filter.getSortOrder())) {
            try {
                pagedUser = selectUsers().stream().
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
                pagedUser = pagedUser.subList(filter.getFirst(), page > selectUsers().size() ? selectUsers().size() : page);
            } catch (NamingException e) {
                e.printStackTrace();
            }
            return pagedUser;
        }

        List<Predicate<Users>> predicates = configFilter(filter);

        List<Users> pagedList = null;
        try {
            pagedList = selectUsers().stream().filter(predicates
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
    
    private List<Predicate<Users>> configFilter(Filter<Users> filter) {
        List<Predicate<Users>> predicates = new ArrayList<>();
        if (filter.hasParam("id")) {
            Predicate<Users> idPredicate = (Users c) -> c.getId().equals(filter.getParam("id"));
            predicates.add(idPredicate);
        }

        if (has(filter.getEntity())) {
            Users filterEntity = filter.getEntity();


        if (has(filterEntity.getNom())) {
             Predicate<Users> nomPredicate = (Users c) -> c.getNom().toLowerCase().contains(filterEntity.getNom().toLowerCase());
             predicates.add(nomPredicate);
            }
        
        if (has(filterEntity.getEmail())) {
             Predicate<Users> emailPredicate = (Users c) -> c.getEmail().toLowerCase().contains(filterEntity.getEmail().toLowerCase());
             predicates.add(emailPredicate);
            }
        if (has(filterEntity.getPrenom())) {
             Predicate<Users> prenomPredicate = (Users c) -> c.getPrenom().toLowerCase().contains(filterEntity.getPrenom().toLowerCase());
             predicates.add(prenomPredicate);
            }
        }
        return predicates;
    }
    
    public long count(Filter<Users> filter) throws NamingException {
        return selectUsers().stream()
                .filter(configFilter(filter).stream()
                        .reduce(Predicate::or).orElse(t -> true))
                .count();
    }

    public Users findById(Integer id) {
        try {
            return selectUsers().stream()
                    .filter(c -> c.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new BusinessException(" User not found with id " + id));
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
}
