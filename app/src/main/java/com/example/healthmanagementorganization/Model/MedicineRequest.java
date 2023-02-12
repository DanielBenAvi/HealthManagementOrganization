package com.example.healthmanagementorganization.Model;

import java.util.UUID;

public class MedicineRequest {
    private Medicine medicine;
    private String docID;
    private String uid;

    private String requestID;

    private int status;

    public MedicineRequest() {
        UUID uuid = UUID.randomUUID();
        requestID = uuid.toString();
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

    public String getRequestID() {
        return requestID;
    }
}
