package com.example.healthmanagementorganization.Model.Person;

import com.example.healthmanagementorganization.Model.DataBase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Doctor extends Person implements DataBase {
    private String specialty = "";

    public Doctor() {
        super();
    }


    public Doctor setSpecialty(String specialty) {

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

    @Override
    public void loadToDataBase() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Doctors");
        String id = this.getUid() + "";
        ref.child(id).setValue(this);

    }

}
