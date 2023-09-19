package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    String url ="jdbc:postgresql://localhost:5432/jdbc_DB";
    private static Connection connection = null;
    private static Util instance = null;

    private Util(){
        try{
            if(connection == null || connection.isClosed()){
                Properties properties = getProps();
                connection = DriverManager.getConnection(properties.getProperty("db.url"), properties.getProperty("db.user"), properties.getProperty("db.password"));
            }
        } catch (SQLException | IOException e){
            e.printStackTrace();
        }
    }

    public static Util getInstance(){
        if(instance == null){
            instance = new Util();
        }
        return  instance;
    }
    public Connection getConnection(){
        return connection;
    }

    private static Properties getProps() throws IOException {
        Properties props = new Properties();
        try (InputStream is = new FileInputStream("./src/main/java/resources/database.properties")) {
            props.load(is);
            return props;
        } catch (IOException e) {
            throw new IOException("Database file was not found");
        }
    }

    //hibernate
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
       if(sessionFactory == null){
           try{
               Properties properties = new Properties();
               properties.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
               properties.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/jdbc_DB");
               properties.setProperty("hibernate.connection.username", "postgres");
               properties.setProperty("hibernate.connection.password", "admin");
               //properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL92Dialect");
               properties.setProperty("hibernate.show_sql", "true");
               //properties.setProperty("hibernate.hbm2ddl.auto", "create");
               sessionFactory= new Configuration().addProperties(properties)
                       .addAnnotatedClass(User.class).buildSessionFactory();

               /*StandardServiceRegistryBuilder builder =
                       new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
               sessionFactory = configuration.buildSessionFactory(builder.build());*/

           }catch (Exception e){
               e.printStackTrace();
           }
       }
        return sessionFactory;
    }
}
