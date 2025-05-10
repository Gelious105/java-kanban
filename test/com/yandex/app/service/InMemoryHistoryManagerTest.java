package com.yandex.app.service;

import com.yandex.app.model.Status;
import com.yandex.app.model.Task;
import com.yandex.app.model.TaskType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    @Test
    void shouldNotChangeViewedTaskInHistory() {
        HistoryManager historyManager = new InMemoryHistoryManager();

        Task task1 = new Task("testName", "testDescription");
        task1.setStatus(Status.IN_PROGRESS);

        String name = task1.getName();
        String description = task1.getDescription();
        Status status = task1.getStatus();
        TaskType type = task1.getTaskType();

        historyManager.add(task1);
        List<Task> taskHistory = historyManager.getHistory();
        Task task2 = taskHistory.getLast();

        Assertions.assertEquals(name, task2.getName());
        Assertions.assertEquals(description, task2.getDescription());
        Assertions.assertEquals(status, task2.getStatus());
        Assertions.assertEquals(type, task2.getTaskType());
    }

}