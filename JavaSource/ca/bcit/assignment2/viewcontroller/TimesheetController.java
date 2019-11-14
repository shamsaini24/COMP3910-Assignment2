package ca.bcit.assignment2.viewcontroller;

import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.assignment2.access.TimesheetManager;
import ca.bcit.assignment2.access.TimesheetRowManager;
import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.timesheet.Timesheet;
import ca.bcit.infosys.timesheet.TimesheetCollection;

@Named("timesheet")
@SessionScoped
/**
 * Bean that talks to the templates and defines the timesheet
 * @author Sham, Kang
 *
 */
public class TimesheetController implements TimesheetCollection{
    
    /** Manager for Timesheet objects.*/
    @Inject private TimesheetManager timesheetManager;
    
    /** Manager for TimesheetRow objects.*/
    @Inject private TimesheetRowManager timesheetRowManager;
    
    /** Timesheets */
    List<TimesheetManager> timesheetList;
    /** Timesheet Details */
    List<TimesheetRowManager> timesheetRowlist;

    /**
     * timesheets getter.
     * @return all of the timesheets.
     */
    @Override
    public List<Timesheet> getTimesheets() {
        return Arrays.asList((Timesheet[])timesheetManager.getAll());
    }

    /**
     * get all timesheets for an employee.
     * @param e the employee whose timesheets are returned
     * @return all of the timesheets for an employee.
     */
    @Override
    public List<Timesheet> getTimesheets(Employee e) {
        System.out.println("getting timesheets");
        return Arrays.asList((Timesheet[])timesheetManager.getByEmployee(e.getEmpNumber()));
    }

    /**
     * get current timesheet for an employee.
     * @param e the employee whose current timesheet is returned
     * @return the current timesheet for an employee.
     */
    @Override
    public Timesheet getCurrentTimesheet(Employee e) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Creates a Timesheet object and adds it to the collection.
     *
     * @return a String representing navigation to the newTimesheet page.
     */
    @Override
    public String addTimesheet() {
        // TODO Auto-generated method stub
        return null;
    }
    /**
     * Get the last index of timesheet list
     * @return the last index of timesheet list
     */
    public int getLastIndex() {
        if(timesheetList != null)
            return timesheetList.size() -1;
        return 0;
    }
    
}
