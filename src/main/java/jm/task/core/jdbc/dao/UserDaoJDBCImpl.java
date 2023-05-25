package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.openConnect;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sqlQuery = "CREATE TABLE Users (id SERIAL PRIMARY KEY, name varchar(255), lastname varchar(255), age smallint);";
//        Если в переменной sqlQuery изменить = "CREATE TABLE IF NOT EXISTS.... - то Exception ни когда не сработает....
        try {
            Statement statement = Util.openConnect().createStatement();
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            System.out.println("Таблица уже существует");
        }
    }

    public void dropUsersTable() {
        String sqlQuery = "DROP TABLE IF EXISTS users";
        try {
            Statement statement = openConnect().createStatement();
            statement.executeUpdate(sqlQuery);
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlQuery = "INSERT INTO users (name, lastname, age) VALUES (?,?,?)";
        try {
            PreparedStatement preparedStatement = openConnect().prepareStatement(sqlQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User с именем %s добавлен в базу\n", name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String sqlQuery = "delete from users where id=?";
        try {
            PreparedStatement preparedStatement = openConnect().prepareStatement(sqlQuery);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> listUser = new ArrayList<>();
        try {
            Statement statement = openConnect().createStatement();
            String sqlQuery = "SELECT * FROM Users";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                listUser.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listUser;
    }

    public void cleanUsersTable() {
        String sqlQuery = "TRUNCATE TABLE users";
        try {
            PreparedStatement statement = openConnect().prepareStatement(sqlQuery);
            statement.executeUpdate();
            System.out.println("База данных очищена");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
