public class Subtask extends Task{
    protected Epic epicParent;

    public Subtask(String name, String description, Epic epicParent) {
        super(name, description);
        this.epicParent = epicParent;
        this.taskType = TaskType.SUBTASK;
    }

    public void setEpicParent(Epic epicParent) {
        this.epicParent = epicParent;
    }

    public Epic getEpicParent() {
        return epicParent;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicParent=" + epicParent +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taskId=" + taskId +
                ", status=" + status +
                ", taskType=" + taskType +
                '}';
    }
}
