/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CommentDAO;
import java.util.List;
import model.Comment;

/**
 *
 * @author Kissy de Melo
 */
public class CommentController {

    private final CommentDAO commentDAO = new CommentDAO();

    public List<Comment> searchCommentsByTicket(Integer id) throws Exception {
        try {
            return commentDAO.listCommentsByTicketId(id);
        } catch (Exception e) {
            throw new Exception("Não foi possível localizar o projeto");
        }
    }

    public Comment insert(Comment comment) throws Exception {
        try {
            commentDAO.insertComment(comment);
        } catch (Exception e) {
            throw new Exception("Não foi possivel cadastrar comentário!");
        }
        return comment;
    }

}
