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
import net.horus.pointage.models.EmployeSortiePointage;
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
public class EmployeMouvementDao  implements Serializable{
    private HibernateUtils hibernateUtils;
    
    private EmployeSortiePointage employeSortiePointage;
    
    public  EmployeMouvementDao(){
        hibernateUtils = new HibernateUtils();
    }
    
    public EmployeSortiePointage insertMouvement(EmployeSortiePointage employeSortiePointage) throws NamingException{
        Session  session= this.hibernateUtils.getSession();
        Transaction transaction= null;
        try {transaction= session.beginTransaction();
        session.save(employeSortiePointage);
        transaction.commit();
        }catch(HibernateException hibernateException)
        {
            if(transaction != null)
                transaction.rollback();
            throw hibernateException;
        }
        finally{
            
        }
        return employeSortiePointage;
    }
    
    
    public EmployeSortiePointage MiseAjourEmployeMouvement(EmployeSortiePointage employeSortiePointage) throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.update(employeSortiePointage);
            transaction.commit();       
        }catch(HibernateException hibernateException){
            if(transaction != null)
                transaction.rollback();
            throw hibernateException;
            
        }
        finally{
            
        }
        return employeSortiePointage;
    }
    
    public int deleteEmployeMouvement(String paramString) throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        int result;
        try{
            transaction = session.beginTransaction();
             result = session.createQuery("delete from employe_sortie_pointage where id=:idEmployeMouvement").setString("idEmployeMouvement",paramString).executeUpdate();
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
    
    
    public List<EmployeSortiePointage> selectEmployeMouvement() throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        List listEmployeMouvement;
        try{
            transaction = session.beginTransaction();
             listEmployeMouvement = session.createQuery("from employe_sortie_pointage").list();     
        }
        finally{
            
        }
        return listEmployeMouvement;
    }
    
    /*
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
    */
    
    
}
