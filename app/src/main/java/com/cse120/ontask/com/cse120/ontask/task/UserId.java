package com.cse120.ontask.com.cse120.ontask.task;

import java.io.Serializable;

public class UserId implements Serializable{

    private static final long serialVersionUID = 687987546843515L;

    private int user_id;

    private int GenerateId(){
        // Generate Id
        // Random? Incrementing?
        return 69;
    }

    public UserId(){
        user_id = GenerateId();
    }

    public int GetId(){
        return user_id;
    }
}