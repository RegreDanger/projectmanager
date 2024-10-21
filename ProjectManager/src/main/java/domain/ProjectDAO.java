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
        String sqlProject = "INSERT INTO project (userID, name, description, progress, status, date_start, date_end) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmtProject = connection.prepareStatement(sqlProject, Statement.RETURN_GENERATED_KEYS)) {
            stmtProject.setInt(1, project.getUserID());
            stmtProject.setString(2, project.getName());
            stmtProject.setString(3, project.getDescription());
            stmtProject.setInt(4, project.getProgress());
            stmtProject.setBoolean(5, project.isStatus());
            stmtProject.setDate(6, new Date(project.getDateStart().getTime()));
            stmtProject.setDate(7, new Date(project.getDateEnd().getTime()));
            stmtProject.executeUpdate();
        }
    }

    @Override
    public List<Project> readProjects(int userID) throws SQLException {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM project WHERE userID = ? ORDER BY projectID";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Project project = new Project(
                        rs.getInt("userID"),
                        rs.getInt("projectID"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("progress"),
                        rs.getBoolean("status"),
                        rs.getDate("date_start"),
                        rs.getDate("date_end")
                    );
                    projects.add(project);
                }
            }
        }
        return projects;
    }

    @Override
    public void updateProject(Project project) throws SQLException {
        String sql = "UPDATE project SET name = ?, description = ?, progress = ?, status = ?, date_start = ?, date_end = ? WHERE projectID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, project.getName());
            stmt.setString(2, project.getDescription());
            stmt.setInt(3, project.getProgress());
            stmt.setBoolean(4, project.isStatus());
            stmt.setDate(5, new Date(project.getDateStart().getTime()));
            stmt.setDate(6, new Date(project.getDateEnd().getTime()));
            stmt.setInt(7, project.getProjectId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteProject(int projectId) throws SQLException {
        String sql = "DELETE FROM project WHERE projectID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            stmt.executeUpdate();
        }
    }
}
