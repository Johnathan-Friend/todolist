package com.example.todolist;

public class Task {

    private String name;
    private String date;

    public Task(String name, String date){
        this.name = name;
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return date + "\t" + name;
    }
}
