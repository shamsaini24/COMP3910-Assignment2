package ca.bcit.assignment2.model;

import ca.bcit.infosys.employee.Credentials;
import ca.bcit.infosys.employee.Employee;

public class CredentialsModel extends ca.bcit.infosys.employee.Credentials{

    private Employee employee;
    
    public CredentialsModel() {
        super();
        employee = new Employee();
    }
    
    public CredentialsModel(int empNum, String empUsername, String empPassword) {
        super();
        setUserName(empUsername);
        setPassword(empPassword);
        employee = new EmployeeModel();
        employee.setEmpNumber(empNum);//Unsure about how to do this initalization
    }
    
    public CredentialsModel (Credentials cred) {
        setUserName(cred.getUserName());
        setPassword(cred.getPassword());
        employee = new EmployeeModel();
    }
    
    
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeModel employee) {
        this.employee = employee;
    }
}
