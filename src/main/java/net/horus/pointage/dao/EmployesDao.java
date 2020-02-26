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
             listEmployes = session.createQuery("from employes").list();     
        }
        finally{
            
        }
        return listEmployes;
    }
    
    
     /*public List<SelectItem> selectRolesItems() throws NamingException{
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
    }*/
    
    
    
}
