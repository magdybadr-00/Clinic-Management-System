package clinic.model;

import java.io.Serializable;

public abstract class User implements Serializable {
    protected int id;
    protected String name;
    protected String password;

    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public int getId() { return id; }
    
    public String getName() { return name; }

    // >>>> PASTE THIS LINE HERE <<<<
    public void setName(String name) { this.name = name; }
    // -----------------------------
    
    public String getPassword() { return password; }
}
