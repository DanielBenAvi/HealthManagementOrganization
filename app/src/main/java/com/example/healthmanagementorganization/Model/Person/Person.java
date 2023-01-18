package com.example.healthmanagementorganization.Model.Person;

import android.util.Log;

import com.example.healthmanagementorganization.Model.DataBase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Person {
    private String uid = "";
    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private String phone = "";
    private List<String> appointmentsIDs = new ArrayList<>();


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

    public List<String> getAppointmentsIDs() {
        return appointmentsIDs;
    }

    public Person setAppointmentsIDs(List<String> appointmentsIDs) {
        this.appointmentsIDs = appointmentsIDs;
        return this;
    }

    @Override
    public String toString() {
        return "Person{" + "uid='" + uid + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", email='" + email + '\'' + ", phone='" + phone + '\'' + ", appointmentsIDs=" + appointmentsIDs + '}';
    }
}
