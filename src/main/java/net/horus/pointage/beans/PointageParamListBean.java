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
import net.horus.pointage.dao.PointageParamDao;
import net.horus.pointage.dao.UserDao;
import net.horus.pointage.models.EmployeSortiePointage;
import net.horus.pointage.models.Employes;
import net.horus.pointage.models.PointageParam;
import net.horus.pointage.models.Users;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;


/**
 *
 * @author mbs dev
 */
@Named
@ViewScoped
public class PointageParamListBean implements Serializable {

    @Inject
    private PointageParamDao pointageParamDao;
    private PointageParam pointageParam;
    private Integer id;
    private LazyDataModel<PointageParam> pointageParams;

    private Filter<PointageParam> filter = new Filter<>(new PointageParam());

    private List<PointageParam> selectedPointageParams; //cars selected in checkbox column

    private List<PointageParam> filteredValue;// datatable filteredValue attribute (column filters)
    private String action;

    @PostConstruct
    public void InitPointageParam(){
        pointageParams = new LazyDataModel<PointageParam>() {
            @Override
            public List<PointageParam> load(int first, int pageSize,
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
                List<PointageParam> list = null;
                try {
                    list = pointageParamDao.paginate(filter);
                    setRowCount((int) pointageParamDao.count(filter));
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
            public PointageParam getRowData(String key) {
                return pointageParamDao.findById(new Integer(key));
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

    public PointageParamDao getPointageParamDao() {
        return pointageParamDao;
    }

    public void setPointageParamDao(PointageParamDao pointageParamDao) {
        this.pointageParamDao = pointageParamDao;
    }

    public PointageParam getPointageParam() {
        return pointageParam;
    }

    public void setPointageParam(PointageParam pointageParam) {
        this.pointageParam = pointageParam;
    }

    public LazyDataModel<PointageParam> getPointageParams() {
        return pointageParams;
    }

    public void setPointageParams(LazyDataModel<PointageParam> pointageParams) {
        this.pointageParams = pointageParams;
    }

    public Filter<PointageParam> getFilter() {
        return filter;
    }

    public void setFilter(Filter<PointageParam> filter) {
        this.filter = filter;
    }

    public List<PointageParam> getSelectedPointageParams() {
        return selectedPointageParams;
    }

    public void setSelectedPointageParams(List<PointageParam> selectedPointageParams) {
        this.selectedPointageParams = selectedPointageParams;
    }

    public List<PointageParam> getFilteredValue() {
        return filteredValue;
    }

    public void setFilteredValue(List<PointageParam> filteredValue) {
        this.filteredValue = filteredValue;
    }

  
    

}