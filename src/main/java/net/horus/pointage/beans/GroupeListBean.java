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
import net.horus.pointage.dao.GroupeDao;
import net.horus.pointage.models.Groupe;


@Named
@ViewScoped
public class GroupeListBean  implements Serializable {

    @Inject
    GroupeDao groupeDao;
    private Groupe groupe;
    Integer id;
    LazyDataModel<Groupe> groupes;

    Filter<Groupe> filter = new Filter<>(new Groupe());

    List<Groupe> selectedGroupes; //cars selected in checkbox column

    List<Groupe> filteredValue;// datatable filteredValue attribute (column filters)
    private String action;

    @PostConstruct
    public void InitGroupe(){
        groupes = new LazyDataModel<Groupe>() {
            @Override
            public List<Groupe> load(int first, int pageSize,
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
                List<Groupe> list = null;
                try {
                    list = groupeDao.paginate(filter);
                    setRowCount((int) groupeDao.count(filter));
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
            public Groupe getRowData(String key) {
                return groupeDao.findById(new Integer(key));
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

    public GroupeDao getGroupeDao() {
        return groupeDao;
    }

    public void setGroupeDao(GroupeDao groupeDao) {
        this.groupeDao = groupeDao;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public LazyDataModel<Groupe> getGroupes() {
        return groupes;
    }

    public void setGroupes(LazyDataModel<Groupe> groupes) {
        this.groupes = groupes;
    }

    public Filter<Groupe> getFilter() {
        return filter;
    }

    public void setFilter(Filter<Groupe> filter) {
        this.filter = filter;
    }

    public List<Groupe> getSelectedGroupes() {
        return selectedGroupes;
    }

    public void setSelectedGroupes(List<Groupe> selectedGroupes) {
        this.selectedGroupes = selectedGroupes;
    }

    public List<Groupe> getFilteredValue() {
        return filteredValue;
    }

    public void setFilteredValue(List<Groupe> filteredValue) {
        this.filteredValue = filteredValue;
    }

   
}

