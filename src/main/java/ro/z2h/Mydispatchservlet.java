package ro.z2h;

import org.codehaus.jackson.map.ObjectMapper;
import ro.z2h.annotation.MyController;
import ro.z2h.annotation.MyRequestMethod;
import ro.z2h.controller.DepartmentController;
import ro.z2h.controller.EmployeeControler;
import ro.z2h.fmk.AnnotationScanUtils;
import ro.z2h.fmk.MethodAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;


/**
 * Created by user on 11/11/2014.
 */
public class MyDispatchServlet extends HttpServlet{
    HashMap<String, MethodAttributes> allowedMethods;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        dispatchReply("GET",req, resp);
    }
    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        dispatchReply("POST",req, resp);
    }
    private void dispatchReply(String httpMethod,HttpServletRequest req, HttpServletResponse resp) throws IOException {
       /*dispatch*/
        Object r = dispatch(httpMethod, req, resp);
        /*reply*/
        reply(r, req, resp);
        /*catch errors*/
        Exception ex = null;
        sendException(ex, req, resp);
    }

    private void sendException (Exception ex, HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("There was an exception");
    }

    /*used to send the view to the client */


    @Override
    public void init() throws ServletException {

        try {
            Iterable<Class> classes = AnnotationScanUtils.getClasses("ro.z2h.controller");
            allowedMethods = new HashMap<String, MethodAttributes>();
            for (Class aClass : classes) {
                if (aClass.isAnnotationPresent(MyController.class)){
                  MyController annotation =(MyController)aClass.getAnnotation(MyController.class);
                    System.out.println(annotation.urlPath());
            Method[] methods1 = aClass.getMethods();

                    for (Method method : methods1) {

                       if (method.isAnnotationPresent(MyRequestMethod.class)){
                           MyRequestMethod methodd = (MyRequestMethod)method.getAnnotation(MyRequestMethod.class);
                           System.out.println(methodd.urlPath());
                           String urlKey = annotation.urlPath() +methodd.urlPath();
                           MethodAttributes mAttr = new MethodAttributes();
                           mAttr.setMethodName(method.getName());
                           mAttr.setControllerClass(aClass.getName());
                           mAttr.setMethodType(methodd.methodTYPE());
                           allowedMethods.put(urlKey, mAttr);

                       }
                    }

                }
                System.out.println(allowedMethods);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /* where an application controller should be called*/
    private  Object dispatch(String httpmethod, HttpServletRequest req, HttpServletResponse resp) {
        /* ptr /test  = "Hello" */
        /* ptr /employee => allEmployees de la Application Controller */
        String pathInfo = req.getPathInfo();
        /*if (pathInfo.startsWith("/employee")) {
          EmployeeControler controler =   new EmployeeControler();
            return  controler.getAllEmployees();
        }
        else if (pathInfo.startsWith("/departments")){
            DepartmentController departmentController = new DepartmentController();
           return departmentController.getAllDepartments();
        }
        return  "Hello";
        */
        //controllerul si metoda care vor realiza procesarea sa fie preluate din Map-al allowedMethods
        MethodAttributes val = allowedMethods.get(pathInfo);
        if (val != null){
            // creem o instanta de controler

            try {
                // obtinem clasa pe baza numelui controlerului
                Class<?> controllerClass = Class.forName(val.getControllerClass());
                // creem o instanta de controler pentru clasa obtinuta
                Object controllerInstance = controllerClass.newInstance();
               // obtinem metoda pe baza numelui si clasei din care  face parte
                Method method = controllerClass.getMethod(val.getMethodName());
                // apelam metoda pe instanta

                return  method.invoke(controllerInstance);


            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
       return  null;
    }
    private void reply(Object r, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();

        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(r);
        writer.printf(s);
    }


}


