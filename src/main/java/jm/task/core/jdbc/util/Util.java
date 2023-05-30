package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соединения с БД
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/itm_pp1";
    private static final String DB_username = "postgres";
    private static final String DB_password = "bestuser";
    private static Connection connection;


    public static Connection openConnect() {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(DB_URL, DB_username, DB_password);
//            System.out.println("Open database ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnect(Connection connection) {
        try {
            connection.close();
            System.out.println("Close Database");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}