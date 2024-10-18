package domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ProjectDAOTest {
	
	private ProjectDAO projectDAO;
	
	@BeforeEach
	public void setUp() throws SQLException {
		// Inicializa la conexión y el DAO aquí, puedes usar un mock o una base de datos en memoria para pruebas
		projectDAO = ProjectDAO.getInstance();
	}
	
	@Test
	public void testCreateProject() throws SQLException {
		Project project = new Project(0, "Test Project", "Description", 0, true, new java.util.Date(),
				new java.util.Date(), Arrays.asList(new Task(), new Task()));
		projectDAO.createProject(project);
		
		// Verifica que el proyecto se ha creado correctamente
		List<Project> projects = projectDAO.readProjects();
		assertTrue(projects.stream().anyMatch(p -> p.getName().equals("Test Project")));
	}
	
	@Test
	public void testReadProjects() throws SQLException {
		List<Project> projects = projectDAO.readProjects();
		assertNotNull(projects);
		assertFalse(projects.isEmpty());
	}
	
}
