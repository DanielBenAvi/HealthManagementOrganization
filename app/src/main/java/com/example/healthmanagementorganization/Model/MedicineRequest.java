package com.example.healthmanagementorganization.Model;

public class MedicineRequest {
    private Medicine medicine;
    private String docID;
    private String uid;

    private int status;

    public MedicineRequest() {

    }

    public Medicine getMedicine() {
        return medicine;
    }

    public MedicineRequest setMedicine(Medicine medicine) {
        this.medicine = medicine;
        return this;
    }

    public String getDocID() {
        return docID;
    }

    public MedicineRequest setDocID(String docID) {
        this.docID = docID;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public MedicineRequest setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public MedicineRequest setStatus(int status) {
        this.status = status;
        return this;
    }
}
