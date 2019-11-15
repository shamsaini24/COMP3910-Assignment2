package ca.bcit.assignment2.viewcontroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.assignment2.access.TimesheetManager;
import ca.bcit.assignment2.access.TimesheetRowManager;
import ca.bcit.assignment2.model.TimesheetModel;
import ca.bcit.assignment2.model.TimesheetRowModel;
import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.timesheet.Timesheet;
import ca.bcit.infosys.timesheet.TimesheetCollection;
import ca.bcit.infosys.timesheet.TimesheetRow;

@Named("timesheet")
@SessionScoped
/**
 * Bean that talks to the templates and defines the timesheet
 * @author Sham, Kang
 *
 */
public class TimesheetController{
    
    /** Manager for Timesheet objects.*/
    @Inject private TimesheetManager timesheetManager;
    
    /** Manager for TimesheetRow objects.*/
    @Inject private TimesheetRowManager timesheetRowManager;
    
    /** Timesheets */
    List<TimesheetModel> timesheetList;
    
    /** Timesheets */
    List<TimesheetRowModel> timesheetRowList;

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
        TimesheetModel[] tsArr = timesheetManager.getByEmployee(e.getEmpNumber());
        for(TimesheetModel ts: tsArr) {
            if(ts.getEmployee().getEmpNumber() == e.getEmpNumber()) {
                timesheetList.add(ts);
            }
        }
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
    
    public List<TimesheetRowModel> getTimesheetRows(TimesheetModel ts) {
        TimesheetRowModel[] trArr = timesheetRowManager.getByTimesheet(ts.getTimesheetId());
        for(TimesheetRowModel tr: trArr) {
            if(tr.getTimesheetModel().getTimesheetId() == ts.getTimesheetId()) {
                timesheetRowList.add(tr);
            }
        }
        return timesheetRowList;
    }
    
}
