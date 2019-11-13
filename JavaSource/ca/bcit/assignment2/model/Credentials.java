package ca.bcit.assignment2.model;

public class Credentials extends ca.bcit.infosys.employee.Credentials{


    private Employee employee;
    
    public Credentials() {
        super();
        employee = new Employee();
    }
    
    public Credentials(int empNum, String empUsername, String empPassword) {
        super();
        setUserName(empUsername);
        setPassword(empPassword);
        employee = new Employee();
        employee.setEmpNumber(empNum);//Unsure about how to do this initalization
        }
    
    
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
