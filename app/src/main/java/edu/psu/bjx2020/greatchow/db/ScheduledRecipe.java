package edu.psu.bjx2020.greatchow.db;

public class ScheduledRecipe {

    private int year;
    private int month;
    private int dayOfMonth;
    private String ownerID;
    private String id;
    private String name;

    public ScheduledRecipe() {

    }

    public ScheduledRecipe(int year, int month, int dayOfMonth, String ownerID, String id, String name) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.ownerID = ownerID;
        this.id = id;
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName(){return name;}

    public void setName(String name){this.name = name;}

    @Override
    public String toString() {
        return "ScheduledRecipe{" +
                "year=" + year +
                ", month=" + month +
                ", dayOfWeek=" + dayOfMonth +
                ", ownerID='" + ownerID + '\'' +
                ", id='" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
