package domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class SimplyDAOExecution {
    private TaskDAO taskDAO;
    private ProjectDAO projectDAO;
    private Faker faker;

    @BeforeEach
    public void setUp() throws SQLException {
        taskDAO = TaskDAO.getInstance();
        projectDAO = ProjectDAO.getInstance();
        faker = new Faker();
    }

    private Project createRandomProject(int userId) throws SQLException {
        String name = faker.company().name();
        String description = faker.lorem().sentence();
        Project project = new Project(userId, 0, name, description, faker.number().randomDigit(), true, new Date(), new Date());
        projectDAO.createProject(project);
        return project;
    }

    private Task createRandomTask(int projectId) throws SQLException {
        String taskName = faker.job().title();
        String taskDescription = faker.lorem().sentence();
        Task task = new Task(0, projectId, taskName, taskDescription, faker.number().numberBetween(1, 3), false, true, null);
        taskDAO.createTask(task);
        return task;
    }

    @Test
    public void testCreateAndReadTask() throws SQLException {
        Project project1 = createRandomProject(1);
        Project project2 = createRandomProject(1);

        int first_id = projectDAO.readProjects(1).get(0).getProjectId();
        int second_id = projectDAO.readProjects(1).get(1).getProjectId();

        createRandomTask(first_id);
        createRandomTask(second_id);

        List<Task> tasks1 = taskDAO.readTasks(first_id);
        assertEquals(1, tasks1.size());
        assertNotNull(tasks1.get(0).getName());

        List<Task> tasks2 = taskDAO.readTasks(second_id);
        assertEquals(1, tasks2.size());
        assertNotNull(tasks2.get(0).getName());
    }

    @Test
    public void testUpdateTask() throws SQLException {
    }

    @Test
    public void testDeleteTask() throws SQLException {
    }
}
