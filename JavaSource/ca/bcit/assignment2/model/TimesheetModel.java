package ca.bcit.assignment2.model;

import java.util.ArrayList;
import java.util.Date;
import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.timesheet.TimesheetRow;
/**
 * TimesheetModel that extends Timesheet in order to create the primary key (timesheetId)
 * and link each TimesheetModel with the Employee
 * @author Sham, Kang
 *
 */
public class TimesheetModel extends ca.bcit.infosys.timesheet.Timesheet {

    /*
     * Employee that owns this timesheet
     */
    private Employee employee;
    /*
     * primary key timesheetId
     */
    private int timesheetId;
    
    
    /**
     * Constructing TimesheetModel
     * @param employee Employee
     * @param timesheetId int
     * @param endWeek Date
     */
    public TimesheetModel(int timesheetId, Employee employee, Date endWeek) {
        super(employee, endWeek, new ArrayList<TimesheetRow>());
        this.timesheetId = timesheetId;
        this.employee = employee;
        setEndWeek(endWeek);
    }
    
    public TimesheetModel() {
        super();
        employee = new Employee();
    }
    
    /**
     * @return employee
     */
    public Employee getEmployee() {
        return employee;
    }
    
    /**
     * @param the employee to set employee
     */
    public void setEmployee(EmployeeModel employee) {
        this.employee = employee;
    }
    
    /**
     * 
     * @return the timesheetId 
     */
    public int getTimesheetId() {
        return timesheetId;
    }
    
    /**
     * 
     * @param timesheetId to set the timesheetId
     */
    public void setTimesheetId(int timesheetId) {
        this.timesheetId = timesheetId;
    }
}
