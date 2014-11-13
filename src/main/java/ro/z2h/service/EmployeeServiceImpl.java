package ro.z2h.service;

import ro.z2h.dao.EmployeeDao;
import ro.z2h.domain.Employee;
import ro.z2h.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 11/12/2014.
 */
public class EmployeeServiceImpl implements EmployeeService{
    @Override
    public List<Employee> findAllEmployees() {
        Connection connection = DatabaseManager.getConnection("ZTH_23", "passw0rd");
        EmployeeDao ed = new EmployeeDao();
        List<Employee> empl = new ArrayList<Employee>();

        try {
            empl = ed.getAllEmployees(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
       return  empl;
    }
}
