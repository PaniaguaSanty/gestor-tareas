package com.todoproject.demo.controller;

import com.todoproject.demo.dto.request.ProjectRequestDto;
import com.todoproject.demo.dto.request.TeamRequestDto;
import com.todoproject.demo.dto.request.WorkModeDto;
import com.todoproject.demo.dto.response.ProjectResponseDto;
import com.todoproject.demo.dto.response.TeamResponseDto;
import com.todoproject.demo.dto.response.UserResponseDto;
import com.todoproject.demo.exception.NotFoundException;
import com.todoproject.demo.service.ProjectServiceImpl;
import com.todoproject.demo.service.TeamServiceImpl;
import com.todoproject.demo.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/onboarding")
public class OnboardingController {

    private final UserServiceImpl userService;
    private final TeamServiceImpl teamService;
    private final ProjectServiceImpl projectService;

    private final Logger logger = LoggerFactory.getLogger(OnboardingController.class);

    public OnboardingController(UserServiceImpl userService,
                                TeamServiceImpl teamService,
                                ProjectServiceImpl projectService) {
        this.userService = userService;
        this.teamService = teamService;
        this.projectService = projectService;
    }

    @GetMapping("/work-mode")
    public String selectWorkMode(Model model) {
        logger.info("Mostrando selección de modo de trabajo");
        model.addAttribute("workMode", new WorkModeDto());
        return "select-work-mode";
    }

    @PostMapping("/process-work-mode")
    public String processWorkMode(@ModelAttribute WorkModeDto workModeDto,
                                  Principal principal,
                                  HttpSession session) {

        String username = principal.getName();
        logger.info("Procesando modo de trabajo para usuario: {}", username);

        UserResponseDto user = userService.findByEmail(username)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con username: " + username));

        if ("TEAM".equals(workModeDto.getMode())) {
            logger.info("Usuario {} seleccionó modo EQUIPO", username);
            return "redirect:/onboarding/create-team";
        } else {
            logger.info("Usuario {} seleccionó modo INDIVIDUAL", username);

            // Crear equipo personal automáticamente usando DTO
            TeamRequestDto teamRequest = new TeamRequestDto();
            teamRequest.setName(user.getName() + "'s Personal Team");
            teamRequest.setDescription("Espacio de trabajo personal");

            TeamResponseDto savedTeam = teamService.create(teamRequest);

            // Asignar usuario al equipo
            teamService.addUserToTeam(user.getDni(), savedTeam.getDni());

            // Guardar teamDni en sesión para el siguiente paso
            session.setAttribute("teamDni", savedTeam.getDni());

            return "redirect:/projects/create/" + savedTeam.getDni();
        }
    }

    @GetMapping("/create-team")
    public String createTeamForm(Model model) {
        logger.info("Mostrando formulario de creación de equipo");
        model.addAttribute("team", new TeamRequestDto());
        return "create-team";
    }

    @PostMapping("/create-team")
    public String createTeam(@ModelAttribute TeamRequestDto teamRequest,
                             Principal principal,
                             HttpSession session) {

        String username = principal.getName();
        logger.info("Creando equipo para usuario: {}", username);

        UserResponseDto user = userService.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con username: " + username));

        // Crear equipo usando DTO
        TeamResponseDto savedTeam = teamService.create(teamRequest);

        // Asignar usuario al equipo
        teamService.addUserToTeam(user.getDni(), savedTeam.getDni());

        // Guardar teamDni en sesión para el siguiente paso
        session.setAttribute("teamDni", savedTeam.getDni());

        return "redirect:/onboarding/create-project";
    }

    @GetMapping("/create-project")
    public String createProjectForm(Model model, HttpSession session) {
        logger.info("Mostrando formulario de creación de proyecto");

        String teamDni = (String) session.getAttribute("teamDni");
        if (teamDni == null) {
            logger.error("No se encontró teamDni en sesión");
            return "redirect:/onboarding/work-mode";
        }

        // Crear DTO con teamDni preestablecido
        ProjectRequestDto projectRequest = new ProjectRequestDto();
        projectRequest.setTeamDni(teamDni);

        model.addAttribute("project", projectRequest);
        return "create-project";
    }

    @PostMapping("/create-project")
    public String createProject(@ModelAttribute ProjectRequestDto projectRequest,
                                HttpSession session) {

        String teamDni = (String) session.getAttribute("teamDni");
        if (teamDni == null) {
            logger.error("No se encontró teamDni en sesión al crear proyecto");
            return "redirect:/onboarding/work-mode";
        }

        // Asegurar que el proyecto se asocie al equipo correcto
        projectRequest.setTeamDni(teamDni);

        ProjectResponseDto savedProject = projectService.create(projectRequest);

        logger.info("Proyecto creado: {} para equipo: {}", savedProject.getName(), teamDni);

        // Limpiar la sesión
        session.removeAttribute("teamDni");

        // Redirigir al dashboard usando el código del proyecto
        return "redirect:/projects/detail/" + savedProject.getCode();
    }
}