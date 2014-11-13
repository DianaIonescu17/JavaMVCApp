package ro.z2h.controller;

import ro.z2h.annotation.MyController;
import ro.z2h.annotation.MyRequestMethod;
import ro.z2h.domain.Department;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 11/11/2014.
 */
@MyController(urlPath = "/department")
public class DepartmentController {
    @MyRequestMethod(urlPath = "/all")
    public List<Department> getAllDepartments(){
       List<Department> allDepartments = new ArrayList<Department>();
       Department d1 = new Department();
        d1.setId(2L);
        d1.setDepartmentName("Solutii");
        Department d2 = new Department();
        d2.setDepartmentName("Solutions");
        d2.setId(3L);
        allDepartments.add(d1);
        allDepartments.add(d2);
        return  allDepartments;
    }
}







