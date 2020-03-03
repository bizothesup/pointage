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
import net.horus.pointage.models.EmployeSortiePointage;
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
            hibernateUtils.closeSession();
        }
        return result;
    }
    
    
    public List<PointageParam> selectPOintageParam() throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        List listPointageParam;
        try{
            transaction = session.beginTransaction();
             listPointageParam = session.createQuery("from "+PointageParam.class.getName()).list();     
        }
        finally{
            
        }
        return listPointageParam;
        
    }
    
    public List<PointageParam> paginate(Filter<PointageParam> filter)  {
        List<PointageParam> pagedPointageParam = new ArrayList<>();
        if(has(filter.getSortOrder()) && !SortOrder.UNSORTED.equals(filter.getSortOrder())) {
            try {
                pagedPointageParam = selectPOintageParam().stream().
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
                pagedPointageParam = pagedPointageParam.subList(filter.getFirst(), page > selectPOintageParam().size() ? selectPOintageParam().size() : page);
            } catch (NamingException e) {
                e.printStackTrace();
            }
            return pagedPointageParam;
        }

        List<Predicate<PointageParam>> predicates = configFilter(filter);

        List<PointageParam> pagedList = null;
        try {
            pagedList = selectPOintageParam().stream().filter(predicates
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
    
    private List<Predicate<PointageParam>> configFilter(Filter<PointageParam> filter) {
        List<Predicate<PointageParam>> predicates = new ArrayList<>();
        if (filter.hasParam("id")) {
            Predicate<PointageParam> idPredicate = (PointageParam c) -> c.getId().equals(filter.getParam("id"));
            predicates.add(idPredicate);
        }

        if (has(filter.getEntity())) {
            PointageParam filterEntity = filter.getEntity();


        if (has(filterEntity.getHeureDebutService())) {
             Predicate<PointageParam> heureDebutPredicate = (PointageParam c) -> c.getHeureDebutService().equals(filterEntity.getHeureDebutService());
             predicates.add(heureDebutPredicate);
            }
        
        if (has(filterEntity.getHeureFinService())) {
             Predicate<PointageParam> heureFinPredicate = (PointageParam c) -> c.getHeureFinService().equals(filterEntity.getHeureFinService());
             predicates.add(heureFinPredicate);
            }
        if (has(filterEntity.getNombreHeureTravaille())) {
             Predicate<PointageParam> nbHeureTravaillePredicate = (PointageParam c) -> c.getNombreHeureTravaille().equals(filterEntity.getNombreHeureTravaille());
             predicates.add(nbHeureTravaillePredicate);
            }
        }
        return predicates;
    }
    
    public long count(Filter<PointageParam> filter) throws NamingException {
        return selectPOintageParam().stream()
                .filter(configFilter(filter).stream()
                        .reduce(Predicate::or).orElse(t -> true))
                .count();
    }

    public PointageParam findById(Integer id) {
        try {
            return selectPOintageParam().stream()
                    .filter(c -> c.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new BusinessException(" User not found with id " + id));
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
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
