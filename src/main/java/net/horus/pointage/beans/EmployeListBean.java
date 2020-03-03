/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.horus.pointage.beans;

import com.github.adminfaces.starter.infra.model.Filter;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import net.horus.pointage.dao.EmployesDao;
import net.horus.pointage.dao.UserDao;
import net.horus.pointage.models.Employes;
import net.horus.pointage.models.Users;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;


/**
 *
 * @author mbs dev
 */
@Named
@ViewScoped
public class EmployeListBean implements Serializable {

    @Inject
    private EmployesDao employesDao;
    private Employes employe;
    private Integer id;
    private LazyDataModel<Employes> employes;

    private Filter<Employes> filter = new Filter<>(new Employes());

    private List<Employes> selectedEmploye; //cars selected in checkbox column

    private List<Employes> filteredValue;// datatable filteredValue attribute (column filters)
    private String action;

    @PostConstruct
    public void InitEmploye(){
        employes = new LazyDataModel<Employes>() {
            @Override
            public List<Employes> load(int first, int pageSize,
                                   String sortField, SortOrder sortOrder,
                                   Map<String, Object> filters) {

                com.github.adminfaces.starter.infra.model.SortOrder order = null;
                if (sortOrder != null) {
                    order = sortOrder.equals(SortOrder.ASCENDING) ? com.github.adminfaces.starter.infra.model.SortOrder.ASCENDING
                            : sortOrder.equals(SortOrder.DESCENDING) ? com.github.adminfaces.starter.infra.model.SortOrder.DESCENDING
                            : com.github.adminfaces.starter.infra.model.SortOrder.UNSORTED;
                }
                filter.setFirst(first).setPageSize(pageSize)
                        .setSortField(sortField).setSortOrder(order)
                        .setParams(filters);
                List<Employes> list = null;
                try {
                    list = employesDao.paginate(filter);
                    setRowCount((int) employesDao.count(filter));
                } catch (NamingException e) {
                    e.printStackTrace();
                }
                return list;
            }

            @Override
            public int getRowCount() {
                return super.getRowCount();
            }

            @Override
            public Employes getRowData(String key) {
                return employesDao.findById(new Integer(key));
            }
        };
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EmployesDao getEmployesDao() {
        return employesDao;
    }

    public void setEmployesDao(EmployesDao employesDao) {
        this.employesDao = employesDao;
    }

    public Employes getEmploye() {
        return employe;
    }

    public void setEmploye(Employes employe) {
        this.employe = employe;
    }

    public LazyDataModel<Employes> getEmployes() {
        return employes;
    }

    public void setEmployes(LazyDataModel<Employes> employes) {
        this.employes = employes;
    }

    public Filter<Employes> getFilter() {
        return filter;
    }

    public void setFilter(Filter<Employes> filter) {
        this.filter = filter;
    }

    public List<Employes> getSelectedEmploye() {
        return selectedEmploye;
    }

    public void setSelectedEmploye(List<Employes> selectedEmploye) {
        this.selectedEmploye = selectedEmploye;
    }

    public List<Employes> getFilteredValue() {
        return filteredValue;
    }

    public void setFilteredValue(List<Employes> filteredValue) {
        this.filteredValue = filteredValue;
    }

    
}
