package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection con = null;

    public static Connection getConnection() {
        if (con == null) {
            try {
                Properties prop = readProp();
                String url = prop.getProperty("dburl");

                con = DriverManager.getConnection(url, prop);
            }catch(SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return con;
    }

    public static void closeConnection() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    private static Properties readProp (){
        try(FileInputStream fis = new FileInputStream("db.properties")){

            Properties prop = new Properties();
            prop.load(fis);
            return prop;

        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}
