
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
  
    private static final String NEW_USER = "INSERT INTO user (name , password, email, status, userIcon) VALUES (?,?,?,?,?)";
    private static final String SEARCH_BY_ID = "SELECT idUser, name, email, status FROM user WHERE idUser=?";
    private static final String USERS = "SELECT idUser, name, email, status FROM user ORDER BY UPPER(name) ASC";
    private static final String EDIT_USER = "UPDATE user SET name = ?, email = ?, password = ?, status = ?, userIcon = ? WHERE idUser = ?";
    private static final String SEARCH = "SELECT idUser, name, email, status FROM user WHERE idUser=?";
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
                user.setStatus(rs.getString(4));
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
                user.setStatus(rs.getString(4));
                return user;
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
            prepared.setString(4, user.getStatus());
            prepared.setString(5,user.getUserIcon());
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
            prepared.setString(4, user.getStatus());
            prepared.setString(5, user.getUserIcon());
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

/**
 *
 * @author Kissy de Melo
 *//*
public class ClientDAO {
    
    private static final String NEW_CLIENT = "INSERT INTO clients (cpf, name, last_name) VALUES (?,?,?)";
    private static final String EDIT_CLIENT = "UPDATE clients SET cpf = ?, name = ?, last_name = ? WHERE id = ?";
    private static final String DELETE_CLIENT = "DELETE FROM clients WHERE id=?";
    private static final String CLIENT_CAN_BE_DELETED = "SELECT * FROM orders WHERE client_id = ?";
    private static final String SEARCH = "SELECT id, name, last_name, cpf FROM clients WHERE cpf=?";
    private static final String SEARCH_BY_ID = "SELECT id, name, last_name, cpf FROM clients WHERE id=?";

    public List<Client> clients() {
        Connection conn = null;
        List<Client> list = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection(); //- Faz conexão conexão com banco de dados
            list = new ArrayList(); //- Instaciamos a lista
            prepared = conn.prepareStatement(CLIENTS); //- Passa a query que vamos executar no banco de dados
            rs = prepared.executeQuery(); //- Executa a query de fato e ela retorna uma lista do tipo ResultSet

            while (rs.next()) { //- Interagi sobre a lista e para cada item desta lista (que é um registro/uma linha do banco de dados) a gente instância um objeto client
                Client client = new Client();
                client.setId(rs.getInt(1));
                client.setCpf(rs.getString(2));
                client.setName(rs.getString(3));
                client.setLastName(rs.getString(4));
                list.add(client); //- A gente add este objeto cliente dentro da lista
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

    public boolean store(Client client) {
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(NEW_CLIENT, PreparedStatement.RETURN_GENERATED_KEYS);
            prepared.setString(1, client.getCpf());
            prepared.setString(2, client.getName());
            prepared.setString(3, client.getLastName());
            prepared.executeUpdate();
            rs = prepared.getGeneratedKeys();

            if (rs.next()) {
                client.setId(rs.getInt(1));
            }

            return true;
        } catch (Exception ex) {
            System.out.println("[CLIENT STORE] - " + ex.getMessage());
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

        return false;
    }

    public boolean update(Client client) {
        Connection conn = null;
        PreparedStatement prepared = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(EDIT_CLIENT);
            prepared.setString(1, client.getCpf());
            prepared.setString(2, client.getName());
            prepared.setString(3, client.getLastName());
            prepared.setInt(4, client.getId());
            prepared.executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println("[CLIENT UPDATE] - " + ex.getMessage());
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

        return false;
    }

    public boolean delete(Client client) {
        if (this.clientCanBeDeleted(client)) {
            Connection conn = null;
            PreparedStatement prepared = null;

            try {
                conn = new ConnectionFactory().getConnection();
                prepared = conn.prepareStatement(DELETE_CLIENT);
                prepared.setInt(1, client.getId());
                prepared.executeUpdate();
                return true;
            } catch (Exception ex) {
                System.out.println("[CLIENT DELETE] - " + ex.getMessage());
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
        }

        return false;
    }

    public Client search(String cpf) {
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(SEARCH);
            prepared.setString(1, cpf);
            rs = prepared.executeQuery();

            if (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt(1));
                client.setName(rs.getString(2));
                client.setLastName(rs.getString(3));
                client.setCpf(rs.getString(4));
                return client;
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

    public boolean clientCanBeDeleted(Client client) {
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(CLIENT_CAN_BE_DELETED);
            prepared.setInt(1, client.getId());
            rs = prepared.executeQuery();

            if (!rs.next()) {
                return true;
            }

            return false;
        } catch (Exception ex) {
            System.out.println("[CLIENT CAN BE DELETED] - " + ex.getMessage());
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
                System.out.println("[Error close connections] - " + ex.getMessage());
            }
        }

        return false;
    }

    public Client search(int id) {
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(SEARCH_BY_ID);
            prepared.setInt(1, id);
            rs = prepared.executeQuery();

            if (rs.next()) {
                Client client = new Client();
                client.setId(id);
                client.setName(rs.getString(2));
                client.setLastName(rs.getString(3));
                client.setCpf(rs.getString(4));
                return client;
            }
        } catch (Exception ex) {
            System.out.println("[SEARCH BY ID] - " + ex.getMessage());
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
}
*/