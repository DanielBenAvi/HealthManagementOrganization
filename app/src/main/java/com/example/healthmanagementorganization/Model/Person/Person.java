package com.example.healthmanagementorganization.Model.Person;

import com.example.healthmanagementorganization.Model.Appointment;
import com.example.healthmanagementorganization.Model.MedicineRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Person {
    private String uid = "";
    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private String phone = "";
    private List<Appointment> appointments = new ArrayList<>();
    private Map<String, MedicineRequest> requests = new HashMap<>();


    public Person() {
    }

    public String getUid() {
        return uid;
    }

    public Person setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Person setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Person setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Person setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Person setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public Person setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
        return this;
    }


    public Map<String, MedicineRequest> getRequests() {
        return requests;
    }

    public Person setRequests(Map<String, MedicineRequest> requests) {
        this.requests = requests;
        return this;
    }

    @Override
    public String toString() {
        return "Person{" + "uid='" + uid + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", email='" + email + '\'' + ", phone='" + phone + '\'' + '}';
    }


}
