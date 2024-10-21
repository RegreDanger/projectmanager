package domain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import domain.utils.interfaces.ITaskDAO;

public class TaskDAO implements ITaskDAO {
    private Connection connection;
    private static TaskDAO instance;

    private TaskDAO() throws SQLException {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static TaskDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new TaskDAO();
        }
        return instance;
    }

    @Override
    public void createTask(Task task) throws SQLException {
        String sqlTask = "INSERT INTO task (projectID, name, description, priority, completed, status, date_max) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmtTask = connection.prepareStatement(sqlTask, Statement.RETURN_GENERATED_KEYS)) {
            stmtTask.setInt(1, task.getProjectId());
            stmtTask.setString(2, task.getName());
            stmtTask.setString(3, task.getDescription());
            stmtTask.setInt(4, task.getPriority());
            stmtTask.setBoolean(5, task.isCompleted());
            stmtTask.setBoolean(6, task.isStatus());
            stmtTask.setDate(7, task.getDateMax() != null ? new Date(task.getDateMax().getTime()) : null);
            stmtTask.executeUpdate();
        }
    }
 
    @Override
    public List<Task> readTasks(int projectId) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM task WHERE projectID = ? ORDER BY taskID";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Task task = new Task(
                        rs.getInt("taskID"),
                        rs.getInt("projectID"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("priority"),
                        rs.getBoolean("completed"),
                        rs.getBoolean("status"),
                        rs.getDate("date_max")
                    );
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }

    @Override
    public void updateTask(Task task) throws SQLException {
        String sql = "UPDATE task SET name = ?, description = ?, priority = ?, completed = ?, status = ?, date_max = ? WHERE taskID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, task.getName());
            stmt.setString(2, task.getDescription());
            stmt.setInt(3, task.getPriority());
            stmt.setBoolean(4, task.isCompleted());
            stmt.setBoolean(5, task.isStatus());
            stmt.setDate(6, task.getDateMax() != null ? new Date(task.getDateMax().getTime()) : null);
            stmt.setInt(7, task.getTaskId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteTask(int taskId) throws SQLException {
        String sql = "DELETE FROM task WHERE taskID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, taskId);
            stmt.executeUpdate();
        }
    }
}
