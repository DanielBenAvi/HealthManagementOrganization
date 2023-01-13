package com.example.healthmanagementorganization.Model.Person;

import java.util.HashSet;

public class Patient extends Person {
    private HashSet<MedicalIssue> medicalIssues = new HashSet<>();


    public Patient() {
        super();
    }

    /**
     * add medicalIssue to the set
     *
     * @param medicalIssue the medicalIssue to be added
     * @return true if the medicalIssue is added successfully
     */
    private boolean addMedicalIssue(MedicalIssue medicalIssue) {
        return medicalIssues.add(medicalIssue);
    }

    /**
     * remove medicalIssue from the set
     *
     * @param medicalIssue the medicalIssue to be removed
     * @return true if the medicalIssue is removed successfully
     */
    private boolean removeMedicalIssue(MedicalIssue medicalIssue) {
        return medicalIssues.remove(medicalIssue);
    }

    /**
     * get medicalIssues
     *
     * @return the set of medicalIssues
     */
    public HashSet<MedicalIssue> getMedicalIssues() {
        return medicalIssues;
    }
}
