package com.example.healthmanagementorganization.DataBase;

import com.example.healthmanagementorganization.Model.Appointment;
import com.example.healthmanagementorganization.Model.Person.Doctor;
import com.example.healthmanagementorganization.Model.Person.Patient;

import java.util.HashMap;

public class DataBase {
    private HashMap<String, Appointment> appointments = new HashMap<>();
    private HashMap<String, Doctor> doctors = new HashMap<>();
    private HashMap<String, Patient> patients = new HashMap<>();


    public DataBase() {
    }


    public boolean addAppointment(Appointment appointment) {
        if (appointment == null) {
            return false;
        }

        String appointmentID = appointment.getAppointmentID();
        if (appointments.containsKey(appointmentID)) {
            return false;
        }
        appointments.put(appointmentID, appointment);
        return true;
    }

    public boolean addDoctor(Doctor doctor) {
        if (doctor == null) {
            return false;
        }
        String doctorID = doctor.getUid();
        if (doctors.containsKey(doctorID)) {
            return false;
        }
        doctors.put(doctorID, doctor);
        return true;
    }

    public boolean addPatient(Patient patient) {
        if (patient == null) {
            return false;
        }
        String patientID = patient.getUid();
        if (patients.containsKey(patientID)) {
            return false;
        }
        patients.put(patientID, patient);
        return true;
    }

    public HashMap<String, Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(HashMap<String, Appointment> appointments) {
        this.appointments = appointments;
    }

    public HashMap<String, Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(HashMap<String, Doctor> doctors) {
        this.doctors = doctors;
    }

    public HashMap<String, Patient> getPatients() {
        return patients;
    }

    public void setPatients(HashMap<String, Patient> patients) {
        this.patients = patients;
    }


}
