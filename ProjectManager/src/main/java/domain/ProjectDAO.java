package domain;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import domain.utils.interfaces.IProjectDAO;

public class ProjectDAO implements IProjectDAO {
    private Connection connection;
    private static ProjectDAO instance;
    
    private ProjectDAO() throws SQLException {
    	connection = DatabaseConnection.getInstance().getConnection();
    }
    
    public static ProjectDAO getInstance() throws SQLException {
    	if (instance == null) {
            instance = new ProjectDAO();
        }
        return instance;
    }

    @Override
    public void createProject(Project project) throws SQLException {
        // Insertar el proyecto en la base de datos
        String sqlProject = "INSERT INTO project (name, description, progress, status, date_start, date_end) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmtProject = connection.prepareStatement(sqlProject, Statement.RETURN_GENERATED_KEYS)) {
            stmtProject.setString(1, project.getName());
            stmtProject.setString(2, project.getDescription());
            stmtProject.setInt(3, project.getProgress());
            stmtProject.setInt(4, project.isStatus() ? 1 : 0);
            stmtProject.setDate(5, new Date(project.getDateStart().getTime()));
            stmtProject.setDate(6, new Date(project.getDateEnd().getTime()));
            // Ejecutar la inserción del proyecto
            stmtProject.executeUpdate();
            // Obtener el ID generado del nuevo proyecto
            try (ResultSet generatedKeys = stmtProject.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int projectId = generatedKeys.getInt(1);
                    
                    // Insertar las tareas asociadas al proyecto
                    String sqlTask = "INSERT INTO task (projectID, name, description, status) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement stmtTask = connection.prepareStatement(sqlTask)) {
                        for (Task task : project.getTasks()) {
                            stmtTask.setInt(1, projectId); // Asignar el ID del proyecto a la tarea
                            stmtTask.setString(2, task.getName());
                            stmtTask.setString(3, task.getDescription());
                            stmtTask.setInt(4, task.isStatus() ? 1 : 0);
                            stmtTask.executeUpdate();
                        }
                    }
                }
            }
        }
    }


    @Override
    public List<Project> readProjects() throws SQLException {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT p.*, t.* FROM project p LEFT JOIN task t ON p.projectID = t.projectID ORDER BY p.projectID";
        
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            Project currentProject = null;
            int lastProjectID = -1;
            List<Task> tasks = null;
            
            while (rs.next()) {
                int projectId = rs.getInt("p.projectID");
                
                // Si se encuentra un nuevo proyecto, crea el anterior (si existe)
                if (currentProject == null || projectId != lastProjectID) {
                    if (currentProject != null) {
                        currentProject.setTasks(tasks);
                        projects.add(currentProject);
                    }
                    
                    // Crea el nuevo proyecto
                    currentProject = new Project(
                        rs.getInt("p.projectID"),
                        rs.getString("p.name"),
                        rs.getString("p.description"),
                        rs.getInt("p.progress"),
                        rs.getInt("p.status") == 0 ? false : true,
                        rs.getDate("p.date_start"),
                        rs.getDate("p.date_end"),
                        new ArrayList<>()
                    );
                    
                    tasks = new ArrayList<>(); // Inicializa la lista de tareas
                    lastProjectID = projectId; // Actualiza el ID del proyecto actual
                }
                
                // Añade las tareas si hay alguna
                if (rs.getInt("t.taskID") > 0) {
                    Task task = new Task(
                        rs.getInt("t.taskID"),
                        rs.getInt("t.projectID"),
                        rs.getString("t.name"),
                        rs.getString("t.description"),
                        rs.getInt("t.priority"),
                        rs.getInt("t.completed") == 0 ? false : true,
                        rs.getInt("t.status") == 0 ? false : true,
                        rs.getDate("t.date_max")
                    );
                    tasks.add(task);
                }
            }
            
            // Añade el último proyecto (si existe)
            if (currentProject != null) {
                currentProject.setTasks(tasks);
                projects.add(currentProject);
            }
        }
        return projects;
    }




    @Override
    public void updateProject(Project project) throws SQLException {
        /*String sql = "UPDATE Projects SET name = ?, description = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, project.getName());
            stmt.setString(2, project.getDescription());
            stmt.setInt(3, project.getProjectId());
            stmt.executeUpdate();
        }*/
    }

    @Override
    public void deleteProject(int projectId) throws SQLException {
        /*String sql = "DELETE FROM Projects WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            stmt.executeUpdate();
        }*/
    }
}

