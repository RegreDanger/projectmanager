package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase que representa un Proyecto.
 * Un proyecto tiene un ID, nombre, descripción, progreso, estado, fecha de inicio, fecha de finalización y una lista de tareas asociadas.
 */
public class Project {
    private final int projectId;     // ID único del proyecto (Auto Increment)
    private String name;       // Nombre del proyecto
    private String description; // Descripción del proyecto
    private int progress;      // Progreso del proyecto (0 a 100)
    private boolean status;    // Estado del proyecto (activo/inactivo)
    private Date dateStart;    // Fecha de inicio del proyecto
    private Date dateEnd;      // Fecha de finalización del proyecto
    private List<Task> tasks;  // Lista de tareas asociadas al proyecto

    // Constructor por defecto
    public Project() {
        this.projectId = 0; // O un valor adecuado si se requiere
        this.name = "";
        this.description = "";
        this.progress = 0;
        this.status = false; // Por defecto inactivo
        this.dateStart = null;
        this.dateEnd = null;
        this.tasks = new ArrayList<>(); // O inicializar como nueva lista si se prefiere
    }

    // Constructor completo
    public Project(int projectId, String name, String description, int progress, boolean status, Date dateStart, Date dateEnd, List<Task> tasks) {
        this.projectId = projectId;
        setName(name); // Usar setter para validación
        setDescription(description);
        setProgress(progress);
        setStatus(status);
        setDateStart(dateStart);
        setDateEnd(dateEnd);
        this.tasks = tasks;
    }

    // Getters y Setters
    public int getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del proyecto no puede estar vacío.");
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
    	    throw new IllegalArgumentException("La fecha de finalización no puede ser anterior a la fecha de inicio.");
    	}
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        if (dateStart != null && dateEnd != null && dateEnd.before(dateStart)) {
            throw new IllegalArgumentException("La fecha de finalización no puede ser anterior a la fecha de inicio.");
        }
        this.dateEnd = dateEnd; // Este campo puede ser null
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks; // Puede ser null o nueva lista
    }

    // Métodos adicionales para manipular las tareas
    public void addTask(Task task) {
        if (task != null && !this.tasks.contains(task)) {
            this.tasks.add(task);
        }
    }

    public void removeTask(Task task) {
        if (this.tasks != null) {
            this.tasks.remove(task);
        }
    }
    
    public void removeTask(int taskId) {
        tasks.removeIf(task -> task.getTaskId() == taskId);
    }
    
    public void updateTask(int taskId, String name, String description) {
        for (Task task : tasks) {
            if (task.getTaskId() != 0 && task.getTaskId() == taskId) {
                task.setName(name);
                task.setDescription(description);
                break; // Salir del bucle una vez que se haya encontrado y actualizado la tarea
            }
        }
    }

    // Implementación de equals y hashCode
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
                ", tasks=" + tasks +
                '}';
    }
}
