package com.example.healthmanagementorganization.Model.Person;

import android.util.Log;

public class Doctor extends Person {
    private String specialty = "";

    public Doctor() {
        super();
    }

    /**
     * set specialty
     *
     * @param specialty the specialty
     * @return null if the specialty is null or empty, otherwise return this
     */
    public Doctor setSpecialty(String specialty) {
        // check if the specialty is null or empty
        if (specialty == null || specialty.isEmpty()) {
            Log.d("Doctor", "setSpecialty: specialty is null or empty");
            return null;
        }
        // check if the specialty is valid using regex
        if (!specialty.matches("^[a-zA-Z ]+$")) {
            Log.d("Doctor", "setSpecialty: specialty is not valid");
            return null;
        }
        // todo: check if the specialty is valid some list of specialties


        this.specialty = specialty;
        return this;
    }

    /**
     * get specialty
     *
     * @return specialty
     */
    public String getSpecialty() {
        return specialty;
    }
}
