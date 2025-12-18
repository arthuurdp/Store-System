package db;

import java.sql.*;

public class DB {
    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/storejdbc";
                conn = DriverManager.getConnection(url, "root", "arthur0702");
            } catch (SQLException e) {
                throw new RuntimeException("Error connecting to database: " + e.getMessage());
            }
        }
        return conn;
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}