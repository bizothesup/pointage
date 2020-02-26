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
             listServices = session.createQuery("from services").list();     
        }
        finally{
            
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
    
    
    
}
