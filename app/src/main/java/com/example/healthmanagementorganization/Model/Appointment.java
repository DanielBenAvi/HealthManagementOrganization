package com.example.healthmanagementorganization.Model;

import java.util.Calendar;

public class Appointment {
    private String appointmentID;
    private String patientID;
    private String doctorID;
    private Calendar date;

    /**
     * constructor
     */
    public Appointment() {
    }

    private Appointment setAppointmentID() {
        String str = "";
        str += date.get(Calendar.DAY_OF_MONTH);
        str += date.get(Calendar.MONTH);
        str += date.get(Calendar.YEAR);
        str += date.get(Calendar.HOUR);
        str += date.get(Calendar.MINUTE);
        str += doctorID;
        this.appointmentID = str;
        return this;
    }

    public String getAppointmentID() {
        return appointmentID;
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

    public Calendar getDate() {
        return date;
    }

    public Appointment setDate(Calendar date) {
        this.date = date;
        return this;
    }


}
