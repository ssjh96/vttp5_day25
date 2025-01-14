package vttp5.paf.day25_producer.model;

public class Todo {
    
    private int id;
    private String taskName;

    public Todo() {
    }
    public Todo(int id, String taskName) {
        this.id = id;
        this.taskName = taskName;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    
}
