package ca.bcit.assignment2.access;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.sql.DataSource;

import ca.bcit.infosys.employee.Employee;


@SessionScoped
public class EmployeeManager implements Serializable{
    private static final long serialVersionUID = 1L;

    /** dataSource for connection pool on JBoss AS 7 or higher. */
    @Resource(mappedName = "java:jboss/datasources/employeeTimesheet")
    private DataSource ds;

    /**
     * Find Category record from database.
     * 
     * @param id
     *            primary key for record.
     * @return the Category record with key = id, null if not found.
     */
    public Employee find(int id) {
        Statement stmt = null;
        Connection connection = null;
        try {
            try {
                connection = ds.getConnection();
                try {
                    stmt = connection.createStatement();
                    ResultSet result = stmt
                            .executeQuery("SELECT * FROM Categories "
                                    + "where categoryID = '" + id + "'");
                    if (result.next()) {
                        return new Employee(result.getInt("CategoryID"),
                                result.getString("CategoryName"));
                    } else {
                        return null;
                    }
                } finally {
                    if (stmt != null) {
                        stmt.close();
                    }

                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in find " + id);
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Persist Category record into database. id must be unique.
     * 
     * @param category
     *            the record to be persisted.
     */
    public void persist(Employee employee) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            try {
                connection = ds.getConnection();
                try {
                    stmt = connection.prepareStatement(
                            "INSERT INTO Categories VALUES (?, ?)");
                    stmt.setInt(1, category.getId());
                    stmt.setString(2, category.getName());
                    stmt.executeUpdate();
                } finally {
                    if (stmt != null) {
                        stmt.close();
                    }
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in persist " + category);
            ex.printStackTrace();
        }
    }

    /**
     * merge Category record fields into existing database record.
     * 
     * @param category
     *            the record to be merged.
     */
    public void merge(Employee employee) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            try {
                connection = ds.getConnection();
                try {
                    stmt = connection.prepareStatement(
                            "UPDATE Categories SET CategoryName = ? "
                                    + "WHERE CategoryID =  ?");
                    stmt.setString(1, category.getName());
                    stmt.setInt(2, category.getId());
                    stmt.executeUpdate();
                } finally {
                    if (stmt != null) {
                        stmt.close();
                    }

                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in merge " + category);
            ex.printStackTrace();
        }
    }

    /**
     * Remove category from database.
     * 
     * @param category
     *            record to be removed from database
     */
    public void remove(Employee employee) {
        Connection connection = null;        
        PreparedStatement stmt = null;
        try {
            try {
                connection = ds.getConnection();
                try {
                    stmt = connection.prepareStatement(
                            "DELETE FROM Categories WHERE CategoryID =  ?");
                    stmt.setInt(1, category.getId());
                    stmt.executeUpdate();
                } finally {
                    if (stmt != null) {
                        stmt.close();
                    }
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in remove " + category);
            ex.printStackTrace();
        }
    }

    /**
     * Return Categories table as array of Category.
     * 
     * @return Category[] of all records in Categories table
     */
    public Employee[] getAll() {
        Connection connection = null;        
        ArrayList<Employee> categories = new ArrayList<Employee>();
        Statement stmt = null;
        try {
            try {
                connection = ds.getConnection();
                try {
                    stmt = connection.createStatement();
                    ResultSet result = stmt.executeQuery(
                            "SELECT * FROM Categories ORDER BY CategoryID");
                    while (result.next()) {
                        categories.add(new Employee(
                                result.getInt("CategoryID"), 
                                result.getString("CategoryName")));
                    }
                } finally {
                    if (stmt != null) {
                        stmt.close();
                    }

                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in getAll");
            ex.printStackTrace();
            return null;
        }

        Employee[] catarray = new Employee[categories.size()];
        return categories.toArray(catarray);
    }
}
