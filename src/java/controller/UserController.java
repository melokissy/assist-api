/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
import java.util.Formatter;
import java.util.List;
import javax.ws.rs.core.Response;
import model.User;

/**
 *
 * @author Kissy de Melo
 */
public class UserController {
    private final UserDAO userDao = new UserDAO();
    
    public User getUserById(Integer idUser){
        return this.userDao.search(idUser);
    }
     public User insert(User user) throws Exception {
         try {
             user.setStatus(true);
             userDao.insertUser(user);
         } catch (Exception e) {
             throw new Exception("Não foi possivel cadastrar usuário");
         }
         return user;
     }

    public List<User> users() throws Exception {
        try {
             return userDao.users();
         } catch (Exception e) {
             throw new Exception("Não foi possível listar usuários");
         }
    }
    
    
        public User update(User user) {
        User selectedUser = this.userDao.search(user.getId());
        
            if (user.getName() != null){
               selectedUser.setName(user.getName());
            }

            if (user.getEmail() != null){
               selectedUser.setEmail(user.getEmail());
            }
            
             if (user.getPassword() != null){
               selectedUser.setPassword(user.getPassword());
            }

            if(user.getStatus()!= selectedUser.getStatus()){
               selectedUser.setStatus(user.getStatus());
            }
        return this.userDao.update(selectedUser);
    }
        
        public User delete(Integer idUser) {
        User selectedUser = this.userDao.search(idUser);
        return this.userDao.delete(selectedUser);
    }
}
