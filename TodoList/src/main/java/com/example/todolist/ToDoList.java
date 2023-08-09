package com.example.todolist;

import java.util.ArrayList;

public class ToDoList {

    private String name;
    private final ArrayList<List> listArrayList;

    private String password;

    public ToDoList(String name, String password) {
        this.name = name;
        this.password = password;
        this.listArrayList = new ArrayList<List>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void addList(String name) {
        listArrayList.add(new List(name));
    }

    public ArrayList<List> getListArrayList() {
        return listArrayList;
    }

    @Override
    public String toString() {
        return name;
    }
}
