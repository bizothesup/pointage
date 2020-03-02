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
import javax.faces.model.SelectItem;
import javax.naming.NamingException;
import net.horus.pointage.models.CardRfid;
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
             listCardRfid = session.createQuery("from "+CardRfid.class.getName()).list();     
        }
        finally{
            hibernateUtils.closeSession();
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
            hibernateUtils.closeSession();
        }
        return arraylist;
    }
     
     public List<CardRfid> paginate(Filter<CardRfid> filter) {
         List<CardRfid> pagedCardRfid= new ArrayList<>();
        if(has(filter.getSortOrder()) && !SortOrder.UNSORTED.equals(filter.getSortOrder())) {
            try {
                pagedCardRfid = selectCardRfid().stream().
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
                pagedCardRfid = pagedCardRfid.subList(filter.getFirst(), page > selectCardRfid().size() ? selectCardRfid().size() : page);
            } catch (NamingException e) {
                e.printStackTrace();
            }
            return pagedCardRfid;
        }

        List<Predicate<CardRfid>> predicates = configFilter(filter);

        List<CardRfid> pagedList = null;
        try {
            pagedList = selectCardRfid().stream().filter(predicates
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
    
    private List<Predicate<CardRfid>> configFilter(Filter<CardRfid> filter) {
        List<Predicate<CardRfid>> predicates = new ArrayList<>();
        if (filter.hasParam("id")) {
            Predicate<CardRfid> idPredicate = (CardRfid c) -> c.getId().equals(filter.getParam("id"));
            predicates.add(idPredicate);
        }

        if (has(filter.getEntity())) {
            CardRfid filterEntity = filter.getEntity();


        if (has(filterEntity.getIdentifiantCarte())) {
             Predicate<CardRfid> CardIDPredicate = (CardRfid c) -> c.getIdentifiantCarte().toLowerCase().contains(filterEntity.getIdentifiantCarte().toLowerCase());
             predicates.add(CardIDPredicate);
            }
        
        if (has(filterEntity.getNumeroCarte())) {
             Predicate<CardRfid> CardNumPredicate = (CardRfid c) -> c.getNumeroCarte().toLowerCase().contains(filterEntity.getNumeroCarte().toLowerCase());
             predicates.add(CardNumPredicate);
            }
        if (has(filterEntity.getMaricule())) {
             Predicate<CardRfid> CardMatrPredicate = (CardRfid c) -> c.getMaricule().toLowerCase().contains(filterEntity.getMaricule().toLowerCase());
             predicates.add(CardMatrPredicate);
            }
        }
        return predicates;
    }
    
    public long count(Filter<CardRfid> filter) throws NamingException {
        return selectCardRfid().stream()
                .filter(configFilter(filter).stream()
                        .reduce(Predicate::or).orElse(t -> true))
                .count();
    }

    public CardRfid findById(Integer id) {
        try {
            return selectCardRfid().stream()
                    .filter(c -> c.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new BusinessException(" CardRfid not found with id " + id));
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    
}
