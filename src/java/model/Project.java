/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Kissy de Melo
 */
public class Project {

    private int id;
    private String name;
    private String description;
    private List<Ticket> tickets;
    private Boolean status;
    private Date createdAt; 
    private String editedAt; 
    //private User supervisor;

    public Project() {
    }

    public Project(int id, String name, String description, List<Ticket> tickets, Boolean status, Date createdAt, String editedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tickets = tickets;
        this.status = status;
        this.createdAt = createdAt; 
        this.editedAt = editedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(String editedAt) {
        this.editedAt = editedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    
}
