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
public class PointageParamDao  implements Serializable{
    private HibernateUtils hibernateUtils;
    
    private PointageParam pointageParam;
    
    public  PointageParamDao(){
        hibernateUtils = new HibernateUtils();
    }
    
    public PointageParam insertRole(Role role) throws NamingException{
        Session  session= this.hibernateUtils.getSession();
        Transaction transaction= null;
        try {transaction= session.beginTransaction();
        session.save(pointageParam);
        transaction.commit();
        }catch(HibernateException hibernateException)
        {
            if(transaction != null)
                transaction.rollback();
            throw hibernateException;
        }
        finally{
            
        }
        return pointageParam;
    }
    
    
    public PointageParam MiseAjourPointageParam(PointageParam pointageParam) throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.update(pointageParam);
            transaction.commit();       
        }catch(HibernateException hibernateException){
            if(transaction != null)
                transaction.rollback();
            throw hibernateException;
            
        }
        finally{
            
        }
        return pointageParam;
    }
    
    public int deletePointageParam(String paramString) throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        int result;
        try{
            transaction = session.beginTransaction();
             result = session.createQuery("delete from pointage_param where id=:idPointageParam").setString("idPointageParam",paramString).executeUpdate();
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
    
    
    public List<PointageParam> selectPOintageParam() throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        List listPointageParam;
        try{
            transaction = session.beginTransaction();
             listPointageParam = session.createQuery("from pointage_param").list();     
        }
        finally{
            
        }
        return listPointageParam;
    }
    
    
    /* public List<SelectItem> selectRolesItems() throws NamingException{
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
