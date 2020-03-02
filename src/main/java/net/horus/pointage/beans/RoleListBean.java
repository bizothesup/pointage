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


@Named
@ViewScoped
public class RoleListBean  implements Serializable {

    @Inject
    RoleDao roleDao;
    private Role role;
    Integer id;
    LazyDataModel<Role> roles;

    Filter<Role> filter = new Filter<>(new Role());

    List<Car> selectedRole; //cars selected in checkbox column

    List<Car> filteredValue;// datatable filteredValue attribute (column filters)
    private String action;

    @PostConstruct
    public void InitRole(){
        roles = new LazyDataModel<Role>() {
            @Override
            public List<Role> load(int first, int pageSize,
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
                List<Role> list = null;
                try {
                    list = roleDao.paginate(filter);
                    setRowCount((int) roleDao.count(filter));
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
            public Role getRowData(String key) {
                return roleDao.findById(new Integer(key));
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LazyDataModel<Role> getRoles() {
        return roles;
    }

    public void setRoles(LazyDataModel<Role> roles) {
        this.roles = roles;
    }

    public Filter<Role> getFilter() {
        return filter;
    }

    public void setFilter(Filter<Role> filter) {
        this.filter = filter;
    }

    public List<Car> getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(List<Car> selectedRole) {
        this.selectedRole = selectedRole;
    }

    public List<Car> getFilteredValue() {
        return filteredValue;
    }

    public void setFilteredValue(List<Car> filteredValue) {
        this.filteredValue = filteredValue;
    }
}

