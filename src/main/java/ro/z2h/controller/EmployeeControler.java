package ro.z2h.controller;

import ro.z2h.annotation.MyController;
import ro.z2h.annotation.MyRequestMethod;
import ro.z2h.domain.Employee;
import ro.z2h.service.EmployeeServiceImpl;

import java.util.List;

/**
 * Created by user on 11/11/2014.
 */
@MyController(urlPath = "/employee")
public class EmployeeControler {


    @MyRequestMethod(urlPath = "/all")

    public List<Employee> getAllEmployees(){
        /*List<Employee> allEmployees = new ArrayList<Employee>();
      Employee em = new Employee();
        em.setId(1L);
        em.setLastName("Diana");
        Employee em2 = new Employee();
        em2.setLastName("Andreea");
        em2.setId(2L);
        allEmployees.add(em);
        allEmployees.add(em2);
        return allEmployees;
        */
        EmployeeServiceImpl empl = new EmployeeServiceImpl();
        return  empl.findAllEmployees();
    }
    @MyRequestMethod(urlPath = "/one")
    /*public Employee getOneEmployee(){
        Employee oneRandomnEmployee = "OneRandomnEmployee";
        return  oneRandomnEmployee;
    }
    */
    public Employee findOneEmployee(){
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setLastName("Z2H");
        return  employee;

    }
}
