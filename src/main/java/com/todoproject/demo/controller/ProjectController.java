package com.todoproject.demo.controller;

import com.todoproject.demo.dto.request.ProjectRequestDto;
import com.todoproject.demo.dto.response.ProjectResponseDto;
import com.todoproject.demo.service.ProjectServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final Logger logger = LoggerFactory.getLogger(ProjectController.class);
    private final ProjectServiceImpl projectService;

    public ProjectController(ProjectServiceImpl projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDto> create(@RequestBody ProjectRequestDto REQUEST) {
        logger.info("POST /projects - Creating new project");
        ProjectResponseDto created = projectService.create(REQUEST);
        return ResponseEntity.ok(created);
    }

    @PutMapping
    public ResponseEntity<ProjectResponseDto> update(@RequestBody ProjectRequestDto REQUEST) {
        logger.info("PUT /projects - Updating project");
        ProjectResponseDto updated = projectService.update(REQUEST);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(@PathVariable String code) {
        logger.info("DELETE /projects/{} - Deleting project", code);
        projectService.delete(code);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/enable/{code}")
    public ResponseEntity<ProjectResponseDto> enable(@PathVariable String code) {
        logger.info("PUT /projects/{}/enable - Enabling project", code);
        ProjectResponseDto enabledProject = projectService.enable(code);
        return ResponseEntity.ok(enabledProject);
    }

    @PutMapping("/{code}/disable")
    public ResponseEntity<ProjectResponseDto> disable(@PathVariable String code) {
        logger.info("PUT /projects/{}/disable - Disabling project", code);
        ProjectResponseDto disabled = projectService.disable(code);
        return ResponseEntity.ok(disabled);
    }

    @GetMapping("/findAllActive")
    public ResponseEntity<List<ProjectResponseDto>> findAllActive() {
        logger.info("GET /projects/active - Retrieving all active projects");
        List<ProjectResponseDto> activeProjects = projectService.findAllActive();
        return ResponseEntity.ok(activeProjects);
    }

    @GetMapping("/{code}")
    public ResponseEntity<ProjectResponseDto> findOne(@PathVariable String code) {
        logger.info("GET /projects/{} - Retrieving project", code);
        ProjectResponseDto project = projectService.findOne(code);
        return ResponseEntity.ok(project);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDto>> findAll() {
        logger.info("GET /projects - Retrieving all projects");
        List<ProjectResponseDto> projects = projectService.findAll();
        return ResponseEntity.ok(projects);
    }

    //método para mostrar la lista de proyectos...


    //url para probar el método: http://localhost:8080/projects/list
    @GetMapping("/list")
    public String findAllProjects(Model model) {
        logger.info("Entering in findAllProjects method..");
        List<ProjectResponseDto> projects = projectService.findAllActive();
        model.addAttribute("projects", projects);
        logger.info("Exiting findAllProjects method..");
        return "teamDetail";
    }

    @PostMapping("/save")
    public String saveProject(@ModelAttribute("project") ProjectRequestDto dto) {
        projectService.create(dto);
        return "redirect:/teams/detail/" + dto.getTeamDni();
    }

    @GetMapping("/create/{teamDni}")
    public String showProjectForm(@PathVariable String teamDni, Model model) {
        ProjectRequestDto project = new ProjectRequestDto();
        project.setTeamDni(teamDni); // seteamos el team directamente
        model.addAttribute("project", project);
        return "create-project";
    }

    //Método showProjectDashboard para mostrar la ventana de dashboard con los tasks.
    //tengo que ajustar este método para que muestre dashboard.
    @GetMapping("/detail/{code}")
    public String showProjectDetail(@PathVariable String code, Model model) {
        logger.info("Entering showProjectDetail method for DNI: {}", code);
        ProjectResponseDto project = projectService.findOne(code);
        List<ProjectResponseDto> allProjects = projectService.findAll();
        model.addAttribute("project", project);
        model.addAttribute("allProjects", allProjects);
        return "teamDetail";
    }
}
