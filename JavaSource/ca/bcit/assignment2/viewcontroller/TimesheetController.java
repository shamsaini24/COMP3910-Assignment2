package ca.bcit.assignment2.viewcontroller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.assignment2.access.TimesheetManager;
import ca.bcit.assignment2.model.TimesheetModel;
import ca.bcit.infosys.employee.Employee;

@Named("timesheet")
@SessionScoped
/**
 * Bean that talks to the templates and defines the timesheet
 * @author Sham, Kang
 *
 */
public class TimesheetController implements Serializable{
    private static final long serialVersionUID = 1L;

    /** Manager for Timesheet objects.*/
    @Inject private TimesheetManager timesheetManager;
    
    
    
    /** Timesheets */
    List<TimesheetModel> timesheetList;
    
    
    

    /**
     * timesheets getter.
     * @return all of the timesheets.
     */
    public List<TimesheetModel> getTimesheets() {
        return Arrays.asList(timesheetManager.getAll());
    }

    /**
     * get all timesheets for an employee.
     * @param e the employee whose timesheets are returned
     * @return all of the timesheets for an employee.
     */
    public List<TimesheetModel> getTimesheets(Employee e) {
        timesheetList = new ArrayList<TimesheetModel>();
        TimesheetModel[] tsArr = timesheetManager.getByEmployee(e.getEmpNumber());
        for(TimesheetModel ts: tsArr) {
            if(ts.getEmployee().getEmpNumber() == e.getEmpNumber()) {
                System.out.println(ts.getWeekEnding() + " " + ts.getWeekNumber());
                timesheetList.add(ts);
            }
        }
        System.out.println(timesheetList.get(0).getTimesheetId());
        return timesheetList;
    }
    /**
     * get current timesheet for an employee.
     * @param e the employee whose current timesheet is returned
     * @return the current timesheet for an employee.
     */
    public TimesheetModel getCurrentTimesheet(Employee e) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Creates a Timesheet object and adds it to the collection.
     *
     * @return a String representing navigation to the newTimesheet page.
     */
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
