package com.yandex.app.service;

import com.yandex.app.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    @Test
    void inMemoryTaskManagerShouldAddDifferentTypesOfTasksAndGetDifferentTaskTypesById() {
        TaskManager manager = Managers.getDefault();

        Task task1 = new Task("Задача 1", "Описание задачи 1");
        manager.addTask(task1);
        int task1Id = task1.getTaskId();

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        manager.addEpic(epic1);
        int epic1Id = epic1.getTaskId();

        Subtask subtask1 = new Subtask("Подзадача1 эпика1", "Описание подзадачи1 эпика1", epic1.getTaskId());
        manager.addSubtask(subtask1);
        int subtask1Id = subtask1.getTaskId();

        Assertions.assertEquals(manager.getTaskById(task1Id), task1);
        Assertions.assertEquals(manager.getSubtaskById(subtask1Id), subtask1);
        Assertions.assertEquals(manager.getEpicById(epic1Id), epic1);
    }

    @Test
    void shouldNotConflictManualIdSetAndManagerIdSetForAllTaskTypes() {
        TaskManager manager = new InMemoryTaskManager();

        Task task1 = new Task("testName1", "testDescription1");
        task1.setTaskId(100);
        manager.addTask(task1);

        Task task2 = new Task("testName2", "testDescription2");
        manager.addTask(task2);

        Epic epic1 = new Epic("epicName1", "epicDescription1");
        epic1.setTaskId(100); // вручную заданный ID (будет переопределён!)
        manager.addEpic(epic1);

        Epic epic2 = new Epic("epicName2", "epicDescription2");
        manager.addEpic(epic2);

        Epic epic = new Epic("parentEpic", "desc");
        manager.addEpic(epic);
        int epicId = epic.getTaskId();

        Subtask sub1 = new Subtask("subName1", "subDesc1", epicId);
        sub1.setTaskId(100); // вручную заданный ID
        manager.addSubtask(sub1);

        Subtask sub2 = new Subtask("subName2", "subDesc2", epicId);
        manager.addSubtask(sub2);

        Assertions.assertEquals(manager.getTaskById(task1.getTaskId()), task1);
        Assertions.assertEquals(manager.getTaskById(task2.getTaskId()), task2);
        Assertions.assertNotEquals(task1, task2);

        Assertions.assertEquals(manager.getSubtaskById(sub1.getTaskId()), sub1);
        Assertions.assertEquals(manager.getSubtaskById(sub2.getTaskId()), sub2);
        Assertions.assertNotEquals(sub1, sub2);

        Assertions.assertEquals(manager.getEpicById(epic1.getTaskId()), epic1);
        Assertions.assertEquals(manager.getEpicById(epic2.getTaskId()), epic2);
        Assertions.assertNotEquals(epic1, epic2);
    }
    @Test
    void shouldNotChangeTaskFieldsAfterAddingToManager() {
        TaskManager manager = new InMemoryTaskManager();

        Task task1 = new Task("testName", "testDescription");
        task1.setStatus(Status.IN_PROGRESS);

        String name = task1.getName();
        String description = task1.getDescription();
        Status status = task1.getStatus();
        TaskType type = task1.getTaskType();

        manager.addTask(task1);
        Task task2 = manager.getTaskById(task1.getTaskId());

        Assertions.assertEquals(name, task2.getName());
        Assertions.assertEquals(description, task2.getDescription());
        Assertions.assertEquals(status, task2.getStatus());
        Assertions.assertEquals(type, task2.getTaskType());
    }


}