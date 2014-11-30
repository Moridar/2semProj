package Entity;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

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
                        rs.getInt(3), rs.getString(4), rs.getInt(5), getOrderToKunde(rs.getInt(1))));
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
                list.put(rs.getInt(1), new Lager(rs.getString(2)));
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
                        rs.getDouble(7), rs.getDate(8), rs.getDate(9)));
            }
            getKompToOrder(list);
            getStaffListToOrder(list);
            getLastbilListToOrder(list);
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
    public static HashMap<Integer, Integer> getKompToLager(int lagerID) throws SQLException {
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
    private static void getKompToOrder(HashMap<Integer, Ordre> list) throws SQLException {
        ResultSet rs = null;
        Statement statement = null;
        Connection connection = null;

        try {
            Class.forName(driver);

            connection = DriverManager.getConnection(URL, ID, PW);

            statement = connection.createStatement();

            String query = "SELECT * FROM UDE";

            rs = statement.executeQuery(query);

            //=== read the result
            //=== Move cursor one step at a time and
            //	 check for the existence of a row  
            // 1 = ordre id, 2 = kompID, 3 = antal)
            while (rs.next()) {
                list.get(rs.getInt(1)).getKompList().put(rs.getInt(2), rs.getInt(3));
            }

        } catch (Exception ee) {
            System.out.println("fail5");
            System.err.println(ee);
        } finally {
            statement.close();
            connection.close();
        }

    }

    public static HashMap<Date, Integer> getArbejdsSkema(int staffID) throws SQLException {
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

    private static void getStaffListToOrder(HashMap<Integer, Ordre> list) throws SQLException {
        ResultSet rs = null;
        Statement statement = null;
        Connection connection = null;

        try {
            Class.forName(driver);

            connection = DriverManager.getConnection(URL, ID, PW);

            statement = connection.createStatement();

            String query = "SELECT * FROM arbejde";

            rs = statement.executeQuery(query);

            //=== read the result
            //=== Move cursor one step at a time and
            //	 check for the existence of a row  
            while (rs.next()) {
                list.get(rs.getInt(2)).getStaffList().put(rs.getInt(1), rs.getDate(3));
            }

        } catch (Exception ee) {
            System.out.println("fail7");
            System.err.println(ee);
        } finally {
            statement.close();
            connection.close();
        }
    }

    public static HashMap<Date, Integer> getTransportSkema(int LastbilID) throws SQLException {
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

    private static void getLastbilListToOrder(HashMap<Integer, Ordre> list) throws SQLException {
        ResultSet rs = null;
        Statement statement = null;
        Connection connection = null;

        try {
            Class.forName(driver);

            connection = DriverManager.getConnection(URL, ID, PW);

            statement = connection.createStatement();

            String query = "SELECT * FROM Transport";

            rs = statement.executeQuery(query);

            //=== read the result
            //=== Move cursor one step at a time and
            //	 check for the existence of a row  
            while (rs.next()) {
                list.get(rs.getInt(1)).getLastbilList().put(rs.getInt(2), rs.getDate(3));
            }

        } catch (Exception ee) {
            System.out.println("fail9");
            System.err.println(ee);
        } finally {
            statement.close();
            connection.close();
        }

    }

    public static ArrayList<Integer> getOrderToKunde(int KundeID) throws SQLException {
        ResultSet rs = null;
        Statement statement = null;
        Connection connection = null;
        ArrayList<Integer> list = new ArrayList<>();

        try {
            Class.forName(driver);

            connection = DriverManager.getConnection(URL, ID, PW);

            statement = connection.createStatement();

            String query = "SELECT * FROM Ordre WHERE KundeID = " + KundeID;

            rs = statement.executeQuery(query);

            //=== read the result
            //=== Move cursor one step at a time and
            //	 check for the existence of a row  
            while (rs.next()) {
                list.add(rs.getInt(1));
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

    public static void createNewKunde(int id, Kunde k) throws SQLException {
        Statement statement = null;
        Connection connection = null;

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(URL, ID, PW);
            statement = connection.createStatement();

            String insertSQL = "INSERT INTO kunder VALUES ("
                    + id + ",'" + k.getName() + "'," + k.getTelefon() + ",'" + k.getEmail() + "',"
                    + k.getRabat() + ")";
            //=== Execute the statement and retrieve 
            //	a count of how many rows was inserted      
            int rows = statement.executeUpdate(insertSQL);

            //=== Validate the result
            if (rows == 1) {
                System.out.println("One row inserted!");
            } else {
                System.out.println("No row inserted (fail)");
            }
        } catch (Exception ee) {
            System.out.println("fail");
            System.err.println(ee);
        } finally {
            statement.close();
            connection.close();
        }
    }

    public static void createNewStaff(int id, Staff s) throws SQLException {
        Statement statement = null;
        Connection connection = null;

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(URL, ID, PW);
            statement = connection.createStatement();

            String insertSQL = "INSERT INTO staff VALUES ("
                    + id + ",'" + s.getNavn() + "'," + s.getTelefon() + ",'" + s.getStilling() + ")";
            //=== Execute the statement and retrieve 
            //	a count of how many rows was inserted      
            int rows = statement.executeUpdate(insertSQL);

            //=== Validate the result
            if (rows == 1) {
                System.out.println("One row inserted!");
            } else {
                System.out.println("No row inserted (fail)");
            }
        } catch (Exception ee) {
            System.out.println("fail");
            System.err.println(ee);
        } finally {
            statement.close();
            connection.close();
        }
    }

    public static void createNewOrdre(int id, Ordre o) throws SQLException {
        try {
            saveNewOrdre(id, o);
            saveNewKompList(id, o.getKompList());
            saveNewStaffList(id, o.getStaffList());
            saveNewLastbilList(id, o.getLastbilList());
        } catch (Exception e) {
        }

    }

    private static void saveNewOrdre(int id, Ordre o) throws SQLException {
        Statement statement = null;
        Connection connection = null;

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(URL, ID, PW);
            statement = connection.createStatement();

            String insertSQL = "INSERT INTO ordre VALUES ("
                    + id + "," + o.getSalgsmedarbsID() + "," + o.getKundeID() + ",'" + o.getVej() + "',"
                    + o.getPostNR() + "," + o.getConfirmation() + ","
                    + o.getPris() + ",'" + convertJavaDateToSqlDate(o.getDatoStart()) + "','" + convertJavaDateToSqlDate(o.getDatoSlut()) + "')";
            //=== Execute the statement and retrieve 
            //	a count of how many rows was inserted      
            int rows = statement.executeUpdate(insertSQL);

            //=== Validate the result
            if (rows == 1) {
                System.out.println("One row inserted!");
            } else {
                System.out.println("No row inserted (fail)");
            }
        } catch (Exception ee) {
            System.out.println("fail lelz");
            System.err.println(ee);
        } finally {
            statement.close();
            connection.close();
        }
    }

    private static void saveNewKompList(int id, HashMap<Integer, Integer> KompList) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = null;

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(URL, ID, PW);

            String query = "Insert into ude VALUES (?,?,?)";
            statement = connection.prepareStatement(query);
            // Read data for new parts from file and insert into database 
            // Format of file: Each line contains values for one part.
            // pno pname qoh price olevel

            int rows = 0;
            for (Integer KompID : KompList.keySet()) {
                statement.setInt(1, id);
                statement.setInt(2, KompID);
                statement.setInt(3, KompList.get(KompID));

                statement.executeUpdate();
            }
        } catch (Exception ee) {
            System.out.println("fail");
            System.err.println(ee);
            ee.printStackTrace();
        } finally {
            statement.close();
            connection.close();
        }
    }

    private static java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    private static void saveNewStaffList(int id, HashMap<Integer, Date> StaffList) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = null;

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(URL, ID, PW);

            String query = "Insert into arbejde VALUES (?,?,?)";
            statement = connection.prepareStatement(query);
            // Read data for new parts from file and insert into database 
            // Format of file: Each line contains values for one part.
            // pno pname qoh price olevel

            for (Integer StaffID : StaffList.keySet()) {
                statement.setInt(1, id);
                statement.setInt(2, StaffID);
                statement.setDate(3, convertJavaDateToSqlDate(StaffList.get(StaffID)));
                statement.executeUpdate();
            }
        } catch (Exception ee) {
            System.out.println("fail");
            System.err.println(ee);
            ee.printStackTrace();
        } finally {
            statement.close();
            connection.close();
        }
    }

    private static void saveNewLastbilList(int id, HashMap<Integer, Date> LastbilList) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = null;

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(URL, ID, PW);

            String query = "Insert into transport VALUES (?,?,?)";
            statement = connection.prepareStatement(query);
            // Read data for new parts from file and insert into database 
            // Format of file: Each line contains values for one part.
            // pno pname qoh price olevel

            for (Integer LastbilID : LastbilList.keySet()) {
                statement.setInt(1, id);
                statement.setInt(2, LastbilID);
                statement.setDate(3, convertJavaDateToSqlDate(LastbilList.get(LastbilID)));
                statement.executeUpdate();
            }
        } catch (Exception ee) {
            System.out.println("fail");
            System.err.println(ee);
            ee.printStackTrace();
        } finally {
            statement.close();
            connection.close();
        }
    }
}
