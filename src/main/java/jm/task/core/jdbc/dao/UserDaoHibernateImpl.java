package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {  }

    @Override
    public void createUsersTable() {
        try(Session session = Util.getSessionFactory().openSession()){
            Transaction tx1 = session.beginTransaction();

            String sql = "CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGSERIAL NOT NULL PRIMARY KEY,"  +
                    "firstName VARCHAR(50) NOT NULL, " +
                    "lastName VARCHAR(50) NOT NULL, " +
                    "age smallint NOT NULL)";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            tx1.commit();
        }

    }

    @Override
    public void dropUsersTable() {
        try(Session session = Util.getSessionFactory().openSession()){
            Transaction tx1 = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS users";

            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            tx1.commit();
        }
    }

    @Override
    public void saveUser(String name1, String lastName, byte age) {
        try(Session session = Util.getSessionFactory().openSession()){
            Transaction tx1 = session.beginTransaction();
            User user1 = new User(name1, lastName, age);
            //String sql = "INSERT into users (firstName, lastName, age) VALUES (name1, firstName, age)";
            //Query query = session.createSQLQuery(sql);
            //query.executeUpdate();
            session.save(user1);
            System.out.println("User " + name1 + " is added to table \"users\"");
            tx1.commit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = Util.getSessionFactory().openSession()){
            Transaction tx1 = session.beginTransaction();
            String sql = "DELETE FROM users WHERE id=id";
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            tx1.commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try(Session session = Util.getSessionFactory().openSession()){
           Transaction tx1 = session.beginTransaction();
           String sql = "SELECT * FROM users";
           List<User> users = session.createSQLQuery(sql).addEntity(User.class).list();

            tx1.commit();
           return users;
       }
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = Util.getSessionFactory().openSession()){
            Transaction tx1 = session.beginTransaction();
            String sql = "DELETE FROM users";
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            tx1.commit();
            }
        }
    }

