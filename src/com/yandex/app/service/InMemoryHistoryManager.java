package com.yandex.app.service;

import com.yandex.app.model.*;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{

    private final List<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        if (history.size() == 10) {
            history.removeFirst();
        }
        history.add(task);
    }

    @Override
    public void remove(int id) {
        Task taskRemove = null;
        for (Task task : history) {
            if (task.getTaskId() == id) {
                taskRemove = task;
                break;
            }
        }

        if (taskRemove != null) {
            history.remove(taskRemove);
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }
}
