package com.example.ubuntu.list_todo.controllers;
public class Task {
    private Integer id;
    private String title,body,date;
    Task(Integer id, String title, String date, String body){
        this.id =id;
        this.title=title;
        this.date= date;
        this.body=body;
    }
    public Integer getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDate() {
        return this.date;
    }

    public String getBody() {
        return this.body;
    }
}
