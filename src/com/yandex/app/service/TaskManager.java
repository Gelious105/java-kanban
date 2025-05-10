package com.yandex.app.service;

import com.yandex.app.model.Epic;
import com.yandex.app.model.Subtask;
import com.yandex.app.model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    // com.yandex.app.model.Task methods
    ArrayList<Task> getAllTasks();

    void deleteAllTasks();

    Task getTaskById(int taskId);

    void addTask(Task newTask);

    void updateTask(Task updatedTask);

    void deleteTaskById(int taskId);

    // com.yandex.app.model.Subtask methods
    ArrayList<Task> getAllSubtasks();

    void deleteAllSubtasks();

    Subtask getSubtaskById(int subtaskId);

    void addSubtask(Subtask newSubtask);

    void updateSubtask(Subtask updatedSubtask);

    void deleteSubtaskById(int subtaskId);

    // com.yandex.app.model.Epic  methods
    ArrayList<Epic> getAllEpics();

    void deleteAllEpics();

    Epic getEpicById(int epicId);

    void addEpic(Epic newEpic);

    void updateEpic(Epic updatedEpic);

    void deleteEpicById(int epicId);

    // Additional methods
    ArrayList<Subtask> getEpicSubtasks(int epicId);

    List<Task> getHistory();
}
