/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.horus.pointage.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;
import net.horus.pointage.models.Role;
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
        session.save(users);
        transaction.commit();
        }catch(HibernateException hibernateException)
        {
            if(transaction != null)
                transaction.rollback();
            throw hibernateException;
        }
        finally{
            
        }
        return users;
    }
    
    
    public Users MiseAjourUsers(Users users) throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
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
    
    public int deleteUsers(String paramString) throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        int result;
        try{
            transaction = session.beginTransaction();
             result = session.createQuery("delete from users where id=:idUser").setString("idUser",paramString).executeUpdate();
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
             listUsers = session.createQuery("from users").list();     
        }
        finally{
            
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
    
    
    
}
