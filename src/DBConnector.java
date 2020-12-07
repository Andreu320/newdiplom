import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnector {
    static final String DB_URL = "jdbc:mysql://localhost:3306/";
    static final String DB_NAME = "hotel";
    static final String DB_PAR = "?characterEncoding=UTF-8";
    static final String USER = "root";
    static final String PASS = "Andreu320";
    
    DBConnector()
    {
        connectToDB();
    }
    
    Connection connection = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet;
        
    private int connectToDB()
    {
        //check if database exists
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            
            String sql = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
            return 2;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //connect to database
        try {
            connection = DriverManager.getConnection(DB_URL + DB_NAME + DB_PAR, USER, PASS);
            statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }
    
    public ArrayList<String> getRoomsFromDB() {
        ArrayList<String> list = new ArrayList<>();
        try {
            String select = "SELECT room_num FROM room";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            while(resultSet.next()) {
                list.add(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
    
    public int getCountClientRoomFromDB(String room) {
        int count = 0;
        try {
            String select = "SELECT COUNT(*) FROM client_room WHERE room_num = ?;";
            preparedStatement = connection.prepareStatement(select);
            preparedStatement.setString(1, room);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return count;
    }
    
    public int getCountBookingFromDB(String room) {
        int count = 0;
        try {
            String select = "SELECT COUNT(*) FROM booking WHERE room_num = ?;";
            preparedStatement = connection.prepareStatement(select);
            preparedStatement.setString(1, room);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return count;
    }
    
    //
    public ArrayList<Client> getClientInfoFromDB(String rumNum, String stDate, String finDate) {
        ArrayList<Client> list = new ArrayList();
        try {
            String select = 
                    "SELECT client_ln, client_fn, booking_begin_date, booking_end_date FROM booking JOIN client USING(client_id)"
                    + "WHERE room_num = ? AND booking_begin_date BETWEEN ? AND ?;";
            preparedStatement = connection.prepareStatement(select);
            preparedStatement.setString(1, rumNum);
            preparedStatement.setString(2, stDate);
            preparedStatement.setString(3, finDate);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String сl = resultSet.getString(1);
                String cf = resultSet.getString(2);
                String stD = resultSet.getString(3);
                String finD = resultSet.getString(4);
                
                Client client = new Client(сl, cf, stD, finD);
                list.add(client);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
}
