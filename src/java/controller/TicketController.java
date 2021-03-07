/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */ 
package controller;

import dao.TicketDAO;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import javax.ws.rs.core.Response;
import model.Ticket;
import model.User;

/**
 *
 * @author Kissy de Melo
 */
public class TicketController {
    private final TicketDAO tDAO = new TicketDAO();
    
     public Ticket insert(Ticket ticket) throws Exception {
         try {
             tDAO.insertTicket(ticket);
         } catch (Exception e) {
             throw new Exception("Não foi possivel cadastrar ticket");
         }
         return ticket;
     }

    public List<Ticket> tickets() throws Exception {
        try {
             return tDAO.tickets();
         } catch (Exception e) {
             throw new Exception("Não foi possível listar ticket");
         }
    }
    
    public Ticket update(Ticket ticket) {
        Ticket selectedTicket = this.tDAO.search(ticket.getId());
        
            if (ticket.getSubject() != null){
               selectedTicket.setSubject(ticket.getSubject());
            }

            if (ticket.getDescription()!= null){
               selectedTicket.setDescription(ticket.getDescription());
            }
            
            if (ticket.getResponsible()!= null){
                selectedTicket.setResponsible(ticket.getResponsible());
            }
            
            if (ticket.getPriority()< 0){
               selectedTicket.setPriority(ticket.getPriority());
            }

            if(ticket.getType()< 0){
               selectedTicket.setType(ticket.getType());
            }
            
            //colocar data editat
                    
        return this.tDAO.update(selectedTicket);
    }
        
        public Ticket delete(Integer idTicket) {
        Ticket selectedTicket = this.tDAO.search(idTicket);
        return this.tDAO.delete(selectedTicket);
    }
}
