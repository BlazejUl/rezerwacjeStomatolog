package uliasz.blazej.backend.database.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Dentist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String password;
    private String email;



    @OneToMany(mappedBy = "dentist")
    List<PatientNote> patientNotes;

    public Dentist() {
    }

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
