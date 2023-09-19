package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final Connection connection = Util.getInstance().getConnection();
    public UserDaoJDBCImpl() {  }

    public void createUsersTable() {
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGSERIAL PRIMARY KEY, firstName TEXT, lastName TEXT, age INTEGER)");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate("DROP TABLE IF EXISTS users");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(PreparedStatement prepStatement = connection
                .prepareStatement("INSERT INTO users (firstName, lastName, age) VALUES (?, ?, ?)")){
            prepStatement.setString(1, name);
            prepStatement.setString(2, lastName);
            prepStatement.setByte(3, age);
            prepStatement.executeUpdate();
            System.out.println("User " + name + " is added to table \"users\"");
        }catch (SQLException e)        {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try(Statement st = connection.createStatement()){
            st.executeUpdate("DELETE FROM users WHERE id=id");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(ResultSet resultSet = connection.createStatement()
                .executeQuery("select * from users")){
            while(resultSet.next()){
                User user = new User(resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try(Statement st = connection.createStatement()){
            st.executeUpdate("truncate table users");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
