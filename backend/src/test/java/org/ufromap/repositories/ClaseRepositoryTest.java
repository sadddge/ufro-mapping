package org.ufromap.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ufromap.models.Clase;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClaseRepositoryTest {

    private ClaseRepository claseRepository;
    private Connection connection;

    @BeforeAll
    public static void setUpClass() {
        // Configura el driver de H2 para que pueda ser utilizado en las pruebas
        System.setProperty("jdbc.drivers", "org.h2.Driver");
    }
    @BeforeEach
    public void setUp() throws SQLException {
        // Configura la conexión a la base de datos H2 en memoria
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");

        // Crear la tabla en la base de datos H2
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS clase");
            String createTable = """
                CREATE TABLE clase (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    sala_id INT,
                    edificio_id INT,
                    asignatura_id INT,
                    dia_semana INT,
                    periodo INT,
                    docente_nombre VARCHAR(255),
                    modulo INT
                );
                """;
            stmt.execute(createTable);

            // Insertar datos de ejemplo para los tests
            String insertData = """
                INSERT INTO clase (sala_id, edificio_id, asignatura_id, dia_semana, periodo, docente_nombre, modulo)
                VALUES (101, 201, 301, 1, 2023, 'Profesor Test', 1);
                """;
            stmt.execute(insertData);
        }

        // Inicia ClaseRepository con la conexión H2 en memoria
        claseRepository = new ClaseRepository(connection);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        // Cierra la conexión después de cada test
        connection.close();
    }

    @Test
    public void testGetClases() {

        List<Clase> clases = claseRepository.getClases();
        assertNotNull(clases);
        assertEquals(1, clases.size());
        assertEquals("Profesor Test", clases.get(0).getDocenteNombre());
    }

    @Test
    public void testGetClaseById() {

        Clase clase = claseRepository.getClaseById(1);
        assertNotNull(clase);
        assertEquals("Profesor Test", clase.getDocenteNombre());
        assertEquals(101, clase.getSalaId());
    }

    @Test
    public void testGetClasesBySalaId() {

        List<Clase> clases = claseRepository.getClasesBySalaId(101);
        assertNotNull(clases);
        assertEquals(1, clases.size());
        assertEquals("Profesor Test", clases.get(0).getDocenteNombre());
    }

    @Test
    public void testGetClasesByEdificioId() {

        List<Clase> clases = claseRepository.getClasesByEdificioId(201);
        assertNotNull(clases);
        assertEquals(1, clases.size());
        assertEquals("Profesor Test", clases.get(0).getDocenteNombre());
    }

    @Test
    public void testGetClasesByAsignaturaId() {

        List<Clase> clases = claseRepository.getClasesByAsignaturaId(301);
        assertNotNull(clases);
        assertEquals(1, clases.size());
        assertEquals("Profesor Test", clases.get(0).getDocenteNombre());
    }

    @Test
    public void testGetClasesByDiaSemana() {

        List<Clase> clases = claseRepository.getClasesByDiaSemana(1);
        assertNotNull(clases);
        assertEquals(1, clases.size());
        assertEquals("Profesor Test", clases.get(0).getDocenteNombre());
    }

    @Test
    public void testAddClase() {
        Clase clase = new Clase(2, 101, 201, 301, 1, 2023, "Profesor Test", 1);

        boolean result = claseRepository.addClase(clase);
        assertTrue(result);
    }

    @Test
    public void testUpdateClase() {
        Clase clase = new Clase(1, 101, 201, 301, 2, 2023, "Profesor Actualizado", 1);
        boolean result = claseRepository.updateClase(clase);
        assertTrue(result);
    }

    @Test
    public void testDeleteClase(){
        boolean result = claseRepository.deleteClase(1);
        assertTrue(result);
    }
}
