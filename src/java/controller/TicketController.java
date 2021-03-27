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
    UserController userController = new UserController();

    public Ticket insert(Ticket ticket) throws Exception {
        // id gerado, 
        // descrição, assunto, tipo, prioridade, status, solicitante, createdAt, dataVencimento, id_projeto, id_solcitante e id_Responsable
        
        try {
            tDAO.insertTicket(ticket);
        } catch (Exception e) {
            throw new Exception("Não foi possivel cadastrar ticket");
        }
        return ticket;
    }

    public List<Ticket> tickets() {
        List<Ticket> tickets = this.tDAO.tickets();
        if (!tickets.isEmpty()) {
            for (int i = 0; i < tickets.size(); i++) {
                if (tickets.get(i).getRequester().getId() != null) {
                    User usuario = userController.getUserById(tickets.get(i).getRequester().getId());
                    usuario.setPassword("");
                    tickets.get(i).setRequester(usuario);
                }
                if (tickets.get(i).getResponsible().getId() > 0) {
                    User usuario = userController.getUserById(tickets.get(i).getResponsible().getId());
                    usuario.setPassword("");
                    tickets.get(i).setResponsible(usuario);
                } else if (tickets.get(i).getResponsible().getId() == 0 || tickets.get(i).getResponsible().getId() == null) {
                    User usuario = new User();
                    tickets.get(i).setResponsible(usuario);
                }

            }
        }

        return tickets;
    }

    public Ticket update(Ticket ticket) {
        Ticket selectedTicket = this.tDAO.search(ticket.getId());

        if (ticket.getSubject() != null) {
            selectedTicket.setSubject(ticket.getSubject());
        }

        if (ticket.getDescription() != null) {
            selectedTicket.setDescription(ticket.getDescription());
        }

        if (ticket.getResponsible() != null) {
            selectedTicket.setResponsible(ticket.getResponsible());
        }

        if (ticket.getPriority() != null) {
            selectedTicket.setPriority(ticket.getPriority());
        }

        if (ticket.getType() != null) {
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
