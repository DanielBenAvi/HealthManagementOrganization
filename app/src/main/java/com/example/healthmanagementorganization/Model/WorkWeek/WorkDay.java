package com.example.healthmanagementorganization.Model.WorkWeek;

public class WorkDay {
    private int startTime;
    private int endTime;
    private Boolean isVacation = false;

    public WorkDay(int startTime, int endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.isVacation = false;
    }

    public WorkDay(Boolean isVacation) {
        this.isVacation = isVacation;
    }

    // getters

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }


    public Boolean getVacation() {
        return isVacation;
    }



}
