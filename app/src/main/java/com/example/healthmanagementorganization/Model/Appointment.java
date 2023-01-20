package com.example.healthmanagementorganization.Model;

public class Appointment {
    private String patientID;
    private String doctorID;
    private Long date;
    private int hour;


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

    public Long getDate() {
        return date;
    }

    public Appointment setDate(Long date) {
        this.date = date;
        return this;
    }

    public int getHour() {
        return hour;
    }

    public Appointment setHour(int hour) {
        this.hour = hour;
        return this;
    }
}
