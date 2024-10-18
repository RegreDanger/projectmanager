package domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionTest {

    @Test
    public void testConnection() {
        // Obtener la instancia de la conexión
        try {
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            Connection connection = dbConnection.getConnection();

            // Verificar que la conexión no sea null
            assertNotNull(connection, "La conexión debería ser exitosa y no null");

            // Verificar que la conexión no esté cerrada
            assertFalse(connection.isClosed(), "La conexión no debería estar cerrada");

        } catch (SQLException e) {
            fail("Se produjo un error al intentar conectarse a la base de datos: " + e.getMessage());
        }
    }
}

