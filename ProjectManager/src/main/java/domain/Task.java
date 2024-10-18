package domain;

import java.util.Date;

import domain.utils.enums.Priority;

/**
 * Clase que representa una Tarea dentro de un proyecto.
 * Una tarea tiene un nombre, descripción, prioridad, estado de completado, filtración y fecha de vencimiento.
 */
public class Task {
    private final int taskId;    // ID único de la tarea (autoincremental)
    private final int projectId;  // ID del proyecto al que pertenece
    private String name;       // Nombre de la tarea
    private String description; // Descripción de la tarea
    private int priority;  // Prioridad de tarea: 1, 2 o 3 según criterios
    private boolean completed;  // Para determinar si la tarea está o no terminada
    private boolean status;     // Para filtrar según criterios
    private Date dateMax;      // Fecha de vencimiento de la tarea

    // Constructor por defecto
    public Task() {
        this.taskId = 0;
        this.projectId = 0;
        this.name = "";
        this.description = "";
        this.priority = Priority.HIGH.getValue();
        this.completed = false;
        this.status = false;
        this.dateMax = null;
    }

    // Constructor completo
    public Task(int taskID, int projectId, String name, String description, int priority, boolean completed, boolean status, Date dateMax) {
    	this.taskId = taskID;
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.completed = completed;
        this.status = status;
        this.dateMax = dateMax;
    }

    // Getters y Setters
    public int getTaskId() {
        return taskId;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la tarea no puede estar vacío.");
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
    	if(priority < 1 || priority > 3) {
    		throw new IllegalArgumentException("Debe ser entre 1 y 3");
    	}
        this.priority = priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getDateMax() {
        return dateMax;
    }

    public void setDateMax(Date dateMax) {
        this.dateMax = dateMax; // Este campo puede ser null
    }

    public void markAsCompleted() {
        this.completed = true;
    }
    
    public void markAsIncomplete() {
    	this.completed = false;
    }

    public boolean isOverdue() {
    	if(dateMax == null) {
    		throw new NullPointerException("No se ha puesto una fecha máxima");
    	}
        Date currentDate = new Date();
        return dateMax != null && currentDate.after(this.dateMax);
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", projectId=" + projectId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", completed=" + completed +
                ", status=" + status +
                ", dateMax=" + dateMax +
                '}';
    }
}
