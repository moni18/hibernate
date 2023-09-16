package jm.task.core.jdbc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    //String url = "jdbc:postgresql://localhost/postgres?user=postgres&password=admin&ssl=true";
    //String url = "jdbc:postgresql://host:5432/jdbcDB?user=postgres&password=admin&ssl=true";
    String url ="jdbc:postgresql://localhost:5432/jdbc_DB";
    // //localhost:5432/
   /* Properties props = new Properties();
    props.setProperty("user", "postgres");*/
    private static Connection connection = null;
    private static Util instance = null;

    private Util(){
        try{
            if(connection == null || connection.isClosed()){
                Properties properties = getProperties();
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

    private static Properties getProperties() throws IOException {
        Properties properties = new Properties();
        try (InputStream is = new FileInputStream("./src/main/java/resources/database.properties")) {
            properties.load(is);
            return properties;
        } catch (IOException e) {
            throw new IOException("Database file was not found");
        }

    }
}
