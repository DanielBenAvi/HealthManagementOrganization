package com.example.healthmanagementorganization.Model;

import java.util.Calendar;

public class Appointment {
    private String patientID;
    private String doctorID;
    private Calendar calendar;

    /**
     * constructor
     */
    public Appointment() {
    }


    public String getPatientID() {
        return patientID;
    }

    public Appointment setPatientID(String patientID) {
        this.patientID = patientID;
        return this;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public Appointment setDoctorID(String doctorID) {
        this.doctorID = doctorID;
        return this;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public Appointment setCalendar(Calendar calendar) {
        this.calendar = calendar;
        return this;
    }


}
