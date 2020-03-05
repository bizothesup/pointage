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
import net.horus.pointage.models.EmployeePointage;
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
public class EmployePointageDao  implements Serializable{
    private HibernateUtils hibernateUtils;
    
    private EmployeePointage employeePointage;
    
    public  EmployePointageDao(){
        hibernateUtils = new HibernateUtils();
    }
    
    public EmployeePointage insertEmployePointage(EmployeePointage employeePointage) throws NamingException{
        Session  session= this.hibernateUtils.getSession();
        Transaction transaction= null;
        try {transaction= session.beginTransaction();
        session.save(employeePointage);
        transaction.commit();
        }catch(HibernateException hibernateException)
        {
            if(transaction != null)
                transaction.rollback();
            throw hibernateException;
        }
        finally{
            
        }
        return employeePointage;
    }
    
    
    public EmployeePointage MiseAjourPointage(EmployeePointage employeePointage) throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.update(employeePointage);
            transaction.commit();       
        }catch(HibernateException hibernateException){
            if(transaction != null)
                transaction.rollback();
            throw hibernateException;
            
        }
        finally{
            
        }
        return employeePointage;
    }
    
    public int deleteEmployePointage(String paramString) throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        int result;
        try{
            transaction = session.beginTransaction();
             result = session.createQuery("delete from employee_pointage where id=:idEmployePointage").setString("idEmployePointage",paramString).executeUpdate();
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
    
    
    public List<EmployeePointage> selectEmployeePointages() throws NamingException{
        Session session = this.hibernateUtils.getSession();
        Transaction transaction = null;
        List listEmployeePointage;
        try{
            transaction = session.beginTransaction();
             listEmployeePointage = session.createQuery("from "+EmployeePointage.class.getName()).list();     
        }
        finally{
            hibernateUtils.closeSession();
        }
        return listEmployeePointage;
    }
    
    
    public List<EmployeePointage> paginate(Filter<EmployeePointage> filter)  {
        List<EmployeePointage> pagedEmployeePointage = new ArrayList<>();
        if(has(filter.getSortOrder()) && !SortOrder.UNSORTED.equals(filter.getSortOrder())) {
            try {
                pagedEmployeePointage = selectEmployeePointages().stream().
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
                pagedEmployeePointage = pagedEmployeePointage.subList(filter.getFirst(), page > selectEmployeePointages().size() ? selectEmployeePointages().size() : page);
            } catch (NamingException e) {
                e.printStackTrace();
            }
            return pagedEmployeePointage;
        }

        List<Predicate<EmployeePointage>> predicates = configFilter(filter);

        List<EmployeePointage> pagedList = null;
        try {
            pagedList = selectEmployeePointages().stream().filter(predicates
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

    private List<Predicate<EmployeePointage>> configFilter(Filter<EmployeePointage> filter) {
        List<Predicate<EmployeePointage>> predicates = new ArrayList<>();
        if (filter.hasParam("id")) {
            Predicate<EmployeePointage> idPredicate = (EmployeePointage c) -> c.getId().equals(filter.getParam("id"));
            predicates.add(idPredicate);
        }

        if (has(filter.getEntity())) {
            EmployeePointage filterEntity = filter.getEntity();


            if (has(filterEntity.getMatriculeEmploye())) {
                Predicate<EmployeePointage> matriculeEmployePredicate = (EmployeePointage c) -> c.getMatriculeEmploye().toLowerCase().contains(filterEntity.getMatriculeEmploye().toLowerCase());
                predicates.add(matriculeEmployePredicate);
            }
            if (has(filterEntity.getDateService())) {
                Predicate<EmployeePointage> dateServicePredicate = (EmployeePointage c) -> c.getDateService().equals(filterEntity.getDateService());
                predicates.add(dateServicePredicate);
            }
            if (has(filterEntity.getNumeroCarte())) {
                Predicate<EmployeePointage> numCartePredicate = (EmployeePointage c) -> c.getNumeroCarte().toLowerCase().contains(filterEntity.getNumeroCarte().toLowerCase());
                predicates.add(numCartePredicate);
            }
            if (has(filterEntity.getMois())) {
                Predicate<EmployeePointage> moisPredicate = (EmployeePointage c) -> c.getMois().equals(filterEntity.getMois());
                predicates.add(moisPredicate);
            }
            if (has(filterEntity.getAnnees())) {
                Predicate<EmployeePointage> anneePredicate = (EmployeePointage c) -> c.getAnnees().equals(filterEntity.getAnnees());
                predicates.add(anneePredicate);
            }

        }
        return predicates;
    }

    public long count(Filter<EmployeePointage> filter) throws NamingException {
        return selectEmployeePointages().stream()
                .filter(configFilter(filter).stream()
                        .reduce(Predicate::or).orElse(t -> true))
                .count();
    }

    public EmployeePointage findById(Integer id) {
        try {
            return selectEmployeePointages().stream()
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
    }
    */
    
    
}
