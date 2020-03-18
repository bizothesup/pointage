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
import net.horus.pointage.dao.UserDao;
import net.horus.pointage.models.Users;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;


/**
 *
 * @author mbs dev
 */
@Named
@ViewScoped
public class UserListBean implements Serializable {

    @Inject
    private UserDao userDao;
    private Users user;
    private Integer id;
    private LazyDataModel<Users> users;

    private Filter<Users> filter = new Filter<>(new Users());

    private List<Users> selectedUser; //cars selected in checkbox column

    private List<Users> filteredValue;// datatable filteredValue attribute (column filters)
    private String action;

    @PostConstruct
    public void InitUsers(){
        users = new LazyDataModel<Users>() {
            @Override
            public List<Users> load(int first, int pageSize,
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
                List<Users> list = null;
                try {
                    list = userDao.paginate(filter);
                    setRowCount((int) userDao.count(filter));
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
            public Users getRowData(String key) {
                return userDao.findById(new Integer(key));
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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public LazyDataModel<Users> getUsers() {
        return users;
    }

    public void setUsers(LazyDataModel<Users> users) {
        this.users = users;
    }

    public Filter<Users> getFilter() {
        return filter;
    }

    public void setFilter(Filter<Users> filter) {
        this.filter = filter;
    }

    public List<Users> getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(List<Users> selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<Users> getFilteredValue() {
        return filteredValue;
    }

    public void setFilteredValue(List<Users> filteredValue) {
        this.filteredValue = filteredValue;
    }
    
}
