public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Задача 1", "Описание задачи 1");
        taskManager.addTask(task1);
        Task task2 = new Task("Задача 2", "Описание задачи 2");
        taskManager.addTask(task2);

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        taskManager.addEpic(epic1);

        Subtask subtask1 = new Subtask("Подзадача1 эпика1", "Описание подзадачи1 эпика1", epic1);
        Subtask subtask2 = new Subtask("Подзадача2 эпика1", "Описание подзадачи2 эпика1", epic1);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        taskManager.addEpic(epic2);

        Subtask subtask3 = new Subtask("Подзадача эпика2", "Описание подзадачи эпика2", epic2);
        taskManager.addSubtask(subtask3);

        System.out.println("Изначальные списки:");
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllSubtasks());
        System.out.println(taskManager.getAllEpics());

        task1.setStatus(Status.IN_PROGRESS);
        task2.setStatus(Status.DONE);
        taskManager.updateTask(task1);
        taskManager.updateTask(task2);

        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        subtask3.setStatus(Status.IN_PROGRESS);
        taskManager.updateSubtask(subtask1);
        taskManager.updateSubtask(subtask2);
        taskManager.updateSubtask(subtask3);

        System.out.println("После обновления статусов:");
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllSubtasks());
        System.out.println(taskManager.getAllEpics());

        taskManager.deleteTaskById(task1.getTaskId());
        taskManager.deleteEpicById(epic1.getTaskId());

        System.out.println("После удаления задачи 1 и эпика 1:");
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllSubtasks());
        System.out.println(taskManager.getAllEpics());
    }
}
