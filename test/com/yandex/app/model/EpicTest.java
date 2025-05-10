package com.yandex.app.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    @Test
    void epicCannotBeItsOwnSubtask() {
        int testId = 100;
        Epic epic = new Epic("testName", "testDescription");
        epic.setTaskId(testId);
        Subtask subtask = new Subtask("testSubtaskName", "testSubtaskDescription", epic.getTaskId());
        subtask.setTaskId(testId);
        epic.addSubtaskId(subtask.getTaskId());

        Assertions.assertFalse(epic.getSubtaskIds().contains(testId));
    }

    @Test
    void epicsWithSameIdEqual() {
        int epicTestId = 100;
        Epic epic1 = new Epic("testName1", "testDescription1");
        epic1.setTaskId(epicTestId);
        Epic epic2 = new Epic("testName2", "testDescription2");
        epic2.setTaskId(epicTestId);

        Assertions.assertEquals(epic1, epic2);

    }
}