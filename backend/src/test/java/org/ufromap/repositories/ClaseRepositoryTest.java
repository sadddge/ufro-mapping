package org.ufromap.repositories;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ufromap.models.Clase;

import java.sql.*;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ClaseRepositoryTest {

    private ClaseRepository claseRepository;
    private Connection connection;
    @BeforeAll
    static void setUpClass() {
        System.setProperty("jdbc.drivers", "org.h2.Driver");
    }

    @BeforeEach
    void setUp() throws SQLException {
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

    @Test
    void findAll() {
        List<Clase> clases = claseRepository.findAll();
        assertNotNull(clases);
        assertEquals(1, clases.size());
        assertEquals("Profesor Test", clases.get(0).getDocente());
    }

    @Test
    void findById() {
        Clase clase = claseRepository.findById(1);
        assertNotNull(clase);
        assertEquals("Profesor Test", clase.getDocente());
    }

    @Test
    void findByFilter() {
        Map<String, Object> filters = Map.of("sala_id", 101);
        List<Clase> clases = claseRepository.findByFilter(filters);
        assertNotNull(clases);
        assertEquals(1, clases.size());
        assertEquals(101, clases.get(0).getSalaId());
    }

    @Test
    void add() {
        Clase newClase = new Clase(0, 102, 202, 302, 2, 2024, "Profesor Nuevo", 2);
        Clase addedClase = claseRepository.add(newClase);
        assertNotNull(addedClase);
        assertEquals("Profesor Nuevo", addedClase.getDocente());

        List<Clase> clases = claseRepository.findAll();
        assertEquals(2, clases.size());
    }

    @Test
    void update() {
        Clase clase = claseRepository.findById(1);
        assertNotNull(clase);

        clase.setDocente("Profesor Actualizado");
        Clase updatedClase = claseRepository.update(clase);
        assertNotNull(updatedClase);
        assertEquals("Profesor Actualizado", updatedClase.getDocente());
    }

    @Test
    void delete() {
        boolean isDeleted = claseRepository.delete(1);
        assertTrue(isDeleted);

        Clase clase = claseRepository.findById(1);
        assertNull(clase);
    }

    @Test
    void findByAsignaturaId() {
        List<Clase> clases = claseRepository.findByAsignaturaId(301);
        assertNotNull(clases);
        assertEquals(1, clases.size());
        assertEquals(301, clases.get(0).getAsignaturaId());
    }

    @Test
    void findBySalaId() {
        List<Clase> clases = claseRepository.findBySalaId(101);
        assertNotNull(clases);
        assertEquals(1, clases.size());
        assertEquals(101, clases.get(0).getSalaId());
    }

    @Test
    void mapToObject() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM clase WHERE id = 1");
            assertTrue(resultSet.next());
            Clase clase = claseRepository.mapToObject(resultSet);
            assertNotNull(clase);
            assertEquals("Profesor Test", clase.getDocente());
        }
    }
}