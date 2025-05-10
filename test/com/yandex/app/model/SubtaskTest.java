package com.yandex.app.model;

import com.yandex.app.service.InMemoryTaskManager;
import com.yandex.app.service.Managers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SubtaskTest {
    @Test
    void subtaskCannotBeItsOwnEpic() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        Epic epic = new Epic("testName1", "testDescription1");
        manager.addEpic(epic);
        int epicId = epic.getTaskId();
        Subtask subtask = new Subtask("testName2", "testDescriptio2", epicId);
        subtask.setTaskId(epicId);

        manager.addSubtask(subtask);

        Assertions.assertNull(manager.getSubtaskById(epicId));

    }

    @Test
    void subtasksWithSameIdEqual() {
        int taskTestId = 100;
        Subtask subtask1 = new Subtask("testName1", "testDescription1", 0);
        subtask1.setTaskId(taskTestId);
        Subtask subtask2 = new Subtask("testName2", "testDescription2", 0);
        subtask2.setTaskId(taskTestId);

        Assertions.assertEquals(subtask1, subtask2);
    }

}