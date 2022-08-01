package com.example.swimming.dto;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author BespoyasovaV
 */
@Entity
public class Timetable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long timeId;

    public Long getTimeId() {
        return timeId;
    }

    public void setTimeId(Long timeId) {
        this.timeId = timeId;
    }

    private Date date;
    private String time;
    private boolean flag;

    public Timetable() {
    }

    public Timetable(Date date, String time, boolean flag) {
        this.date = date;
        this.time = time;
        this.flag = flag;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Client clients) {
        this.clients.add(clients);
    }

    @ManyToMany
    @JoinTable(name = "userandTime",
            joinColumns = @JoinColumn(name = "time_Id"),
            inverseJoinColumns = @JoinColumn(name = "client_Id"))
    private Set<Client> clients = new HashSet<>();


    public void addClient(Client client){
        this.clients.add(client);
        client.getTimetables().add(this);

    }
    public void removeClient(Client client){
        this.clients.remove(client);
        client.getTimetables().remove(this);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
