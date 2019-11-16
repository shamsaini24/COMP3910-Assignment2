package ca.bcit.assignment2.viewcontroller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.assignment2.access.TimesheetManager;
import ca.bcit.assignment2.access.TimesheetRowManager;
import ca.bcit.assignment2.model.TimesheetModel;
import ca.bcit.assignment2.model.TimesheetRowModel;
import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.timesheet.TimesheetRow;

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
    
    /** Number of days in a week. */
    public static final int DAYS_IN_WEEK = 7;
    
    /** Default 5 rows when creating new timesheet. */
    public static final int DEFAULT_ROW = 5;
    
    /** Timesheets */
    List<TimesheetModel> timesheetList;
    
    /** Manager for TimesheetRow objects.*/
    @Inject private TimesheetRowManager timesheetRowManager;
    
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
        System.out.println("getting current timesheet");
        TimesheetModel t = new TimesheetModel();
        for(int i = 0; i < timesheetList.size(); i++) {
            if(calculateCurrentEndWeek() == timesheetList.get(i).getEndWeek()) {
                t = timesheetList.get(i);
                System.out.println("found current timesheet");
            }
        }
        t.setEditable(true);
        return t;
    }

    /**
     * Creates a Timesheet object and adds it to the collection.
     *
     * @return a String representing navigation to the newTimesheet page.
     */
    public String addTimesheet(Employee e) {
        boolean found = false;
        for(int i = 0; i < timesheetList.size(); i++) {
            if(calculateCurrentEndWeek() == timesheetList.get(i).getEndWeek()) {
                found = true;
                System.out.println("current week timesheet already exist");
            }
        }
        if(!found) {
            int newsheetId = timesheetManager.getAll().length;
            int newrowId = timesheetRowManager.getAll().length;
            TimesheetModel t = new TimesheetModel(newsheetId, e, calculateCurrentEndWeek(), true);
            timesheetManager.persist(t);
            timesheetList.add(t);
            // adding 5 empty rows
            for(int i = 0; i < DEFAULT_ROW; i++) {
                TimesheetRowModel r = new TimesheetRowModel((newrowId + i), t, 0, "", null, null, null, null, null, null, null, "");
                timesheetRowManager.persist(r);
                timesheetRowList.add(r);
            }
            System.out.println("Add current timesheet");
        }
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
    
    /**
     * Getting the timesheetRowModel relative to the specific timesheetModel
     * @param ts
     * @return TimesheetRows
     */
    public List<TimesheetRowModel> getTimesheetRows(TimesheetModel ts) {
        timesheetRowList = new ArrayList<TimesheetRowModel>();
        int id = ts.getTimesheetId();
        TimesheetRowModel[] trArr = timesheetRowManager.getByTimesheet(id);
        for(TimesheetRowModel tr: trArr) {
            if(tr.getTimesheetModel().getTimesheetId() == ts.getTimesheetId()) {
                timesheetRowList.add(tr);
            }
        }
        return timesheetRowList;
    }
    
    /**
     * Calculates the daily hours.
     *
     * @return array of total hours for each day of week for timesheet.
     */
    public BigDecimal[] getDailyHours() {
        BigDecimal[] sums = new BigDecimal[DAYS_IN_WEEK];
        for (TimesheetRowModel day : timesheetRowList) {
            BigDecimal[] hours = day.getHoursForWeek();
            for (int i = 0; i < DAYS_IN_WEEK; i++) {
                if (hours[i] != null) {
                    if (sums[i] == null) {
                        sums[i] = hours[i];
                    } else {
                        sums[i] = sums[i].add(hours[i]);
                    }
                }
            }
        }
        return sums;
    }
    
    /**
     * Calculates the total hours.
     *
     * @return total hours for timesheet.
     */
    public BigDecimal getTotalHours() {
        BigDecimal sum = BigDecimal.ZERO;
        for (TimesheetRowModel row : timesheetRowList) {
            sum = sum.add(row.getSum());
        }
        return sum;
    }
    /**
     * Calculates the current, real time end week.
     */
    public Date calculateCurrentEndWeek() {
        final Calendar c = new GregorianCalendar();
        final int currentDay = c.get(Calendar.DAY_OF_WEEK);
        final int leftDays = Calendar.FRIDAY - currentDay;
        c.add(Calendar.DATE, leftDays);
        return c.getTime();
    }
    
    /**
     * add a row on current timesheet
     */
    public void addRow(Employee e) {
        int newrowId = timesheetRowManager.getAll().length;
        TimesheetRowModel r = new TimesheetRowModel(newrowId, getCurrentTimesheet(e), 0, "", null, null, null, null, null, null, null, "");
        timesheetRowManager.persist(r);
        timesheetRowList.add(r);
    }
    
}
