package clinic.model;

public class Doctor extends User {
    private String specialty;

    // This is the 4-argument constructor your App is looking for!
    public Doctor(int id, String name, String password, String specialty) {
        super(id, name, password);
        this.specialty = specialty;
    }

    public String getSpecialty() { return specialty; }
// PASTE THIS LINE BELOW getSpecialty():
    public void setSpecialty(String specialty) { this.specialty = specialty; }
    @Override
    public String toString() {
        return "Dr. " + name + " [" + specialty + "]";
    }
}
