package domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionTest {

    @Test
    public void testConnection() {
        // Obtener la instancia de la conexi�n
        try {
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            Connection connection = dbConnection.getConnection();

            // Verificar que la conexi�n no sea null
            assertNotNull(connection, "La conexi�n deber�a ser exitosa y no null");

            // Verificar que la conexi�n no est� cerrada
            assertFalse(connection.isClosed(), "La conexi�n no deber�a estar cerrada");

        } catch (SQLException e) {
            fail("Se produjo un error al intentar conectarse a la base de datos: " + e.getMessage());
        }
    }
}

