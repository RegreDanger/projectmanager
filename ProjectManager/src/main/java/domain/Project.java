package domain;

import java.util.Date;

public class Project {
	private final int projectId;
    private final int userID;
    private String name;
    private String description;
    private int progress;
    private boolean status;
    private Date dateStart;
    private Date dateEnd;
    
    public Project(int userID, int projectId, String name, String description, int progress, boolean status, Date dateStart, Date dateEnd) {
        this.userID = userID;
    	this.projectId = projectId;
        setName(name);
        setDescription(description);
        setProgress(progress);
        setStatus(status);
        setDateStart(dateStart);
        setDateEnd(dateEnd);
    }

    // Getters y Setters
    public int getUserID() {
    	return userID;
    }
    
    public int getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del proyecto no puede estar vac�o.");
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description; // Este campo puede ser null
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        if (progress < 0 || progress > 100) {
            throw new IllegalArgumentException("El progreso debe estar entre 0 y 100.");
        }
        this.progress = progress;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
    	if (dateEnd != null && (dateStart == null || dateEnd.before(dateStart))) {
    	    throw new IllegalArgumentException("La fecha de finalizaci�n no puede ser anterior a la fecha de inicio.");
    	}
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        if (dateStart != null && dateEnd != null && dateEnd.before(dateStart)) {
            throw new IllegalArgumentException("La fecha de finalizaci�n no puede ser anterior a la fecha de inicio.");
        }
        this.dateEnd = dateEnd; // Este campo puede ser null
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        Project project = (Project) o;
        return projectId == project.projectId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(projectId);
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", progress=" + progress +
                ", status=" + status +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                "}";
    }
}
