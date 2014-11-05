package Entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;

public class DB {

    private static String driver = "oracle.jdbc.driver.OracleDriver";
    private static String URL = "jdbc:oracle:thin:@datdb.cphbusiness.dk:1521:dat";
    private static String ID = "cphba83";
    private static String PW = "cphba83";

    
//    ----------------------------------------------------------------------------
//   Methods to get data from DB
//    ----------------------------------------------------------------------------    
    public static HashMap<Integer, Kunde> getAllKunde() throws SQLException {
        ResultSet rs = null;
        Statement statement = null;
        Connection connection = null;
        HashMap<Integer, Kunde> list = new HashMap<>();

        try {
            Class.forName(driver);

            connection = DriverManager.getConnection(URL, ID, PW);

            statement = connection.createStatement();

            String query = "SELECT * FROM kunder";

            rs = statement.executeQuery(query);

            //=== read the result
            //=== Move cursor one step at a time and
            //	 check for the existence of a row  
            while (rs.next()) {
                list.put(rs.getInt(1), new Kunde(rs.getString(2),
                        rs.getInt(3), rs.getString(4), rs.getInt(5)));
            }

        } catch (Exception ee) {
            System.out.println("fail-2");
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
                        rs.getInt(3), rs.getString(4), getArbejdsSkema(rs.getInt(1))));
            }

        } catch (Exception ee) {
            System.out.println("fail-1");
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
            System.out.println("fail0");
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
                list.put(rs.getInt(1), new Lager(rs.getString(2), 
                        getKompToLager(rs.getInt(1))));
            }

        } catch (Exception ee) {
            System.out.println("fail1");
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

            String query = "SELECT * FROM Lastbiler";

            rs = statement.executeQuery(query);

            //=== read the result
            //=== Move cursor one step at a time and
            //	 check for the existence of a row  
            while (rs.next()) {
                list.put(rs.getInt(1), new Lastbil(rs.getString(2), 
                        rs.getInt(3), getTransportSkema(rs.getInt(1))));
            }

        } catch (Exception ee) {
            System.out.println("fail2");
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
                list.put(rs.getInt(1), new Ordre(rs.getInt(2), rs.getInt(3),
                        rs.getString(4), rs.getInt(5), rs.getInt(6) == 1,
                        rs.getDouble(7), rs.getDate(8), rs.getDate(9),
                        getKompToOrder(rs.getInt(1)), getStaffListToOrder(rs.getInt(1)),
                        getLastbilListToOrder(rs.getInt(1))));
            }

        } catch (Exception ee) {
            System.out.println("fail3");
            System.err.println(ee);
        } finally {
            statement.close();
            connection.close();
        }
        return list;
    }

    /**
     *
     * @param lagerID
     * @return HashMap<KomponentID, Antal>
     * @throws SQLException
     */
    private static HashMap<Integer, Integer> getKompToLager(int lagerID) throws SQLException {
        ResultSet rs = null;
        Statement statement = null;
        Connection connection = null;
        HashMap<Integer, Integer> list = new HashMap<>();

        try {
            Class.forName(driver);

            connection = DriverManager.getConnection(URL, ID, PW);

            statement = connection.createStatement();

            String query = "SELECT * FROM Hjemme WHERE LagerID = " + lagerID;

            rs = statement.executeQuery(query);

            //=== read the result
            //=== Move cursor one step at a time and
            //	 check for the existence of a row  
            while (rs.next()) {
                list.put(rs.getInt(2), rs.getInt(3));
            }

        } catch (Exception ee) {
            System.out.println("fail4");
            System.err.println(ee);
        } finally {
            statement.close();
            connection.close();
        }
        return list;
    }

    /**
     *
     * @param orderID
     * @return HashMap<KomponentID, Antal>
     * @throws SQLException
     */
    private static HashMap<Integer, Integer> getKompToOrder(int ordreID) throws SQLException {
        ResultSet rs = null;
        Statement statement = null;
        Connection connection = null;
        HashMap<Integer, Integer> list = new HashMap<>();

        try {
            Class.forName(driver);

            connection = DriverManager.getConnection(URL, ID, PW);

            statement = connection.createStatement();

            String query = "SELECT * FROM UDE WHERE OrdreID = " + ordreID;

            rs = statement.executeQuery(query);

            //=== read the result
            //=== Move cursor one step at a time and
            //	 check for the existence of a row  
            while (rs.next()) {
                list.put(rs.getInt(2), rs.getInt(3));
            }

        } catch (Exception ee) {
            System.out.println("fail5");
            System.err.println(ee);
        } finally {
            statement.close();
            connection.close();
        }
        return list;
    }

    private static HashMap<Date, Integer> getArbejdsSkema(int staffID) throws SQLException {
        ResultSet rs = null;
        Statement statement = null;
        Connection connection = null;
        HashMap<Date, Integer> list = new HashMap<>();

        try {
            Class.forName(driver);

            connection = DriverManager.getConnection(URL, ID, PW);

            statement = connection.createStatement();

            String query = "SELECT * FROM arbejde WHERE staffID = " + staffID;

            rs = statement.executeQuery(query);

            //=== read the result
            //=== Move cursor one step at a time and
            //	 check for the existence of a row  
            while (rs.next()) {
                list.put(rs.getDate(3), rs.getInt(2));
            }

        } catch (Exception ee) {
            System.out.println("fail6");
            System.err.println(ee);
        } finally {
            statement.close();
            connection.close();
        }
        return list;
    }
    private static HashMap<Integer, Date> getStaffListToOrder(int ordreID) throws SQLException {
        ResultSet rs = null;
        Statement statement = null;
        Connection connection = null;
        HashMap<Integer, Date> list = new HashMap<>();

        try {
            Class.forName(driver);

            connection = DriverManager.getConnection(URL, ID, PW);

            statement = connection.createStatement();

            String query = "SELECT * FROM arbejde WHERE ordreID = " + ordreID;

            rs = statement.executeQuery(query);

            //=== read the result
            //=== Move cursor one step at a time and
            //	 check for the existence of a row  
            while (rs.next()) {
                list.put(rs.getInt(2), rs.getDate(3));
            }

        } catch (Exception ee) {
            System.out.println("fail7");
            System.err.println(ee);
        } finally {
            statement.close();
            connection.close();
        }
        return list;
    }
    private static HashMap<Date, Integer> getTransportSkema(int LastbilID) throws SQLException {
        ResultSet rs = null;
        Statement statement = null;
        Connection connection = null;
        HashMap<Date, Integer> list = new HashMap<>();

        try {
            Class.forName(driver);

            connection = DriverManager.getConnection(URL, ID, PW);

            statement = connection.createStatement();

            String query = "SELECT * FROM Transport WHERE LastbilID = " + LastbilID;

            rs = statement.executeQuery(query);

            //=== read the result
            //=== Move cursor one step at a time and
            //	 check for the existence of a row  
            while (rs.next()) {
                list.put(rs.getDate(3), rs.getInt(2));
            }

        } catch (Exception ee) {
            System.out.println("fail8");
            System.err.println(ee);
        } finally {
            statement.close();
            connection.close();
        }
        return list;
    }
    private static HashMap<Integer, Date> getLastbilListToOrder(int OrdreID) throws SQLException {
        ResultSet rs = null;
        Statement statement = null;
        Connection connection = null;
        HashMap<Integer, Date> list = new HashMap<>();

        try {
            Class.forName(driver);

            connection = DriverManager.getConnection(URL, ID, PW);

            statement = connection.createStatement();

            String query = "SELECT * FROM Transport WHERE OrdreID = " + OrdreID;

            rs = statement.executeQuery(query);

            //=== read the result
            //=== Move cursor one step at a time and
            //	 check for the existence of a row  
            while (rs.next()) {
                list.put(rs.getInt(2), rs.getDate(3));
            }

        } catch (Exception ee) {
            System.out.println("fail9");
            System.err.println(ee);
        } finally {
            statement.close();
            connection.close();
        }
        return list;
    }
//    End of getData methods
//    ----------------------------------------------------------------------------    
//    ----------------------------------------------------------------------------
//    ----------------------------------------------------------------------------
//    saveData methods
//    
//    public static void saveNewKunde(Kunde k) throws SQLException {
//        Statement statement = null;
//        Connection connection = null;
//
//        try {
//            Class.forName(driver);
//            connection = DriverManager.getConnection(URL, ID, PW);
//            statement = connection.createStatement();
//
//            String insertSQL = "INSERT INTO persons VALUES ("
//                    + +p.getId() + ",'" + p.getName() + "')";
//           //=== Execute the statement and retrieve 
//            //	a count of how many rows was inserted      
//            int rows = statement.executeUpdate(insertSQL);
//
//            //=== Validate the result
//            if (rows == 1) {
//                System.out.println("One row inserted!");
//            } else {
//                System.out.println("No row inserted (fail)");
//            }
//        } catch (Exception ee) {
//            System.out.println("fail");
//            System.err.println(ee);
//        } finally {
//            statement.close();
//            connection.close();
//        }
//    }
}
