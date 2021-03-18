/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ProjectDAO;
import java.util.List;
import model.Project;
import model.Ticket;

/**
 *
 * @author Kissy de Melo
 */
public class ProjectController {
    
    private final ProjectDAO pDAO = new ProjectDAO();
    
    public List<Project> projects() throws Exception {
        try {
             return pDAO.projects();
         } catch (Exception e) {
             throw new Exception("Não foi possível listar projeto");
         }
    }
    
     public Project insert(Project project) throws Exception {
         try {
             pDAO.insertProject(project);
         } catch (Exception e) {
             throw new Exception("Não foi possivel cadastrar projeto");
         }
         return project;
     }
     
     public Project delete(Integer idProject) {
         Project selectProject = this.pDAO.search(idProject); 
         return this.pDAO.delete(selectProject);          
     }
}
