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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Project;
import model.Ticket;
import model.User;

/**
 *
 * @author Kissy de Melo
 */
public class ProjectDAO {
    
    //passar tickets null quando criar novo projeto
//    private static final String PROJECTS = "select p.idProject, t.idTicket, p.name, p.description, p.status, p.createdAt, p.editedAt,tsubject, t.description, t.type FROM project p JOIN ticket t ON t.project = p.idProject ORDER BY p.idProject DESC;";
    private static final String NEW_PROJECT = "INSERT INTO project (name, description, status, createdAt) VALUES (?,?,?,?)";
    private static final String SEARCH_BY_ID = "SELECT idProject, name, description, status, createdAt, editedAt FROM project WHERE idProject=?";
    private static final String PROJECTS = "SELECT * FROM project order by createdAt desc";
    private static final String EDIT_PROJECT = "UPDATE project SET name = ?, description = ?, status = ?, editedAt = ? WHERE idProject = ?";
    private static final String SEARCH = "SELECT idProject, name, description, status, createdAt, editedAt FROM project WHERE idProject=?";
    private static final String DELETE_PROJECT = "DELETE FROM project WHERE idProject=?";
    
    
    public ProjectDAO(){}
    
    public List<Project> projects() {
        Connection conn = null;
        List<Project> list = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection(); 
            list = new ArrayList(); 
            prepared = conn.prepareStatement(PROJECTS); 
            rs = prepared.executeQuery(); 

            while (rs.next()) { 
                Project project = new Project();
                project.setId(rs.getInt(1));            
                project.setName(rs.getString(2));
                project.setDescription(rs.getString(3));
                project.setStatus(rs.getBoolean(4));
                project.setCreatedAt(rs.getDate(5));                
                project.setEditedAt(rs.getDate(6));                
                list.add(project); 
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
    
    public void insertProject(Project project){
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;
        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(NEW_PROJECT,Statement.RETURN_GENERATED_KEYS);            
            prepared.setString(1, project.getName());
            prepared.setString(2, project.getDescription());
            prepared.setBoolean(3, project.getStatus());
            prepared.setDate(4,  java.sql.Date.valueOf(java.time.LocalDate.now()));            
            prepared.executeUpdate();
            rs = prepared.getGeneratedKeys();
            System.out.println("PASSOU DO INSERT");
            if (rs.next()) {
                project.setId(rs.getInt(1));
            }
//            rs = prepared;
        } catch (Exception ex) {
            System.out.println("[PROJECT STORE] - " + ex.getMessage());
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
    
    public Project search(int id) {
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(SEARCH);
            prepared.setInt(1, id);
            rs = prepared.executeQuery();

            if (rs.next()) {
              
                Project project = new Project();
                project.setId(rs.getInt(1));
                project.setName(rs.getString(2));
                project.setDescription(rs.getString(3));
                project.setStatus(rs.getBoolean(4));
                project.setCreatedAt(rs.getDate(5));
                project.setEditedAt(rs.getDate(6));
                return project;
            }

        } catch (Exception ex) {
            System.out.println("[SEARCH PROJECT] - " + ex.getMessage());
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
    
    public Project delete( Project project){
        Connection conn = null;
        PreparedStatement prepared = null;
        
        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(DELETE_PROJECT);
            prepared.setInt(1, project.getId());
            prepared.executeUpdate();
            return project;
        } catch (Exception ex) {
            System.out.println("[PROJECT DELETE] - " + ex.getMessage());
        } finally {
            try {
                if (conn != null){
                    conn.close();
                }
                if (prepared != null) {
                    prepared.close();
                }
            } catch (Exception ex) {
                System.out.println("Error Close connections " + ex.getMessage());
            }
        }
        return project;
    }
    
}
