package clinic.main;

import clinic.model.*;
import clinic.utils.FileManager;
import java.util.ArrayList;
import java.util.List;

public class ClinicApp {

    // --- 1. THE DATABASE (Keep this!) ---
    // These lists hold all our data in memory
    public static List<Doctor> doctors;
    public static List<Patient> patients;
    public static List<Appointment> appointments;
    
    // The "Bottom Half" Data
    public static ArrayList<MedicalRecord> records = new ArrayList<>();
    public static ArrayList<Prescription> prescriptions = new ArrayList<>();
    public static ArrayList<Billing> bills = new ArrayList<>();

    // --- 2. FILE HELPERS (Keep this!) ---
    // Your GUI calls these to save/load data
    
    public static void saveAllData() {
        FileManager.saveData(doctors, "doctors.dat");
        FileManager.saveData(patients, "patients.dat");
        FileManager.saveData(appointments, "appointments.dat");
        FileManager.saveData(records, "records.dat");
        FileManager.saveData(prescriptions, "prescriptions.dat");
        FileManager.saveData(bills, "bills.dat");
    }

    public static void loadAllData() {
        // Load Doctors
        Object docData = FileManager.loadData("doctors.dat");
        doctors = (docData != null) ? (List<Doctor>) docData : new ArrayList<>();

        // Load Patients
        Object patData = FileManager.loadData("patients.dat");
        patients = (patData != null) ? (List<Patient>) patData : new ArrayList<>();
        
        // Load Appointments
        Object apptData = FileManager.loadData("appointments.dat");
        appointments = (apptData != null) ? (List<Appointment>) apptData : new ArrayList<>();
        
        // Load Records
        Object recData = FileManager.loadData("records.dat");
        if (recData != null) records = (ArrayList<MedicalRecord>) recData;

        // Load Prescriptions
        Object presData = FileManager.loadData("prescriptions.dat");
        if (presData != null) prescriptions = (ArrayList<Prescription>) presData;
        
        // Load Bills
        Object billData = FileManager.loadData("bills.dat");
        if (billData != null) bills = (ArrayList<Billing>) billData;
    }
}