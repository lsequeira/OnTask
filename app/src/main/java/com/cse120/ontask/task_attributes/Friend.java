package com.cse120.ontask.task_attributes;

public class Friend {

    private String name;
    private String id;

    public Friend(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Friend() {
        this("null", "null");
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }
}
