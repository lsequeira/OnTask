package com.cse120.ontask.com.cse120.ontask.task;
import java.io.Serializable;

public class Date implements Serializable{

    private static final long serialVersionUID = 6879816854546843515L;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    public Date() {
        year = 2000;
        month = 1;
        day = 1;
        hour = 0;
        minute = 1;
    }

    public Date( int year, int month, int day, int hour, int minute ) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    // Getters
    public int getYear() {
        return year;
    }
    public int getMonth() {
        return month;
    }
    public int getDay() {
        return day;
    }
    public int getHour() {
        return hour;
    }
    public int getMinute() {
        return minute;
    }
    // End Getters

    // Setters
    public void setYear( int year ) {
        this.year = year;
    }
    public void setMonth( int month ) {
        this.month = month;
    }
    public void setDay( int day ) {
        this.day = day;
    }
    public void setHour( int hour ) {
        this.hour = hour;
    }
    public void setMinute( int minute ) {
        this.minute = minute;
    }
    //End Setters
}
