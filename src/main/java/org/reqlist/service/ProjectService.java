package org.reqlist.service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.reqlist.arch.ValidatorProvider;
import org.reqlist.arch.exception.ResourceNotFoundException;
import org.reqlist.entity.Project;
import org.reqlist.enumerated.StatusEnum;
import org.reqlist.repository.ProjectRepository;
import static org.reqlist.util.AssertUtils.isNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Vinicius Pires <vinicius.costa.pires at gmail.com>
 */
@Service
public class ProjectService {
    
    @Autowired
    private ValidatorProvider vp;
    
    @Autowired
    private ProjectRepository repository;

    public List<Project> findAll() {
        return repository.findAll();
    }

    public Project getById(Integer id) {
        Project project = repository.findOne(id);
        
        if ( isNull(project) ) {
            throw new ResourceNotFoundException();
        }
        
        return project;
    }

    public Project save(Project project) {
        project.setDate(new Date());
        project.setStatus(StatusEnum.ACTIVE);
        
        Set<ConstraintViolation<Project>> violations = vp.validator().validate(project);
        
        if ( !violations.isEmpty() ) {
            throw new ConstraintViolationException(violations);
        }
        
        return repository.save(project);
    }
    
    /*public List<ProjectProgress> getProjectProgress(Integer id) {
        return repository.getProjectProgress(id);
    }

    public List<ProjectComparison> getProjectComparison() {
        return repository.getProjectComparison();
    }*/

}
