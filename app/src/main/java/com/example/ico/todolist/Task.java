package com.example.ico.todolist;

/**
 * Created by Vlado on 26.11.2017 г..
 */

public class Task {
    private long id;
    private String task;

    public Task() {
    }

    public Task(String task) {

        this.task = task;
    }

    public long getId() {
        return id;
    }



    public void setId(long id) {
        this.id = id;
    }

    public String getWord() {
        return task;
    }

    public void setWord(String task) {
        this.task = task;
    }
}
