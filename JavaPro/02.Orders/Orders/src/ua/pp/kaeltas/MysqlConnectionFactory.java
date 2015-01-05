package ua.pp.kaeltas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by kaeltas on 18.12.14.
 */
public class MysqlConnectionFactory {
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/ordersdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    //private Connection conn = null;

    public synchronized static Connection createConnection() {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return conn;
    }

    private MysqlConnectionFactory() {
    }

    /*public synchronized MysqlConnectionSingleton getInstance() throws ClassNotFoundException, SQLException {
        if (mysqlConnectionSingleton == null) {
            mysqlConnectionSingleton = new MysqlConnectionSingleton();
        }

        return mysqlConnectionSingleton;
    }*/

}
