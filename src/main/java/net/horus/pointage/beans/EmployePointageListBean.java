package net.horus.pointage.beans;



import com.github.adminfaces.starter.infra.model.Filter;
import com.github.adminfaces.starter.model.Car;
import net.horus.pointage.dao.RoleDao;
import net.horus.pointage.models.Role;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.*;
import net.horus.pointage.dao.EmployePointageDao;
import net.horus.pointage.dao.GroupeDao;
import net.horus.pointage.models.EmployeePointage;
import net.horus.pointage.models.Groupe;


@Named
@ViewScoped
public class EmployePointageListBean  implements Serializable {

    @Inject
    EmployePointageDao employePointageDao;
    private EmployeePointage employeePointage;
    Integer id;
    LazyDataModel<EmployeePointage> employeePointages;

    Filter<EmployeePointage> filter = new Filter<>(new EmployeePointage());

    List<EmployeePointage> selectedEmployeePointages; //cars selected in checkbox column

    List<EmployeePointage> filteredValue;// datatable filteredValue attribute (column filters)
    private String action;

    @PostConstruct
    public void InitGroupe(){
        employeePointages = new LazyDataModel<EmployeePointage>() {
            @Override
            public List<EmployeePointage> load(int first, int pageSize,
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
                List<EmployeePointage> list = null;
                try {
                    list = employePointageDao.paginate(filter);
                    setRowCount((int) employePointageDao.count(filter));
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
            public EmployeePointage getRowData(String key) {
                return employePointageDao.findById(new Integer(key));
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

    public EmployePointageDao getEmployePointageDao() {
        return employePointageDao;
    }

    public void setEmployePointageDao(EmployePointageDao employePointageDao) {
        this.employePointageDao = employePointageDao;
    }

    public EmployeePointage getEmployeePointage() {
        return employeePointage;
    }

    public void setEmployeePointage(EmployeePointage employeePointage) {
        this.employeePointage = employeePointage;
    }

    public LazyDataModel<EmployeePointage> getEmployeePointages() {
        return employeePointages;
    }

    public void setEmployeePointages(LazyDataModel<EmployeePointage> employeePointages) {
        this.employeePointages = employeePointages;
    }

    public Filter<EmployeePointage> getFilter() {
        return filter;
    }

    public void setFilter(Filter<EmployeePointage> filter) {
        this.filter = filter;
    }

    public List<EmployeePointage> getSelectedEmployeePointages() {
        return selectedEmployeePointages;
    }

    public void setSelectedEmployeePointages(List<EmployeePointage> selectedEmployeePointages) {
        this.selectedEmployeePointages = selectedEmployeePointages;
    }

    public List<EmployeePointage> getFilteredValue() {
        return filteredValue;
    }

    public void setFilteredValue(List<EmployeePointage> filteredValue) {
        this.filteredValue = filteredValue;
    }

    
   
}

