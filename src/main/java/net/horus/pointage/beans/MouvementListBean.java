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
import net.horus.pointage.dao.EmployeMouvementDao;
import net.horus.pointage.dao.EmployesDao;
import net.horus.pointage.dao.UserDao;
import net.horus.pointage.models.EmployeSortiePointage;
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
public class MouvementListBean implements Serializable {

    @Inject
    private EmployeMouvementDao employeMouvementDao;
    private EmployeSortiePointage employeSortiePointage;
    private Integer id;
    private LazyDataModel<EmployeSortiePointage> employeSortiePointages;

    private Filter<EmployeSortiePointage> filter = new Filter<>(new EmployeSortiePointage());

    private List<EmployeSortiePointage> selectedEmployeSortiePointage; //cars selected in checkbox column

    private List<EmployeSortiePointage> filteredValue;// datatable filteredValue attribute (column filters)
    private String action;

    @PostConstruct
    public void InitEmployeMouvementPointage(){
        employeSortiePointages = new LazyDataModel<EmployeSortiePointage>() {
            @Override
            public List<EmployeSortiePointage> load(int first, int pageSize,
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
                List<EmployeSortiePointage> list = null;
                try {
                    list = employeMouvementDao.paginate(filter);
                    setRowCount((int) employeMouvementDao.count(filter));
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
            public EmployeSortiePointage getRowData(String key) {
                return employeMouvementDao.findById(new Integer(key));
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

    public EmployeMouvementDao getEmployeMouvementDao() {
        return employeMouvementDao;
    }

    public void setEmployeMouvementDao(EmployeMouvementDao employeMouvementDao) {
        this.employeMouvementDao = employeMouvementDao;
    }

    public EmployeSortiePointage getEmployeSortiePointage() {
        return employeSortiePointage;
    }

    public void setEmployeSortiePointage(EmployeSortiePointage employeSortiePointage) {
        this.employeSortiePointage = employeSortiePointage;
    }

    public LazyDataModel<EmployeSortiePointage> getEmployeSortiePointages() {
        return employeSortiePointages;
    }

    public void setEmployeSortiePointages(LazyDataModel<EmployeSortiePointage> employeSortiePointages) {
        this.employeSortiePointages = employeSortiePointages;
    }

    public Filter<EmployeSortiePointage> getFilter() {
        return filter;
    }

    public void setFilter(Filter<EmployeSortiePointage> filter) {
        this.filter = filter;
    }

    public List<EmployeSortiePointage> getSelectedEmployeSortiePointage() {
        return selectedEmployeSortiePointage;
    }

    public void setSelectedEmployeSortiePointage(List<EmployeSortiePointage> selectedEmployeSortiePointage) {
        this.selectedEmployeSortiePointage = selectedEmployeSortiePointage;
    }

    public List<EmployeSortiePointage> getFilteredValue() {
        return filteredValue;
    }

    public void setFilteredValue(List<EmployeSortiePointage> filteredValue) {
        this.filteredValue = filteredValue;
    }
    
    

}