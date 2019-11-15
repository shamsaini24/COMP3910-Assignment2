package ca.bcit.assignment2.model;

import java.math.BigDecimal;

/**
 * TimesheetRowModel that extends TimesheetRow in order to create the primary key (timesheetRowId)
 * and link each TimesheetRowModel with the Timesheet
 * @author Sham, Kang
 *
 */
public class TimesheetRowModel extends ca.bcit.infosys.timesheet.TimesheetRow {

    /*
     * TimesheetModel wons the timesheetrow
     */
    private TimesheetModel timesheetModel;
    /*
     * primary key for each timesheetrow
     */
    private int timesheetRowId;
    /**
     * boolean to edit the users
     */
    private boolean editable;
    /**
     * TimesheetRow Constructor
     * @param id
     * @param timesheetModel
     * @param projectId
     * @param wp
     * @param satHour
     * @param sunHour
     * @param monHour
     * @param tueHour
     * @param wedHour
     * @param thursHour
     * @param friHour
     * @param notes
     */
    public TimesheetRowModel(int id, TimesheetModel timesheetModel, int projectId, String wp,
            BigDecimal satHour, BigDecimal sunHour, BigDecimal monHour, BigDecimal tueHour, BigDecimal wedHour,
            BigDecimal thursHour, BigDecimal friHour, String notes, boolean editable) {
        // call super to construct parent
        super(projectId, wp, new BigDecimal[]{satHour, sunHour, monHour, tueHour, wedHour, thursHour, friHour}, notes);
        this.timesheetRowId = id;
        this.timesheetModel = timesheetModel;
        this.editable = editable;
    }
    /**
     * 
     * @return timesheetModel
     */
    public TimesheetModel getTimesheetModel() {
        return timesheetModel;
    }
    
    /**
     * 
     * @param timesheetModel TimesheetModel
     */
    public void setTimesheetModel(TimesheetModel timesheetModel) {
        this.timesheetModel = timesheetModel;
    }
    
    /**
     * 
     * @return timesheetRowId
     */
    public int getTimesheetRowId() {
        return timesheetRowId;
    }
    
    /**
     * 
     * @param timesheetRowId int
     */
    public void setTimesheetRowId(int timesheetRowId) {
        this.timesheetRowId = timesheetRowId;
    }
    
    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
