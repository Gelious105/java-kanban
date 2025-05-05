package com.yandex.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import com.yandex.app.model.*;

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
    // com.yandex.app.model.Task methods
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
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

    // com.yandex.app.model.Subtask methods
    public ArrayList<Task> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public void deleteAllSubtasks() {
        ArrayList<Integer> subtasksIds = new ArrayList<>(subtasks.keySet());
        for (int id : subtasksIds) {
            tasks.remove(id);
        }
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.clearSubtaskIds();
            updateEpicStatus(epic);
        }
    }

    public Subtask getSubtaskById(int subtaskId) {
        return subtasks.get(subtaskId);
    }

    public void addSubtask(Subtask newSubtask) {
        newSubtask.setTaskId(++global_id_counter);
        subtasks.put(global_id_counter, newSubtask);
        tasks.put(global_id_counter, newSubtask);
        int epicId = newSubtask.getEpicParentId();
        Epic epic = epics.get(epicId);
        if (epic != null) {
            epic.addSubtaskId(newSubtask.getTaskId());
            updateEpicStatus(epic);
        }
    }

    public void updateSubtask(Subtask updatedSubtask) {
        subtasks.put(updatedSubtask.getTaskId(), updatedSubtask);
        Epic epic = epics.get(updatedSubtask.getEpicParentId());
        if (epic != null) {
            updateEpicStatus(epic);
        }
        tasks.put(updatedSubtask.getTaskId(), updatedSubtask);
    }

    public void deleteSubtaskById(int subtaskId) {
        Subtask subtask = subtasks.get(subtaskId);
        if (subtask != null) {
            int epicId = subtask.getEpicParentId();
            Epic epic = epics.get(epicId);
            if (epic != null) {
                epic.removeSubtaskId(subtaskId);
                updateEpicStatus(epic);
            }
            subtasks.remove(subtaskId);
            tasks.remove(subtaskId);
        }

    }

    // com.yandex.app.model.Epic  methods
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public void deleteAllEpics() {
        for (Epic epic : epics.values()) {
            tasks.remove(epic.getTaskId());
            for (int subtaskId : epic.getSubtaskIds()) {
                tasks.remove(subtaskId);
            }
        }
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
        tasks.put(global_id_counter, newEpic);
    }

    public void updateEpic(Epic updatedEpic) {
        updateEpicStatus(updatedEpic);
        epics.put(updatedEpic.getTaskId(), updatedEpic);
        tasks.put(updatedEpic.getTaskId(), updatedEpic);
    }

    public void deleteEpicById(int epicId) {
        if (epics.containsKey(epicId)) {
            Epic epic = epics.get(epicId);
            ArrayList<Integer> epicSubtaskIds = new ArrayList<>(epic.getSubtaskIds());
            for (int subtaskId : epicSubtaskIds) {
                subtasks.remove(subtaskId);
                tasks.remove(subtaskId);
            }
            epic.clearSubtaskIds();
            epics.remove(epicId);
            tasks.remove(epicId);
        }
    }

    // Additional methods
    public ArrayList<Subtask> getEpicSubtasks(int epicId) {
        ArrayList<Subtask> epicSubtasks = new ArrayList<>();
        if (epics.containsKey(epicId)) {
            Epic epic = epics.get(epicId);
            for (int subtaskId : epic.getSubtaskIds()) {
                Subtask subtask = subtasks.get(subtaskId);
                if (subtask != null) {
                    epicSubtasks.add(subtask);
                }
            }
        }
        return epicSubtasks;
    }

    private void updateEpicStatus(Epic epic) {
        if (epic.getSubtaskIds().isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }
        int counterNew = 0;
        int counterDone = 0;
        for (int subtaskId : epic.getSubtaskIds()) {
            Subtask subtask = subtasks.get(subtaskId);
            if (subtask == null) {
                continue;
            } else if (subtask.getStatus().equals(Status.NEW)) {
                counterNew++;
            } else if (subtask.getStatus().equals(Status.DONE)) {
                counterDone++;
            }
        }
        if (counterNew == epic.getSubtaskIds().size()) {
            epic.setStatus(Status.NEW);
        } else if (counterDone == epic.getSubtaskIds().size()) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

}
