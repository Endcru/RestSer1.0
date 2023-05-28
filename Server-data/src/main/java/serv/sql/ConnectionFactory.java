package serv.sql;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class ConnectionFactory {
    private ConnectionFactory(){
        throw new UnsupportedOperationException();
    }
    public static Connection getConnection() {
        Properties properties = new Properties();
        Connection connection = null;
        try(InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties")){
            properties.load(inputStream);
            String host = properties.getProperty("db.postgresql-host");
            String username = properties.getProperty("db.postgresql-username");
            String password = properties.getProperty("db.postgresql-password");
            connection = DriverManager.getConnection(host, username, password);

        } catch (IOException e){
            throw new RuntimeException(e);
            //log.error("An error occurred: {}", e.getMessage(), e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
