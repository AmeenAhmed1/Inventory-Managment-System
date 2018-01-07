package database;

import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author AmeenAhmed
 */
public class DBConnection {

    private static final String host = "jdbc:mysql://localhost/inventory";
    private static final String user = "root";
    private static final String pass = "";
    
    //Connection function
    public static Connection DBConnect() throws SQLException{
        
        try {
            Connection conn = DriverManager.getConnection(host, user, pass);
            return conn;
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            System.out.println("Connection class : " + e);
            return null;
        }
    }
}
