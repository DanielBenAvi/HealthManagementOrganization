package com.example.healthmanagementorganization.Model.Person;

import android.util.Log;

public class MedicalIssue {
    private String name = "";

    public MedicalIssue() {
    }

    /**
     * set name
     *
     * @param name the name
     * @return null if the name is null or empty, otherwise return this
     */
    public MedicalIssue setName(String name) {
        // check if the name is null or empty
        if (name == null || name.isEmpty()) {
            Log.d("MedicalIssue", "setName: name is null or empty");
            return null;
        }
        // check if the name is valid using regex
        if (!name.matches("^[a-zA-Z ]+$")) {
            Log.d("MedicalIssue", "setName: name is not valid");

            return null;
        }
        this.name = name;
        return this;
    }
}
