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
import net.horus.pointage.models.CardRfid;
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
public class CardRfidDao  implements Serializable{
    private HibernateUtils hibernateUtils;
    
    private CardRfid cardRfid;
    
    public  CardRfidDao(){
        hibernateUtils = new HibernateUtils();
    }
    
    public CardRfid insertCardRfid(CardRfid cardRfid) throws NamingException{
        Session  session= this.hibernateUtils.getSession();
        Transaction transaction= null;
        try {transaction= session.beginTransaction();
        session.save(cardRfid);
        transaction.commit();
        }catch(HibernateException hibernateException)
        {
            if(transaction != null)
                transaction.rollback();
            throw hibernateException;
        }
        finally{
            
        }
        return cardRfid;
    }
    
    
    public CardRfid MiseAjourCardRfid(CardRfid cardRfid) throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.update(cardRfid);
            transaction.commit();       
        }catch(HibernateException hibernateException){
            if(transaction != null)
                transaction.rollback();
            throw hibernateException;
            
        }
        finally{
            
        }
        return cardRfid;
    }
    
    public int deleteCardRfid(String paramString) throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        int result;
        try{
            transaction = session.beginTransaction();
             result = session.createQuery("delete from card_rfid where id=:idCardRfid").setString("idCardRfid",paramString).executeUpdate();
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
    
    
    public List<CardRfid> selectCardRfid() throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        List listCardRfid;
        try{
            transaction = session.beginTransaction();
             listCardRfid = session.createQuery("from card_rfid").list();     
        }
        finally{
            
        }
        return listCardRfid;
    }
    
    
     public List<SelectItem> selectCardRfidItems() throws NamingException{
        Session session = this.hibernateUtils.getSession();
        List listCardRfid;
        ArrayList arraylist= new ArrayList();
        try{
             listCardRfid = session.createQuery("from card_rfid").list();  
             if(listCardRfid.size()!=0){
                 for (byte b=0; b<listCardRfid.size(); b++)
                     arraylist.add(new SelectItem(((CardRfid)listCardRfid.get(b)).getNumeroCarte()));
             }      
        }
        finally{
            
        }
        return arraylist;
    }
    
    
    
}
