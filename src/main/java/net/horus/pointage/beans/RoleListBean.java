package net.horus.pointage.beans;



import net.horus.pointage.dao.RoleDao;
import net.horus.pointage.models.Role;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SelectableDataModel;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

@Named
@ViewScoped
public class RoleListBean  implements Serializable {

    @Inject
    RoleDao roleDao;

    RoleDataModel roleModel;
    List<Role> roles;
    private Role item;
    private Role[] selectedItems;


    @PostConstruct
    public void InitRole(){
        try {
            roles = roleDao.selectRoles();
            roleModel = new RoleDataModel(roles);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public RoleDataModel getRoleModel() {
        return roleModel;
    }

    public void setRoleModel(RoleDataModel roleModel) {
        this.roleModel = roleModel;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Role getItem() {
        return item;
    }

    public void setItem(Role item) {
        this.item = item;
    }

    public Role[] getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(Role[] selectedItems) {
        this.selectedItems = selectedItems;
    }
}

class RoleDataModel extends LazyDataModel<Role> implements SelectableDataModel<Role> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private List<Role> datasource;

    public RoleDataModel() {
    }

    public RoleDataModel(List<Role> datasource) {
        this.datasource = datasource;
    }

    @Override
    public Role getRowData(String rowKey) {
        for (Role o : datasource) {
            if (o.getId() == (Integer.parseInt(rowKey))) {
                return o;
            }
        }
        return null;
    }

    public Object getRowKey(Role o) {
        return o.getId();
    }

    @Override
    public List<Role> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<Role> data = new ArrayList<Role>();

        //filter
        for (Role o : datasource) {
            boolean match = true;

            for (Iterator<String> it = filters.keySet().iterator(); it.hasNext(); ) {
                try {
                    String filterProperty = it.next();
                    String filterValue = (String) filters.get(filterProperty);

                    if (filterProperty.equals("globalFilter")) {
                        if (filterValue == null || o.getName().toLowerCase().contains(filterValue.toLowerCase())) {
                            match = true;
                        } else {
                            match = false;
                            break;
                        }
                    } else {
                        match = true;
                    }

                } catch (Throwable th) {
                   // JsfUtil.displayError(th);
                    match = false;
                }
            }

            if (match) {
                data.add(o);
            }
        }

        //sort
        if (sortField != null) {
            Collections.sort(data, new RoleLazySorter(sortField, sortOrder));
        }

        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);

        //paginate
        if (dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            } catch (IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        } else {
            return data;
        }
    }
}

class RoleLazySorter implements Comparator<Role> {

    private String sortField;

    private SortOrder sortOrder;

    public RoleLazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public int compare(Role o1, Role o2) {
        try {
            Class clazz1 = o1.getClass();
            Class clazz2 = o2.getClass();

            Field field1 = clazz1.getDeclaredField(this.sortField);
            Field field2 = clazz2.getDeclaredField(this.sortField);

            field1.setAccessible(true);
            field2.setAccessible(true);

            Object value1 = (Object) field1.get(o1);
            Object value2 = (Object) field1.get(o2);

            int value = ((Comparable) value1).compareTo(value2);

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        } catch (Throwable th) {
            //JsfUtil.displayError(th);
            throw new RuntimeException();
        }
    }
}
