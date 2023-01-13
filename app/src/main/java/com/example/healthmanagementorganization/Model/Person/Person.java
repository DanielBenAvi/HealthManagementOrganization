package com.example.healthmanagementorganization.Model.Person;

import android.util.Log;

import java.util.HashSet;

public class Person {
    private String uid = "";
    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private String phone = "";
    private HashSet<String> appointmentsIDs = new HashSet<>();


    public Person() {
    }


    /**
     * add appointmentID to the set
     *
     * @param appointmentID the appointmentID to be added
     * @return true if the appointmentID is added successfully
     */
    public boolean addAppointmentID(String appointmentID) {
        return appointmentsIDs.add(appointmentID);
    }

    /**
     * remove appointmentID from the set
     *
     * @param appointmentID the appointmentID to be removed
     * @return true if the appointmentID is removed successfully
     */
    public boolean removeAppointmentID(String appointmentID) {
        return appointmentsIDs.remove(appointmentID);
    }

    /**
     * set user is that generate by firebase
     *
     * @param uid the uid of the person
     * @return the set of appointmentIDs
     */
    public Person setUid(String uid) {
        this.uid = uid;
        return this;
    }

    /**
     * set first name
     *
     * @param firstName the first name
     * @return null if the first name is null or empty, otherwise return this
     */
    public Person setFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            Log.d("Person", "setFirstName: first name is null or empty");
            return null;
        }
        // check if the name is valid
        if (!firstName.matches("[a-zA-Z]+")) {
            Log.d("Person", "setFirstName: first name is invalid");
            return null;
        }
        this.firstName = firstName;
        return this;
    }

    /**
     * set the last name
     *
     * @param lastName the last name
     * @return null if the name is invalid or the person object if the name is valid
     */
    public Person setLastName(String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            Log.d("Person", "setLastName: last name is null or empty");
            return null;
        }
        // check if the name is valid
        if (!lastName.matches("[a-zA-Z]+")) {
            Log.d("Person", "setLastName: last name is invalid");
            return null;
        }
        this.lastName = lastName;

        return this;
    }

    /**
     * set the email that is used to login
     *
     * @param email the email
     * @return null if the email is invalid or the person object if the email is valid
     */
    public Person setEmail(String email) {
        if (email == null || email.isEmpty()) {
            Log.d("Person", "setEmail: email is null or empty");
            return null;
        }
        // check if the email is valid else return null
        if (!email.matches("^(.+)@(.+)$")) {
            Log.d("Person", "setEmail: email is invalid");
            return null;
        }

        this.email = email;
        return this;
    }

    /**
     * set the phone number
     *
     * @param phone the phone number
     * @return null if the phone number is invalid or the person object if the phone number is valid
     */
    public Person setPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            Log.d("Person", "setPhone: phone is null or empty");
            return null;
        }
        // check if the phone number is valid else return null
        if (!phone.matches("[0-9]+")) {
            Log.d("Person", "setPhone: phone is invalid");
            return null;
        }
        this.phone = phone;
        return this;
    }

    /**
     * get the uid
     *
     * @return the uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * get the first name
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * get the last name
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * get the email
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * get the phone number
     *
     * @return the phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * get the set of appointmentIDs
     *
     * @return the set of appointmentIDs
     */
    public HashSet<String> getAppointmentsIDs() {
        return appointmentsIDs;
    }

}
