package com.example.healthmanagementorganization.Model.WorkWeek;

import java.util.HashMap;

public class WorkWeek {
    private HashMap<Integer, WorkDay> workDays = new HashMap<Integer, WorkDay>();


    public WorkWeek() {
    }

    /**
     * @param dayOfWeek 1-7
     * @param workDay workday object
     * @return true if successful false if not
     */
    public boolean addWorkDay( int dayOfWeek, WorkDay workDay) {
        // check if day is valid
        if (dayOfWeek < 1 || dayOfWeek > 7) {
            return false;
        }

        // if workDay is null
        if (workDay == null) {
            return false;
        }
        // add workDay to workDays
        workDays.put(dayOfWeek, workDay);
        return true;

    }

    /**
     * get workDays
     * @return workDays
     */
    public HashMap<Integer, WorkDay> getWorkDays() {
        return workDays;
    }


}
