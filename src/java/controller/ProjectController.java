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

    private final ProjectDAO projectDao = new ProjectDAO();

    public List<Project> projects() throws Exception {
        try {
            return projectDao.projects();
        } catch (Exception e) {
            throw new Exception("Não foi possível listar projeto");
        }
    }

    public Project search(Integer id) throws Exception {
        try {
            return projectDao.search(id);
        } catch (Exception e) {
            throw new Exception("Não foi possível localizar o projeto");
        }
    }

    public Project insert(Project project) throws Exception {
        try {
            projectDao.insertProject(project);
        } catch (Exception e) {
            throw new Exception("Não foi possivel cadastrar projeto");
        }
        return project;
    }

    public Project update(Project project) throws Exception {
        try {
            projectDao.update(project);
        } catch (Exception e) {
            throw new Exception("Não foi possivel cadastrar projeto");
        }
        return project;
    }

    public Project delete(Integer idProject) {
        Project selectProject = this.projectDao.search(idProject);
        return this.projectDao.delete(selectProject);
    }
}
