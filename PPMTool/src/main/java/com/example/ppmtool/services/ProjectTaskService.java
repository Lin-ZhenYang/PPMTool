package com.example.ppmtool.services;

import com.example.ppmtool.domain.Backlog;
import com.example.ppmtool.domain.ProjectTask;
import com.example.ppmtool.exceptions.ProjectNotFoundException;
import com.example.ppmtool.repositories.BacklogRepository;
import com.example.ppmtool.repositories.ProjectRepository;
import com.example.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username){
        //try {

        //Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();
        projectTask.setBacklog(backlog);
        Integer BacklogSequence = backlog.getPTSequence();
        BacklogSequence++;
        backlog.setPTSequence(BacklogSequence);
        projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);
        if (projectTask.getPriority()==null || projectTask.getPriority()==0){
            projectTask.setPriority(3);
        }
        if (projectTask.getStatus()=="" || projectTask.getStatus()==null){
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepository.save(projectTask);
//        } catch(Exception e) {
//            throw new ProjectNotFoundException("Project Not Found");
//        }
    }


    public Iterable<ProjectTask>findBacklogById(String id, String username) {
//        Project project = projectRepository.findByProjectIdentifier(id);
//        if (project==null){
//            throw new ProjectNotFoundException("Project with ID: '" + id + "' does not exist.");
//        }
        projectService.findProjectByIdentifier(id,username);
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username){
//        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
//        if (backlog == null){
//            throw new ProjectNotFoundException("Project with ID: '" + backlog_id + "' does not exist.");
//        }
        projectService.findProjectByIdentifier(backlog_id,username);

        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if (projectTask == null){
            throw new ProjectNotFoundException("Project Task with ID: '" + pt_id + "' does not exist.");
        }

        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project Task with ID: '" + pt_id + "' does not exist in project "+backlog_id+" .");
        }
        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id, String username){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);
        projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);
    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id, String username){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);
        projectTaskRepository.delete(projectTask);
    }
}
