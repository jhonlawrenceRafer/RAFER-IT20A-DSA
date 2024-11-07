package todolist;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Stack;

public class TodoListManager {
    LinkedList<String> todoList = new LinkedList<>();
    LinkedList<String> completedTasks = new LinkedList<>();
    Stack<String> undoStack = new Stack<>();

    public static void main(String[] args) {
        new TodoListManager().run();
    }
    public void run() {
        String[] options = {"Add Task", "Mark Task as Done", "Undo", "View To-Do List", "View Completed Tasks", "Exit"};
        boolean running = true;

        while (running) {
            int choice = JOptionPane.showOptionDialog(null, "Choose an option", "To-Do List Manager",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                    case 0:
                        addTask();
                        break;
                    case 1:
                        markTaskAsDone();
                         break;
                     case 2:
                        undo();
                         break;
                    case 3:
                        viewList(todoList, "To-Do List");
                        break;
                    case 4:
                        viewList(completedTasks, "Completed Tasks");
                        break;
                    case 5:
                        showExitMessage();
                        running = false;
                        break;
               }

        }
    }
    public void addTask() {
        String task = JOptionPane.showInputDialog("Enter task:");
        if (task != null && !task.trim().isEmpty()) {
            todoList.add(task);
            undoStack.push("add:" + task);
            JOptionPane.showMessageDialog(null, "Task added.");
        }
    }
    public void markTaskAsDone() {
        if (todoList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tasks mark as done.");
            return;
        }
        String taskNum = JOptionPane.showInputDialog("To-Do List:\n" + listToString(todoList) + "\nEnter task number:");
        try {
            int num = Integer.parseInt(taskNum) - 1;
            if (num >= 0 && num < todoList.size()) {
                String task = todoList.remove(num);
                completedTasks.add(task);
                undoStack.push("done:" + task);
                JOptionPane.showMessageDialog(null, "Task marked as done.");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid task number.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input.");
        }
    }
    public void undo() {
        if (undoStack.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No result to undo.");
            return;
        }
        String lastAction = undoStack.pop();
        String[] parts = lastAction.split(":", 2);
        String action = parts[0], task = parts[1];

        if (action.equals("add")) {
            todoList.remove(task);
        } else if (action.equals("done")) {
            completedTasks.remove(task);
            todoList.add(task);
        }
        JOptionPane.showMessageDialog(null, "Undo task: " + task);
    }
    public void viewList(LinkedList<String> list, String title) {
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(null, title + " is empty.");
        } else {
            JOptionPane.showMessageDialog(null, title + ":\n" + listToString(list));
        }
    }
    public String listToString(LinkedList<String> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(i + 1).append(". ").append(list.get(i)).append("\n");
        }
        return sb.toString();
    }
    private void showExitMessage() {
        JOptionPane.showMessageDialog(null, "Bye Bye!");
    }
}
