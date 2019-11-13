package ca.bcit.assignment2.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.sql.DataSource;

import ca.bcit.assignment2.model.Credentials;

public class CredentialManager {
    private static final long serialVersionUID = 1L;

    /** dataSource for connection pool on JBoss AS 7 or higher. */
    @Resource(mappedName = "java:jboss/datasources/employeeTimesheet")
    private DataSource ds;

    /**
     * Find Employee record from database.
     * 
     * @param id
     *            primary key for record.
     * @return the Employee record with key = id, null if not found.
     */
    public Credentials find(int id) {
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
                        return new Credentials(result.getInt("CategoryID"),
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
     * Persist Employee record into database. id must be unique.
     * 
     * @param employee
     *            the record to be persisted.
     */
    public void persist(Credentials credential) {
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
     * merge Employee record fields into existing database record.
     * 
     * @param employee
     *            the record to be merged.
     */
    public void merge(Credentials credential) {
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
     * Remove employee from database.
     * 
     * @param employee
     *            record to be removed from database
     */
    public void remove(Credentials credential) {
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
     * Return Employees table as array of Category.
     * 
     * @return Employee[] of all records in Employees table
     */
    public Credentials[] getAll() {
        Connection connection = null;        
        ArrayList<Credentials> categories = new ArrayList<Credentials>();
        Statement stmt = null;
        try {
            try {
                connection = ds.getConnection();
                try {
                    stmt = connection.createStatement();
                    ResultSet result = stmt.executeQuery(
                            "SELECT * FROM Categories ORDER BY CategoryID");
                    while (result.next()) {
                        categories.add(new Credentials(
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

        Credentials[] catarray = new Credentials[categories.size()];
        return categories.toArray(catarray);
    }
}
