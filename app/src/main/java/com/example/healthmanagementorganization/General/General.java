package com.example.healthmanagementorganization.General;

public class General {
    public static final int DAY_START = 9;
    public static final int DAY_END = 17;
    public static final String FB_appointmets = "appointments";
    public static final String FB_Doctors = "Doctors";
    public static final String FB_Medicine = "Drugs";
    public static final String FB_Patients = "Patients";
    public static final String FB_firstName = "firstName";
    public static String FB_LastName = "lastName";
    public static String Info = "Info";
    public static String Main = "My Appointments";
    public static String NewAPP = "New Appointment";
    public static String Medicines = "Medicines Search";


    public enum MedicineRequestStatus {
        DECLINE(-1), NEW(0), APPROVE(1);
        public final int value;

        MedicineRequestStatus(int i) {
            this.value = i;
        }
    }


}
