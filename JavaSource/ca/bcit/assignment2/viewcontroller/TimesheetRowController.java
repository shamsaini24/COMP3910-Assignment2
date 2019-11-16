package ca.bcit.assignment2.viewcontroller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.assignment2.access.TimesheetRowManager;
import ca.bcit.assignment2.model.TimesheetModel;
import ca.bcit.assignment2.model.TimesheetRowModel;

@Named("timesheetrow")
@SessionScoped
public class TimesheetRowController implements Serializable{
    private static final long serialVersionUID = 1L;

    
    /** Manager for TimesheetRow objects.*/
    @Inject private TimesheetRowManager timesheetRowManager;
    
    /** Timesheets */
    List<TimesheetRowModel> timesheetRowList;
    
    
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
}
