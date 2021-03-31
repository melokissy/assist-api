
package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.tagext.TryCatchFinally;
import model.User;


public class UserDAO{
  
    private static final String NEW_USER = "INSERT INTO user (name , password, email, status, userIcon, profile) VALUES (?,?,?,?,?,?)";
    private static final String SEARCH_BY_ID = "SELECT idUser, name, email, status, profile FROM user WHERE idUser=?";
    private static final String USERS = "SELECT idUser, name, email, status, profile FROM user ORDER BY UPPER(name) ASC";
    private static final String EDIT_USER = "UPDATE user SET name = ?, email = ?, password = ?, status = ?, userIcon = ?, profile = ? WHERE idUser = ?";
    private static final String SEARCH = "SELECT idUser, name, email, status, profile FROM user WHERE idUser=?";
    private static final String DELETE_USER = "DELETE FROM user WHERE idUser=?";

    public UserDAO(){}
    
    //lista todos os usuarios
    public List<User> users() {
        Connection conn = null;
        List<User> list = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection(); 
            list = new ArrayList(); 
            prepared = conn.prepareStatement(USERS); 
            rs = prepared.executeQuery(); 

            while (rs.next()) { 
                User user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setStatus(rs.getBoolean(4));
                user.setProfile(rs.getString(5));
                list.add(user); 
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

 public User search(int id) {
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(SEARCH);
            prepared.setInt(1, id);
            rs = prepared.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setStatus(rs.getBoolean(4));
                user.setProfile(rs.getString(5));
                return user;
            }

        } catch (Exception ex) {
            System.out.println("[SEARCH USER] - " + ex.getMessage());
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
    
    //adiciona novo usuario
    public void insertUser(User user){
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;
        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(NEW_USER);            
            prepared.setString(1, user.getName());
            prepared.setString(2, user.getPassword());
            prepared.setString(3, user.getEmail());
            prepared.setBoolean(4, user.getStatus());
            prepared.setString(5,user.getUserIcon());
            prepared.setString(6, user.getProfile());
            prepared.executeUpdate();
            rs = prepared.getGeneratedKeys();

            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
//            rs = prepared;
        } catch (Exception ex) {
            System.out.println("[USER STORE] - " + ex.getMessage());
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
    
     public User update(User user) {
        Connection conn = null;
        PreparedStatement prepared = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(EDIT_USER);
            prepared.setString(1, user.getName());
            prepared.setString(2, user.getPassword());
            prepared.setString(3, user.getEmail());
            prepared.setBoolean(4, user.getStatus());
            prepared.setString(5, user.getUserIcon());
            prepared.setString(6, user.getProfile());
            prepared.executeUpdate();
            return user;
        } catch (Exception ex) {
            System.out.println("[USER UPDATE] - " + ex.getMessage());
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

        return user;
    }
     
    public User delete(User user) {
        Connection conn = null;
        PreparedStatement prepared = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(DELETE_USER);
            prepared.setInt(1, user.getId());
            prepared.executeUpdate();
            return user;
        } catch (Exception ex) {
            System.out.println("[USER DELETE] - " + ex.getMessage());
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

    return user;
    }
}
