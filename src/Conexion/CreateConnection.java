package Conexion;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class CreateConnection {
    private final Properties config = new Properties();
    private String hostname;
    private String port;
    private String database;
    private String username;
    private String password;

    public CreateConnection() {
        try {
            cargarConfiguracion();
            loadProperties();
        } catch (IOException ex) {
            throw new RuntimeException("No se pudo cargar db_config.properties. " + ex.getMessage(), ex);
        }
    }

    private void cargarConfiguracion() throws IOException {
        // Primero intenta cargarlo como recurso del proyecto/JAR.
        try (InputStream in = CreateConnection.class.getResourceAsStream("/Conexion/db_config.properties")) {
            if (in != null) {
                config.load(in);
                return;
            }
        }

        // NetBeans puede ejecutar con Compile on Save y no copiar los .properties
        // al classpath. Por eso también lo buscamos en src/Conexion.
        Path ruta = Paths.get("src", "Conexion", "db_config.properties");
        if (Files.exists(ruta)) {
            try (InputStream in = Files.newInputStream(ruta)) {
                config.load(in);
                return;
            }
        }

        throw new IOException("No se encontró Conexion/db_config.properties en el classpath ni en src/Conexion");
    }

    private void loadProperties() throws IOException {
        hostname = config.getProperty("hostname");
        port = config.getProperty("port");
        database = config.getProperty("database");
        username = config.getProperty("username");
        password = config.getProperty("password");

        if (hostname == null || port == null || database == null || username == null || password == null) {
            throw new IOException("Faltan datos en db_config.properties (hostname, port, database, username o password)");
        }
    }

    public Connection getConection() {
        String jdbcUrl = "jdbc:postgresql://" + hostname + ":" + port + "/" + database;
        try {
            return DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException ex) {
            throw new RuntimeException("No se pudo conectar a PostgreSQL: " + ex.getMessage(), ex);
        }
    }
}
