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
     * Find a Credential record from database.
     * 
     * @param id
     *            primary key for record.
     * @return the Credential record with key = id, null if not found.
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
                            .executeQuery("SELECT * FROM Credentials "
                                    + "where EmpNum = '" + id + "'");
                    if (result.next()) {
                        Credentials cred = new Credentials();
                        cred.setPassword(result.getString("EmpPassword"));
                        cred.setUserName(result.getString("EmpUsername"));
                        return cred;
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
     * Persist Credential record into database. id must be unique.
     * 
     * @param credential
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
                            "INSERT INTO Credentials VALUES (?, ?, ?)");
                    stmt.setInt(1, credential.getEmployee().getEmpNumber());
                    stmt.setString(2, credential.getUserName());
                    stmt.setString(3, credential.getPassword());
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
            System.out.println("Error in persist " + credential);
            ex.printStackTrace();
        }
    }

    /**
     * merge Credential record fields into existing database record.
     * 
     * @param credential
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
                            "UPDATE Credentials SET EmpPassword = ? "
                                    + "WHERE EmpNum =  ?");
                    stmt.setString(1, credential.getPassword());
                    stmt.setInt(2, credential.getEmployee().getEmpNumber());
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
            System.out.println("Error in merge " + credential);
            ex.printStackTrace();
        }
    }

    /**
     * Remove Credential from database.
     * 
     * @param credential
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
                            "DELETE FROM Credentials WHERE EmpNum =  ?");
                    stmt.setInt(1, credential.getEmployee().getEmpNumber());
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
            System.out.println("Error in remove " + credential);
            ex.printStackTrace();
        }
    }

    /**
     * Return Credentials table as array of Credentials.
     * 
     * @return Credentials[] of all records in Credentials table
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
                            "SELECT * FROM Credentials ORDER BY EmpNum");
                    while (result.next()) {
                        categories.add(new Credentials(
                                result.getInt("EmpNum"), 
                                result.getString("EmpUsername"),
                                result.getString("EmpPassword")));
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
