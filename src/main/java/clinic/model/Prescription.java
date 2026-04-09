package clinic.model;

import java.io.Serializable;

public class Prescription implements Serializable {
    private int id;
    private int patientId;
    private String medication;
    private String dosage;
    private String instructions;

    public Prescription(int id, int patientId, String medication, String dosage, String instructions) {
        this.id = id;
        this.patientId = patientId;
        this.medication = medication;
        this.dosage = dosage;
        this.instructions = instructions;
    }

    public String getMedication() { return medication; }
    public String getDosage() { return dosage; }
    public String getInstructions() { return instructions; }
    public int getPatientId() { return patientId; }
}
