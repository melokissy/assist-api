/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author Kissy de Melo
 */
public class Ticket {
    private int id;
    private String subject; 
    private String description;
    private User requester;
    private int type;
    private int priority;
    private String status; 
    private Project project; 
    private User responsible;
    private String createdAt;
    private String editedAt; 
    private String closedAt; 
//    private Date estimativa;
            
    public Ticket(){};

    public Ticket(int id, String subject, String description, User requester, int type, int priority, String status, Project project, User responsible, String createdAt, String editedAt, String closedAt) {
        this.id = id;
        this.subject = subject;
        this.description = description;
        this.requester = requester;
        this.type = type;
        this.priority = priority;
        this.status = status;
        this.project = project;
        this.responsible = responsible;
        this.createdAt = createdAt;
        this.editedAt = editedAt; 
        this.closedAt = closedAt; 
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getResponsible() {
        return responsible;
    }

    public void setResponsible(User responsible) {
        this.responsible = responsible;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(String editedAt) {
        this.editedAt = editedAt;
    }

    public String getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(String closedAt) {
        this.closedAt = closedAt;
    }
    
           
    
}
