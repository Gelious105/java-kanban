import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    public int global_id_counter;
    public HashMap<Integer, Task> tasks;
    public HashMap<Integer, Subtask> subtasks;
    public HashMap<Integer, Epic> epics;

    public TaskManager() {
        this.global_id_counter = 0;
        this.tasks = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.epics = new HashMap<>();
    }
    // Task methods
    public HashMap<Integer, Task> getAllTasks() {
        return tasks;
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public Task getTaskById(int taskId) {
        return tasks.get(taskId);
    }

    public void addTask(Task newTask) {
        newTask.setTaskId(++global_id_counter);
        tasks.put(global_id_counter, newTask);
    }

    public void updateTask(Task updatedTask) {
        tasks.put(updatedTask.getTaskId(), updatedTask);
    }

    public void deleteTaskById(int taskId) {
        tasks.remove(taskId);
    }

    // Subtask methods
    public HashMap<Integer, Subtask> getAllSubtasks() {
        return subtasks;
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.setSubtasks(new ArrayList<Subtask>());
            updateEpicStatus(epic);
        }
    }

    public Subtask getSubtaskById(int subtaskId) {
        return subtasks.get(subtaskId);
    }

    public void addSubtask(Subtask newSubtask) {
        newSubtask.setTaskId(++global_id_counter);
        subtasks.put(global_id_counter, newSubtask);
        ArrayList<Subtask> newSubtasks = newSubtask.getEpicParent().getSubtasks();
        newSubtasks.add(newSubtask);
        newSubtask.getEpicParent().setSubtasks(newSubtasks);
    }

    public void updateSubtask(Subtask updatedSubtask) {
        subtasks.put(updatedSubtask.getTaskId(), updatedSubtask);
        updateEpicStatus(updatedSubtask.getEpicParent());
    }

    public void deleteSubtaskById(int subtaskId) {
        if (subtasks.containsKey(subtaskId)) {
            Epic epic = subtasks.get(subtaskId).getEpicParent();
            epic.getSubtasks().remove(subtasks.get(subtaskId));
            epic.setSubtasks(epic.getSubtasks());
            subtasks.remove(subtaskId);
            updateEpicStatus(epic);
        }
    }

    // Epic  methods
    public HashMap<Integer, Epic> getAllEpics() {
        return epics;
    }

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public Epic getEpicById(int epicId) {
        return epics.get(epicId);
    }

    public void addEpic(Epic newEpic) {
        newEpic.setTaskId(++global_id_counter);
        newEpic.setStatus(Status.NEW);
        epics.put(global_id_counter, newEpic);
    }

    public void updateEpic(Epic updatedEpic) {
        updateEpicStatus(updatedEpic);
        epics.put(updatedEpic.getTaskId(), updatedEpic);
    }

    public void deleteEpicById(int epicId) {
        if (epics.containsKey(epicId)) {
            ArrayList<Subtask> epicSubtasks = epics.get(epicId).getSubtasks();
            for (Subtask subtask : epicSubtasks) {
                subtasks.remove(subtask.getTaskId());
            }
            epics.remove(epicId);
        }
    }

    // Additional methods
    public ArrayList<Subtask> getEpicSubtasks(int epicId) {
        return epics.get(epicId).getSubtasks();
    }

    private void updateEpicStatus(Epic epic) {
        if (epic.getSubtasks().isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }
        int counterNew = 0;
        int counterDone = 0;
        for (Subtask subtask : epic.getSubtasks()) {
            if (subtask.getStatus().equals(Status.NEW)) {
                counterNew++;
            } else if (subtask.getStatus().equals(Status.DONE)) {
                counterDone++;
            }
        }
        if (counterNew == epic.getSubtasks().size()) {
            epic.setStatus(Status.NEW);
        } else if (counterDone == epic.getSubtasks().size()) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

}
