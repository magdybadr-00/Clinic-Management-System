package clinic.model;

import java.io.Serializable;

public class MedicalRecord implements Serializable {
    private int id;
    private int patientId;
    private int doctorId;
    private String diagnosis;
    private String treatment;

    public MedicalRecord(int id, int patientId, int doctorId, String diagnosis, String treatment) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }

    // Getters
    public int getId() { return id; }
    public int getPatientId() { return patientId; }
    public String getDiagnosis() { return diagnosis; }
    public String getTreatment() { return treatment; }
}
