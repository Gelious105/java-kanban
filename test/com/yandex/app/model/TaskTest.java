package com.yandex.app.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    void tasksWithSameIdEqual() {
        int taskTestId = 100;
        Task task1 = new Task("testName1", "testDescription1");
        task1.setTaskId(taskTestId);
        Task task2 = new Task("testName2", "testDescription2");
        task2.setTaskId(taskTestId);

        Assertions.assertEquals(task1, task2);
    }

}