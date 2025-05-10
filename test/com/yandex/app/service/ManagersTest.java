package com.yandex.app.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {
    @Test
    void managerShouldReturnInitializedTaskManager() {
        Assertions.assertNotNull(Managers.getDefault());
    }
    @Test
    void managerShouldReturnInitializedHistoryManager() {
        Assertions.assertNotNull(Managers.getDefaultHistory());
    }
}