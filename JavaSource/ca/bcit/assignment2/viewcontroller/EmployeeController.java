package ca.bcit.assignment2.viewcontroller;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.assignment2.access.CredentialManager;
import ca.bcit.assignment2.access.EmployeeManager;
import ca.bcit.assignment2.model.CredentialsModel;
import ca.bcit.assignment2.model.EmployeeModel;
import ca.bcit.infosys.employee.Credentials;
import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.employee.EmployeeList;

@Named("employee")
@SessionScoped

/**
 * bean that talks to the templates and defines the employee
 */
public class EmployeeController implements EmployeeList {
    
    /** Manager for Employee objects.*/
    @Inject private EmployeeManager employeeManager;
    
    /** Manager for Credential objects.*/
    @Inject private CredentialManager credentialManager;
    
    /**
     * reference to the credentials of the current user
     */
    private CredentialsModel creds = new CredentialsModel();
    
    private EmployeeModel currentEmployee = new EmployeeModel();
    
    
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
        return currentEmployee;
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
            currentEmployee = new EmployeeModel(employeeManager.find(name));
            System.out.println("in here");
            currentEmployee.setLoggedIn(true);
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
        Employee loginEmployee = employeeManager.find(name);
        if (credentialManager.find(loginEmployee.getEmpNumber()) != null) {
            return credentialManager.find(loginEmployee.getEmpNumber()).getPassword().equals(password);
        } else {
            return false;
        }
    }

    @Override
    public String logout(Employee employee) {
        currentEmployee.setLoggedIn(false);
        creds = new CredentialsModel();
        return "login";
    }

    @Override
    public void deleteEmployee(Employee userToDelete) {
        employeeManager.remove(userToDelete);
        credentialManager.remove(creds);
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
    public void setCreds(CredentialsModel creds) {
        this.creds = creds;
    }
    
    /**
     * getter for the creds of the current user
     * @return
     */
    public CredentialsModel getCreds() {
        return this.creds;
    }
    
    /**
     * getter for the password of the current user,
     * @param em
     * @return
     */
    public String getPassword(Employee em) {
        return credentialManager.find(em.getEmpNumber()).getPassword();
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
                credentialManager.merge(creds);
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

    public void setCurrentEmployee(EmployeeModel currentEmployee) {
        this.currentEmployee = currentEmployee;
    }
    
    

}