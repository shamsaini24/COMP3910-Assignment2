package ca.bcit.assignment2.model;

import ca.bcit.infosys.employee.Credentials;
import ca.bcit.infosys.employee.Employee;

public class EmployeeModel extends ca.bcit.infosys.employee.Employee{
    /**
     * boolean to tell us if the employee is admin
     */
    private boolean admin;
    
    /**
     * boolean that tells us if the employee is logged in
     */
    private boolean loggedIn;
    
    /**
     * boolean to edit the users
     */
    private boolean editable;
    
    Credentials creds = new Credentials();
    /**
     * The argument-containing constructor. Used to create the initial employees
     * who have access as well as the administrator.
     *
     * @param empName the name of the employee.
     * @param number the empNumber of the user.
     * @param id the loginID of the user.
     */
    public EmployeeModel(final String empName, final int number, final String id) {
        super(empName, number, id);
        if(id.equals("admin")) {
            setAdmin(true);
        }
        setLoggedIn(false);
        setEditable(false);
    }
    
    public EmployeeModel () {
        super();
        setAdmin(false);
        setLoggedIn(false);
        setEditable(false);
    }
    
    public EmployeeModel (Employee emp) {
        super(emp.getName(), emp.getEmpNumber(), emp.getUserName());
        setAdmin(false);
        setLoggedIn(false);
        setEditable(false);
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public Credentials getCreds() {
        return creds;
    }

    public void setCreds(Credentials creds) {
        this.creds = creds;
    }
    
    
}
