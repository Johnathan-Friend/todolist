package com.example.todolist;

import java.util.ArrayList;

public class List {

    private String name;

    private final ArrayList<Task> taskArrayList;

    private ArrayList<Task> completedTaskArrayList;

    public List(String name) {
        this.name = name;
        this.taskArrayList = new ArrayList<Task>();
        this.completedTaskArrayList = new ArrayList<Task>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Task> getTaskArrayList() {
        return taskArrayList;
    }

    public void addTask(String name, String date) {
        taskArrayList.add(new Task(name, date));
    }

    public void addCompletedTask(Task task) {
        completedTaskArrayList.add(task);
    }

    public ArrayList<Task> getCompletedTaskArrayList() {
        return completedTaskArrayList;
    }

    @Override
    public String toString() {
        return name;
    }
}
