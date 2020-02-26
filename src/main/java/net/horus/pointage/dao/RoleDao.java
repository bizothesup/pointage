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
import net.horus.pointage.utils.HibernateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author A. TRAORE
 */
@Stateless
public class RoleDao  implements Serializable{
    private HibernateUtils hibernateUtils;
    
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
            
        }
        return role;
    }
    
    public int deleteRole(String paramString) throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        int result;
        try{
            transaction = session.beginTransaction();
             result = session.createQuery("delete from role where id=:idRole").setString("idRole",paramString).executeUpdate();
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
    
    
    public List<Role> selectRoles() throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        List listRole;
        try{
            transaction = session.beginTransaction();
             listRole = session.createQuery("from role").list();     
        }
        finally{
            
        }
        return listRole;
    }
    
    
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
    
    
    
}
