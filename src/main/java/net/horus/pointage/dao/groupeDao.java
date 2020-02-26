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
import net.horus.pointage.models.Groupe;
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
public class groupeDao  implements Serializable{
    private HibernateUtils hibernateUtils;
    
    private Groupe groupe;
    
    public  groupeDao(){
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
            
        }
        return result;
    }
    
    
    public List<Groupe> selectGroupes() throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        List listGroupe;
        try{
            transaction = session.beginTransaction();
             listGroupe = session.createQuery("from groupe").list();     
        }
        finally{
            
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
            
        }
        return arraylist;
    }
    
    
    
}
