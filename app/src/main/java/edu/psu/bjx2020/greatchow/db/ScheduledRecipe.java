package edu.psu.bjx2020.greatchow.db;

public class ScheduledRecipe {

    private int dayOfMonth;
    private int month;
    private int year;
    private int ownerID;
    private String id;

    public ScheduledRecipe() {

    }

    public ScheduledRecipe(int dayOfMonth, int month, int year, int ownerID, String id) {
        this.dayOfMonth = dayOfMonth;
        this.month = month;
        this.year = year;
        this.ownerID = ownerID;
        this.id = id;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ScheduledRecipe{" +
                "dayOfMonth=" + dayOfMonth +
                ", month=" + month +
                ", year=" + year +
                ", ownerID=" + ownerID +
                ", id='" + id + '\'' +
                '}';
    }
}
