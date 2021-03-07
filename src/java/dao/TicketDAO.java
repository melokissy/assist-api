/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.Project;
import model.Ticket;
import model.User;

/**
 *
 * @author Kissy de Melo
 */
public class TicketDAO{
  
    private static final String NEW_TICKET = "INSERT INTO ticket (subject, description, requester, type, priority, status, project, reponsible, createdAt, editedAt) VALUES (?,?,?,?,?,?,?,?,?, null)";
    private static final String SEARCH_BY_ID = "SELECT idTicket, subject, description, requester, type, priority,status,project,reponsible, createdAt, editedAt FROM ticket WHERE idTicket=?";
    //private static final String TICKETS = "SELECT * FROM ticket ";
    private static final String TICKETS = "select t.idTicket, t.subject, t.description, u.name, t.type, t.priority, t.status, t.reponsible, t.createdAt, t.editedAt, u.idUser FROM ticket t JOIN user u ON u.name =t.requester";
    private static final String EDIT_TICKET = "UPDATE ticket SET subject = ?, description = ?, requester = ?, type = ?, priority = ?, status = ?, project = ?, reponsible = ?, editedAt = ? WHERE idTicket = ?";
    private static final String SEARCH = "SELECT idTicket, subject , description, requester, type, priority, status, project, reponsible, createdAt, editedAt FROM ticket WHERE idTicket=?";
    private static final String DELETE_TICKET = "DELETE FROM ticket WHERE idTicket=?";

    public TicketDAO(){}
    
    //lista todos os tickets
    public List<Ticket> tickets() {
        Connection conn = null;
        List<Ticket> list = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection(); 
            list = new ArrayList(); 
            prepared = conn.prepareStatement(TICKETS); 
            rs = prepared.executeQuery(); 

            while (rs.next()) { 
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt(1));
                ticket.setSubject(rs.getString(2));
                ticket.setDescription(rs.getString(3));
                User userReques = new User();                 
                userReques.setName(rs.getString(4));//solicitante
                ticket.setType(rs.getInt(5));
                ticket.setPriority(rs.getInt(6));
                ticket.setStatus(rs.getString(7));
//                ticket.setProject(rs.getObject(8, Project.class));
                User userRespons = new User();
                userRespons.setName(rs.getString(8));//responsavel
                ticket.setCreatedAt(rs.getString(9));
                ticket.setEditedAt(rs.getString(10));
                userReques.setId(rs.getInt(11));
                //userRespons.setId(rs.getInt(12));
                ticket.setRequester(userReques);
                ticket.setRequester(userRespons); 
                list.add(ticket); 
            }

            return list;
        } catch (Exception e) {
            System.out.println("ERROR - " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }

                if (prepared != null) {
                    prepared.close();
                }

                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error close connections" + ex.getMessage());
            }
        }

        return null;
    }

    public Ticket search(int id) {
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(SEARCH);
            prepared.setInt(1, id);
            rs = prepared.executeQuery();

            if (rs.next()) {
              
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt(1));
                ticket.setSubject(rs.getString(2));
                ticket.setDescription(rs.getString(3));
                ticket.setRequester(rs.getObject(4, User.class));
                ticket.setType(rs.getInt(5));
                ticket.setPriority(rs.getInt(6));
                ticket.setStatus(rs.getString(7));
                ticket.setPriority(rs.getInt(8));
                ticket.setProject(rs.getObject(9, Project.class));
                ticket.setResponsible(rs.getObject(10, User.class));
                ticket.setCreatedAt(rs.getString(11));
                ticket.setEditedAt(rs.getString(12));
                return ticket;
            }

        } catch (Exception ex) {
            System.out.println("[SEARCH] - " + ex.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }

                if (prepared != null) {
                    prepared.close();
                }

                if (rs != null) {
                    rs.close();
                }

            } catch (Exception ex) {
                System.out.println("Error Close connections " + ex.getMessage());
            }
        }
        return null;

    }    
    
    //adiciona novo ticket
    public void insertTicket(Ticket ticket){
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;
        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(NEW_TICKET);            
            prepared.setString(1, ticket.getSubject());
            prepared.setString(2, ticket.getDescription());
            prepared.setString(3, ticket.getRequester().getName());
            prepared.setInt(4, ticket.getType());
            prepared.setInt(5, ticket.getPriority());
            prepared.setString(6, ticket.getStatus());
            prepared.setInt(7, ticket.getProject().getId());
            prepared.setString(8, ticket.getResponsible().getName());
            java.util.Date d = new Date();
            prepared.setString(9, d.toString());
            prepared.executeUpdate();
            rs = prepared.getGeneratedKeys();

            if (rs.next()) {
                ticket.setId(rs.getInt(1));
            }
//            rs = prepared;
        } catch (Exception ex) {
            System.out.println("[TICKET STORE] - " + ex.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }

                if (prepared != null) {
                    prepared.close();
                }

                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error close connections" + ex.getMessage());
            }
        } 
        
    }
    
     public Ticket update(Ticket ticket) {
        Connection conn = null;
        PreparedStatement prepared = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(EDIT_TICKET);      
            prepared.setString(1, ticket.getSubject());
            prepared.setString(2, ticket.getDescription());
            prepared.setString(3, ticket.getRequester().getName());
            prepared.setInt(4, ticket.getType());
            prepared.setInt(5, ticket.getPriority());
            prepared.setInt(6, ticket.getId());
            prepared.setString(7, ticket.getStatus());
            prepared.setInt(8, ticket.getProject().getId());
            prepared.setString(9, ticket.getResponsible().getName());
            prepared.setString(10, ticket.getEditedAt());
            prepared.executeUpdate();
            return ticket;
            
        } catch (Exception ex) {
            System.out.println("[TICKET UPDATE] - " + ex.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }

                if (prepared != null) {
                    prepared.close();
                }
            } catch (Exception ex) {
                System.out.println("Error close connections" + ex.getMessage());
            }
        }

        return ticket;
    }
     
    public Ticket delete(Ticket ticket) {
        Connection conn = null;
        PreparedStatement prepared = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(DELETE_TICKET);
            prepared.setInt(1, ticket.getId());
            prepared.executeUpdate();
            return ticket;
            
        } catch (Exception ex) {
            System.out.println("[TICKET DELETE] - " + ex.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }

                if (prepared != null) {
                    prepared.close();
                }
            } catch (Exception ex) {
                System.out.println("Error Close connections " + ex.getMessage());
            }
        }

    return ticket;
    }
}
