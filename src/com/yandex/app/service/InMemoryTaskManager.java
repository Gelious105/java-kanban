package com.yandex.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yandex.app.model.*;

public class InMemoryTaskManager implements TaskManager {
    public int global_id_counter;
    public HashMap<Integer, Task> tasks;
    public HashMap<Integer, Subtask> subtasks;
    public HashMap<Integer, Epic> epics;
    public HistoryManager historyWatchedTasks;

    public InMemoryTaskManager() {
        this.global_id_counter = 0;
        this.tasks = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.historyWatchedTasks = Managers.getDefaultHistory();
    }
    // com.yandex.app.model.Task methods
    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public Task getTaskById(int taskId) {
        Task task = tasks.get(taskId);
        historyWatchedTasks.add(task);
        return task;
    }

    @Override
    public void addTask(Task newTask) {
        newTask.setTaskId(getNextFreeId());
        tasks.put(newTask.getTaskId(), newTask);
    }

    @Override
    public void updateTask(Task updatedTask) {
        tasks.put(updatedTask.getTaskId(), updatedTask);
    }

    @Override
    public void deleteTaskById(int taskId) {
        tasks.remove(taskId);
    }

    // com.yandex.app.model.Subtask methods
    @Override
    public ArrayList<Task> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
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

    @Override
    public Subtask getSubtaskById(int subtaskId) {
        Subtask subtask = subtasks.get(subtaskId);
        historyWatchedTasks.add(subtask);
        return subtask;
    }

    @Override
    public void addSubtask(Subtask newSubtask) {
        newSubtask.setTaskId(getNextFreeId());
        subtasks.put(newSubtask.getTaskId(), newSubtask);
        tasks.put(newSubtask.getTaskId(), newSubtask);
        int epicId = newSubtask.getEpicParentId();
        Epic epic = epics.get(epicId);
        if (epic != null) {
            epic.addSubtaskId(newSubtask.getTaskId());
            updateEpicStatus(epic);
        }
    }

    @Override
    public void updateSubtask(Subtask updatedSubtask) {
        subtasks.put(updatedSubtask.getTaskId(), updatedSubtask);
        Epic epic = epics.get(updatedSubtask.getEpicParentId());
        if (epic != null) {
            updateEpicStatus(epic);
        }
        tasks.put(updatedSubtask.getTaskId(), updatedSubtask);
    }

    @Override
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
    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
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

    @Override
    public Epic getEpicById(int epicId) {
        Epic epic = epics.get(epicId);
        historyWatchedTasks.add(epic);
        return epic;
    }

    @Override
    public void addEpic(Epic newEpic) {
        newEpic.setTaskId(getNextFreeId());
        newEpic.setStatus(Status.NEW);
        epics.put(newEpic.getTaskId(), newEpic);
        tasks.put(newEpic.getTaskId(), newEpic);
    }

    @Override
    public void updateEpic(Epic updatedEpic) {
        updateEpicStatus(updatedEpic);
        epics.put(updatedEpic.getTaskId(), updatedEpic);
        tasks.put(updatedEpic.getTaskId(), updatedEpic);
    }

    @Override
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
    @Override
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

    @Override
    public List<Task> getHistory() {
        return historyWatchedTasks.getHistory();
    }

    private int getNextFreeId() {
        while (tasks.containsKey(global_id_counter + 1)) {
            global_id_counter++;
        }
        return ++global_id_counter;
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
