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
import java.sql.Statement;
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
public class TicketDAO {

    private static final String NEW_TICKET = "INSERT INTO ticket (subject, description, requester_id, type, priority, status, project_id, responsible_id, createdAt,dueDate) VALUES (?,?,?,?,?,?,?,?,?,?)";
    private static final String SEARCH_BY_ID = "SELECT idTicket, subject, description, requester_id, type, priority,status,project_id,responsible_id, createdAt, editedAt,dueDate FROM ticket WHERE idTicket=?";
    //private static final String TICKETS = "select t.idTicket, t.subject, t.description, u.name, t.type, t.priority, t.status, t.reponsible, t.createdAt, t.editedAt, u.idUser FROM ticket t JOIN user u ON u.name =t.requester";
    private static final String EDIT_TICKET = "UPDATE ticket SET subject = ?, description = ?, requester_id = ?, type = ?, priority = ?, status = ?, project_id = ?, responsible_id = ?, editedAt = ?, dueDate=? WHERE idTicket = ?";
    private static final String SEARCH = "SELECT idTicket, subject , description, requester_id, type, priority, status, project_id, responsible_id, createdAt, editedAt,dueDate FROM ticket WHERE idTicket=?";
    private static final String DELETE_TICKET = "DELETE FROM ticket WHERE idTicket=?";
    private static final String TICKETS = "SELECT distinct t.idTicket, "
            + "                t.subject,  "
            + "                t.description,  "
            + "                t.requester_id,  "
            + "                t.type,  "
            + "                t.priority,  "
            + "                t.status,  "
            + "                t.responsible_id,  "
            + "                t.createdAt,  "
            + "                t.editedAt,  "
            + "                t.closedAt,  "
            + "                t.dueDate "
            + "                FROM ticket t order by createdAt desc";

    public TicketDAO() {
    }

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

                //id e nome do usuario solicitante
                User userRequest = new User();
                userRequest.setId(rs.getInt(4));
                ticket.setRequester(userRequest);

                ticket.setType(rs.getString(5));
                ticket.setPriority(rs.getString(6));
                ticket.setStatus(rs.getString(7));

                //pega o ususario responsavel
                User userResponsible = new User();
                userResponsible.setId(rs.getInt(8));
                ticket.setResponsible(userResponsible);

                ticket.setCreatedAt(rs.getDate(9));
                ticket.setEditedAt(rs.getDate(10));
                ticket.setClosedAt(rs.getDate(11));
                ticket.setDueDate(rs.getDate(12));
                list.add(ticket);
            }

            return list;
        } catch (Exception e) {
            System.out.println("ERROR LISTA TICKETS- " + e.getMessage());
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
                ticket.setType(rs.getString(5));
                ticket.setPriority(rs.getString(6));
                ticket.setStatus(rs.getString(7));
                ticket.setProject(rs.getObject(8, Project.class));
                ticket.setResponsible(rs.getObject(9, User.class));
                ticket.setCreatedAt(rs.getDate(10));
                ticket.setEditedAt(rs.getDate(11));
                ticket.setDueDate(rs.getDate(12));
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
    public void insertTicket(Ticket ticket) {
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;
        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(NEW_TICKET, Statement.RETURN_GENERATED_KEYS);
            prepared.setString(1, ticket.getSubject());
            prepared.setString(2, ticket.getDescription());
            // prepared.setInt(3, ticket.getRequester().getId());

            //id e nome do usuario solicitante           
            prepared.setInt(3, ticket.getRequester().getId());
            // prepared.setString(4, ticket.getType());
            prepared.setString(4, ticket.getType());
            prepared.setString(5, ticket.getPriority());
            prepared.setString(6, ticket.getStatus());
            //projeto
            prepared.setInt(7, ticket.getProject().getId());
            //pega o ususario responsavel
            prepared.setInt(8, ticket.getResponsible().getId());
            prepared.setDate(9, java.sql.Date.valueOf(java.time.LocalDate.now()));
            prepared.setDate(10, java.sql.Date.valueOf(java.time.LocalDate.now().plusDays(8)));

            prepared.executeUpdate();
            rs = prepared.getGeneratedKeys();
            System.out.println("PASSOU DO INSERT DO TICKET");

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
            prepared.setString(4, ticket.getType());
            prepared.setString(5, ticket.getPriority());
            prepared.setInt(6, ticket.getId());
            prepared.setString(7, ticket.getStatus());
            prepared.setInt(8, ticket.getProject().getId());
            prepared.setString(9, ticket.getResponsible().getName());
            prepared.setDate(10, java.sql.Date.valueOf(java.time.LocalDate.now()));
//            prepared.setString(11, ticket.getDueDate());
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
