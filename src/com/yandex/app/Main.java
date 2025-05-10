package com.yandex.app;

import com.yandex.app.model.*;
import com.yandex.app.service.InMemoryTaskManager;
import com.yandex.app.service.Managers;
import com.yandex.app.service.TaskManager;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager manager = Managers.getDefault();

        Task task1 = new Task("Задача 1", "Описание задачи 1");
        manager.addTask(task1);
        Task task2 = new Task("Задача 2", "Описание задачи 2");
        manager.addTask(task2);

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        manager.addEpic(epic1);

        Subtask subtask1 = new Subtask("Подзадача1 эпика1", "Описание подзадачи1 эпика1", epic1.getTaskId());
        Subtask subtask2 = new Subtask("Подзадача2 эпика1", "Описание подзадачи2 эпика1", epic1.getTaskId());
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);

        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        manager.addEpic(epic2);

        Subtask subtask3 = new Subtask("Подзадача эпика2", "Описание подзадачи эпика2", epic2.getTaskId());
        manager.addSubtask(subtask3);

        System.out.println("Изначальные списки:");
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllSubtasks());
        System.out.println(manager.getAllEpics());

        task1.setStatus(Status.IN_PROGRESS);
        task2.setStatus(Status.DONE);
        manager.updateTask(task1);
        manager.updateTask(task2);

        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        subtask3.setStatus(Status.IN_PROGRESS);
        manager.updateSubtask(subtask1);
        manager.updateSubtask(subtask2);
        manager.updateSubtask(subtask3);

        System.out.println("После обновления статусов:");
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllSubtasks());
        System.out.println(manager.getAllEpics());

        manager.deleteTaskById(task1.getTaskId());
        manager.deleteEpicById(epic1.getTaskId());

        System.out.println("После удаления задачи 1 и эпика 1:");
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllSubtasks());
        System.out.println(manager.getAllEpics());

        printAllTasks(manager);
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }

        System.out.println("Эпики:");
        for (Epic epic : manager.getAllEpics()) {
            System.out.println(epic);
            for (Subtask subtask : manager.getEpicSubtasks(epic.getTaskId())) {
                System.out.println("  --> " + subtask);
            }
        }

        System.out.println("Подзадачи:");
        for (Task subtask : manager.getAllSubtasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }

}
