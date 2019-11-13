package ca.bcit.assignment2.model;

public class Employee extends ca.bcit.infosys.employee.Employee{
    
    /**
     * The argument-containing constructor. Used to create the initial employees
     * who have access as well as the administrator.
     *
     * @param empName the name of the employee.
     * @param number the empNumber of the user.
     * @param id the loginID of the user.
     */
    public Employee(final String empName, final int number, final String id) {
        super(empName, number, id);
    }
}
