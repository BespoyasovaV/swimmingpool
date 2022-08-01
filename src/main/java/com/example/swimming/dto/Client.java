package com.example.swimming.dto;

import javax.naming.Name;
import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author BespoyasovaV
 */
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long clientId;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    private String name;
    private String tel;


    public Client() {
    }

    public Client(String name, String tel, String email) {
        this.email = email;
        this.name = name;
        this.tel = tel;
    }

    public void setTimetables(Set<Timetable> timetables) {
        this.timetables = timetables;
    }

    public Client(Set<Timetable> timetables) {
        this.timetables = timetables;
    }

    public Set<Timetable> getTimetables() {
        return timetables;
    }

    @ManyToMany
    @JoinTable(name = "userandTime",
            joinColumns = @JoinColumn(name = "client_Id"),
            inverseJoinColumns = @JoinColumn(name = "time_Id"))
    private Set<Timetable> timetables = new HashSet<>();

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}