package ca.bcit.assignment2.viewcontroller;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.assignment2.access.EmployeeManager;
import ca.bcit.infosys.employee.Credentials;
import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.employee.EmployeeList;

@Named("employee")
@ApplicationScoped

/**
 * bean that talks to the templates and defines the employee
 */
public class EmployeeController implements EmployeeList {
    
    /** Manager for category objects.*/
    @Inject private EmployeeManager employeeManager;
    
    /**
     * reference to the credentials of the current user
     */
    private Credentials creds = new Credentials();
    
    
    @Override
    public List<Employee> getEmployees() {
        return Arrays.asList(employeeManager.getAll());
    }

    @Override
    public Employee getEmployee(String username) {
        return employeeManager.find(username);
    }

    @Override
    public Map<String, String> getLoginCombos() {
        return credentials;
    }

    @Override
    public Employee getCurrentEmployee() {
        for (Employee emp : employees) {
            if (((EmployeeDetail)emp).isLoggedIn()) {
                return emp;
            }
        }
        return null;
    }

    @Override
    public Employee getAdministrator() {
        return employeeManager.find("admin");
    }
    
    /**
     * login method that logs the user in
     * @return
     */
    public String login() {
        if (verifyUser(creds) == true) {
            String name = creds.getUserName();
            for (Employee emp : employees) {
                if (emp.getUserName().equals(name)) {
                    ((EmployeeDetail)emp).setLoggedIn(true);
                }
            }
            System.out.println("in here");
            return "home";
        } else {
            return "fail";
        }
    }

    @Override
    public boolean verifyUser(Credentials credential) {
        String name = credential.getUserName();
        String password = credential.getPassword();
        System.out.println(password);
        if (credentials.containsKey(name)) {
            return credentials.get(name).equals(password);
        } else {
            return false;
        }
    }

    @Override
    public String logout(Employee employee) {
        ((EmployeeDetail)employee).setLoggedIn(false);
        creds = new Credentials();
        return "login";
    }

    @Override
    public void deleteEmployee(Employee userToDelete) {
        employeeManager.remove(userToDelete);
        credentials.remove(userToDelete.getUserName());
    }

    @Override
    public void addEmployee(Employee newEmployee) {
       employeeManager.persist(newEmployee);
    }
    
    /**
     * creates new employee and adds it to the database
     */
    public void createEmployee() {
        EmployeeDetail empD = new EmployeeDetail();
        empD.setEditable(true);
        addEmployee(empD);
    }

    /**
     * setter for the creds of the current user
     * @param creds
     */
    public void setCreds(Credentials creds) {
        this.creds = creds;
    }
    
    /**
     * getter for the creds of the current user
     * @return
     */
    public Credentials getCreds() {
        return this.creds;
    }
    
    /**
     * getter for the password of the current user, replaces select statement
     * @param em
     * @return
     */
    public String getPassword(Employee em) {
        return credentials.get(em.getUserName());
    }
    
    /**
     * setter for the password once the users changes it, replaces update statement
     * @param old
     * @param newPass
     * @param repeat
     * @return
     */
    public String changePassword(String old, String newPass, String repeat) {
        Employee em = getCurrentEmployee();
        if (creds.getPassword().equals(old)) {
            if (newPass.equals(repeat)) {
                creds.setPassword(newPass);
                credentials.put(em.getUserName(), creds.getPassword());
                return "changePwdSuccess";
            } else {
                return "changePwdFail";
            }
        } else {
            return "changePwdFail";
        }
    }

    /**
     * saves the new employee to the database
     * @param em
     * @param password
     * @return
     */
    public String save(Employee em, String password) {
        if (password.length() < 1 || password == null) {
            System.out.println("password is not legible");
            password = ((EmployeeDetail)em).getCreds().getPassword();
        }
        ((EmployeeDetail)em).setEditable(false);
        credentials.put(em.getUserName(), password);
        
        for(Iterator<Map.Entry<String, String>> it = credentials.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, String> entry = it.next();
            boolean found = false;
            for (Employee e : employees) {
                if (e.getUserName() == entry.getKey()) {
                    found = true;
                }
            }
        }
        return null;
    }

}
