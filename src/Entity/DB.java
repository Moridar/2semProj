package Entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class DB {

    private static String driver = "oracle.jdbc.driver.OracleDriver";
    private static String URL = "jdbc:oracle:thin:@datdb.cphbusiness.dk:1521:dat";
    private static String ID = "cphba83";
    private static String PW = "cphba83";

    public static HashMap<Integer, Kunde> getAllKunde() throws SQLException {
        ResultSet rs = null;
        Statement statement = null;
        Connection connection = null;
        HashMap<Integer, Kunde> list = new HashMap<>();
        
        try {
            Class.forName(driver);

            connection = DriverManager.getConnection(URL, ID, PW);

            statement = connection.createStatement();

            String query = "SELECT * FROM kunde";

            rs = statement.executeQuery(query);

            //=== read the result
            
           //=== Move cursor one step at a time and
            //	 check for the existence of a row  
            while (rs.next()) {
                list.put(rs.getInt(1), new Kunde(rs.getString(2),
                        rs.getInt(3), rs.getString(4), rs.getInt(5)));
            }
            
        } catch (Exception ee) {
            System.out.println("fail");
            System.err.println(ee);
        } finally {
            statement.close();
            connection.close();
        }
        return list;
    }
    
    public static HashMap<Integer, Staff> getAllStaff() throws SQLException {
        ResultSet rs = null;
        Statement statement = null;
        Connection connection = null;
        HashMap<Integer, Staff> list = new HashMap<>();
        
        try {
            Class.forName(driver);

            connection = DriverManager.getConnection(URL, ID, PW);

            statement = connection.createStatement();

            String query = "SELECT * FROM Staff";

            rs = statement.executeQuery(query);

            //=== read the result
            
           //=== Move cursor one step at a time and
            //	 check for the existence of a row  
            while (rs.next()) {
                list.put(rs.getInt(1), new Staff(rs.getString(2),
                        rs.getInt(3), rs.getString(4)));
            }
            
        } catch (Exception ee) {
            System.out.println("fail");
            System.err.println(ee);
        } finally {
            statement.close();
            connection.close();
        }
        return list;
    }
    
    public static HashMap<Integer, Komponent> getAllKomponenter() throws SQLException {
        ResultSet rs = null;
        Statement statement = null;
        Connection connection = null;
        HashMap<Integer, Komponent> list = new HashMap<>();
        
        try {
            Class.forName(driver);

            connection = DriverManager.getConnection(URL, ID, PW);

            statement = connection.createStatement();

            String query = "SELECT * FROM Komponent";

            rs = statement.executeQuery(query);

            //=== read the result
            
           //=== Move cursor one step at a time and
            //	 check for the existence of a row  
            while (rs.next()) {
                list.put(rs.getInt(1), new Komponent(rs.getString(2),
                        rs.getInt(3)));
            }
            
        } catch (Exception ee) {
            System.out.println("fail");
            System.err.println(ee);
        } finally {
            statement.close();
            connection.close();
        }
        return list;
    }
    public static HashMap<Integer, Lager> getAllLager() throws SQLException {
        ResultSet rs = null;
        Statement statement = null;
        Connection connection = null;
        HashMap<Integer, Lager> list = new HashMap<>();
        
        try {
            Class.forName(driver);

            connection = DriverManager.getConnection(URL, ID, PW);

            statement = connection.createStatement();

            String query = "SELECT * FROM Lager";

            rs = statement.executeQuery(query);

            //=== read the result
            
           //=== Move cursor one step at a time and
            //	 check for the existence of a row  
            while (rs.next()) {
                list.put(rs.getInt(1), new Lager(rs.getString(2)));
            }
            
        } catch (Exception ee) {
            System.out.println("fail");
            System.err.println(ee);
        } finally {
            statement.close();
            connection.close();
        }
        return list;
    }
    
    public static HashMap<Integer, Lastbil> getAllLastbil() throws SQLException {
        ResultSet rs = null;
        Statement statement = null;
        Connection connection = null;
        HashMap<Integer, Lastbil> list = new HashMap<>();
        
        try {
            Class.forName(driver);

            connection = DriverManager.getConnection(URL, ID, PW);

            statement = connection.createStatement();

            String query = "SELECT * FROM Lager";

            rs = statement.executeQuery(query);

            //=== read the result
            
           //=== Move cursor one step at a time and
            //	 check for the existence of a row  
            while (rs.next()) {
                list.put(rs.getInt(1), new Lastbil(rs.getString(2), rs.getInt(3)));
            }
            
        } catch (Exception ee) {
            System.out.println("fail");
            System.err.println(ee);
        } finally {
            statement.close();
            connection.close();
        }
        return list;
    }
    
    public static HashMap<Integer, Ordre> getAllOrder() throws SQLException {
        ResultSet rs = null;
        Statement statement = null;
        Connection connection = null;
        HashMap<Integer, Ordre> list = new HashMap<>();
        
        try {
            Class.forName(driver);

            connection = DriverManager.getConnection(URL, ID, PW);

            statement = connection.createStatement();

            String query = "SELECT * FROM Ordre";

            rs = statement.executeQuery(query);

            //=== read the result
            
           //=== Move cursor one step at a time and
            //	 check for the existence of a row  
            while (rs.next()) {
                list.put(rs.getInt(1), new Ordre(rs.getInt(2),rs.getInt(3),
                rs.getString(4),rs.getInt(5), rs.getInt(6)==1,
                rs.getDouble(7),rs.getDate(8),rs.getDate(9)));
            }
            
        } catch (Exception ee) {
            System.out.println("fail");
            System.err.println(ee);
        } finally {
            statement.close();
            connection.close();
        }
        return list;
    }
    
    public static HashMap<Integer, Ordre> getAllHjemme() throws SQLException {
        ResultSet rs = null;
        Statement statement = null;
        Connection connection = null;
        HashMap<Integer, Ordre> list = new HashMap<>();
        
        try {
            Class.forName(driver);

            connection = DriverManager.getConnection(URL, ID, PW);

            statement = connection.createStatement();

            String query = "SELECT * FROM Ordre";

            rs = statement.executeQuery(query);

            //=== read the result
            
           //=== Move cursor one step at a time and
            //	 check for the existence of a row  
            while (rs.next()) {
                list.put(rs.getInt(1), new Ordre(rs.getInt(2),rs.getInt(3),
                rs.getString(4),rs.getInt(5), rs.getInt(6)==1,
                rs.getDouble(7),rs.getDate(8),rs.getDate(9)));
            }
            
        } catch (Exception ee) {
            System.out.println("fail");
            System.err.println(ee);
        } finally {
            statement.close();
            connection.close();
        }
        return list;
    }
}
