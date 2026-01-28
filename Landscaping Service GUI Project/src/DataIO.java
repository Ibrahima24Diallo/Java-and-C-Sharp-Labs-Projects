
import java.sql.*;
import java.util.ArrayList;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Ibrahim Diallo
 */

/*  In Module 6, we focus on reading from and writing to databases. 
    This includes writing data from the GUI to a database and reading data 
    from the database to display it in the GUI application. Additionally, 
    we use a three-tier architecture to manage large development projects efficiently.
*/

public class DataIO {
// Creating the add( ) method so we can write the Customer object to the file
// Now update and set up the DataIO class to read and write to the database.

    // Creating the constants
    private final String DATABASE_NAME = "cis355a";
    private final String CONNECTION_STRING
            = "jdbc:mysql://localhost:3306/" + DATABASE_NAME;
    private final String USER_NAME = "root";
    private final String PASSWORD = "devry123";

    // Behaviors
    public void add(Customer cust) throws ClassNotFoundException, SQLException {
        //check for driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        //connect to database 
        Connection conn = DriverManager.getConnection(CONNECTION_STRING,
                USER_NAME, PASSWORD);

        //add record 
        String strSQL = "INSERT INTO landscape (CustomerName, CustomerAddress, "
                + "LandscapeType, YardLength, YardWidth, LandscapeCost) "
                + "VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(strSQL);
        pstmt.setString(1, cust.getName());
        pstmt.setString(2, cust.getAddress());
        pstmt.setString(3, cust.getYardType());
        pstmt.setDouble(4, cust.getLength());
        pstmt.setDouble(5, cust.getWidth());
        pstmt.setDouble(6, cust.getTotalCost());

        // execute the prepared statement
        pstmt.execute();

        //close connection 
        conn.close();

    }

    /*Creating the method header so we can delete the customer based on the customer’s name.
    Delete the old file so the records are not duplicated. 
    Finally, write all of the records to the file except the one that matches the customer to delete.
     */
    
    //UPDATE: Change the method header so we can delete the customer based on the customer’s ID number from the database.
    public void delete(int CustomerID) throws SQLException 
    {
        // connect to the database
	Connection conn = DriverManager.getConnection(CONNECTION_STRING,
	USER_NAME, PASSWORD);
        
	// delete the record
	String SQL = "DELETE FROM landScape WHERE CustomerID = ?";
	PreparedStatement pstmt = conn.prepareStatement(SQL);
	pstmt.setInt(1, CustomerID);
	pstmt.execute();

	// close the database connection
	conn.close(); 

    }

    public ArrayList<Customer> getList() throws SQLException 
    {
        // create the ArrayList so we have something to return
        ArrayList<Customer> list = new ArrayList<Customer>();

        //connect to database 
        Connection conn = DriverManager.getConnection(CONNECTION_STRING,
                USER_NAME, PASSWORD);

        Statement statement = conn.createStatement();
        String SQL = "Select * FROM landscape";
        ResultSet rs = statement.executeQuery(SQL);

        while (rs.next()) {
            // create Customer object and load the attributes
            Customer client = new Customer();
            client.setCustomerID(rs.getInt(1));
            client.setName(rs.getString(2));
            client.setAddress(rs.getString(3));
            client.setYardType(rs.getString(4));
            client.setLength(rs.getDouble(5));
            client.setWidth(rs.getDouble(6));
            client.setTotalCost(rs.getDouble(7));

            // add the Customer object to our list
            list.add(client);
        }

        // close the database connection
        conn.close();

        // return the ArrayList
        return list;
    }

}
