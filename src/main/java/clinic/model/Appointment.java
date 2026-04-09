package clinic.model;

import java.io.Serializable;

public class Appointment implements Serializable {
    // 1. Fields
    private int id;
    private int doctorId;
    private int patientId;
    private String date;

    // 2. Constructor
    public Appointment(int id, int doctorId, int patientId, String date) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.date = date;
    }

    // 3. GETTERS (These were missing!)
    public int getId() {
        return id;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getDate() {
        return date;
    }

    // 4. toString (Optional, good for debugging)
    @Override
    public String toString() {
        return "Appt{" + "date=" + date + ", doc=" + doctorId + ", pat=" + patientId + '}';
    }
}
