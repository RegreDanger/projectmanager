package domain.utils.interfaces;

import java.sql.SQLException;
import java.util.List;

import domain.Project;

public interface IProjectDAO {
    void createProject(Project project) throws SQLException;
    List<Project> readProjects() throws SQLException;
    void updateProject(Project project) throws SQLException;
    void deleteProject(int projectId) throws SQLException;
}

