package clinic.model;

import java.io.Serializable;

public class Billing implements Serializable {
    private int id;
    private int patientId;
    private double amount;
    private String status; // "Unpaid" or "Paid"

    public Billing(int id, int patientId, double amount, String status) {
        this.id = id;
        this.patientId = patientId;
        this.amount = amount;
        this.status = status;
    }

    public int getId() { return id; }
    public int getPatientId() { return patientId; }
    public double getAmount() { return amount; }
    public String getStatus() { return status; }
    
    public void setStatus(String status) {
        this.status = status;
    }
}