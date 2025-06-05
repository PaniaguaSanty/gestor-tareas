package com.todoproject.demo.controller;

import com.todoproject.demo.dto.request.TeamRequestDto;
import com.todoproject.demo.dto.response.TeamResponseDto;
import com.todoproject.demo.dto.response.UserResponseDto;
import com.todoproject.demo.service.TeamServiceImpl;
import com.todoproject.demo.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teams")
public class TeamController {

    private final TeamServiceImpl teamService;
    private final UserServiceImpl userService;
    private final Logger logger = LoggerFactory.getLogger(TeamController.class);

    public TeamController(TeamServiceImpl teamService, UserServiceImpl userService) {
        this.teamService = teamService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<TeamResponseDto> create(@RequestBody TeamRequestDto REQUEST) {
        logger.info("Entering in create method..");
        TeamResponseDto RESPONSE = teamService.create(REQUEST);
        logger.info("Exiting create method..");
        return new ResponseEntity<>(RESPONSE, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TeamResponseDto> update(@RequestBody TeamRequestDto REQUEST) {
        logger.info("Entering in update method..");
        TeamResponseDto RESPONSE = teamService.update(REQUEST);
        logger.info("Exiting update method..");
        return new ResponseEntity<>(RESPONSE, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Entering in delete method..");
        teamService.delete(String.valueOf(id));
        logger.info("Exiting delete method..");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<TeamResponseDto> findById(@PathVariable Long id) {
        logger.info("Entering in findById method..");
        TeamResponseDto RESPONSE = teamService.findOne(String.valueOf(id));
        logger.info("Exiting findById method..");
        return new ResponseEntity<>(RESPONSE, HttpStatus.FOUND);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<TeamResponseDto>> findAll() {
        logger.info("Entering in findAll method..");
        List<TeamResponseDto> RESPONSES = teamService.findAll();
        logger.info("Exiting findAll method..");
        return new ResponseEntity<>(RESPONSES, HttpStatus.FOUND);
    }

    @GetMapping("/findAllActive")
    public ResponseEntity<List<TeamResponseDto>> findAllActive() {
        logger.info("Entering in findAllActive method..");
        List<TeamResponseDto> RESPONSES = teamService.findAllActive();
        logger.info("Exiting findAllActive method..");
        return new ResponseEntity<>(RESPONSES, HttpStatus.FOUND);
    }

    //url para probar el método: http://localhost:8080/teams/list
    @GetMapping("/list")
    public String findAllTeams(Model model) {
        logger.info("Entering in findAllTeams method..");
        List<TeamResponseDto> teams = teamService.findAllActive();
        model.addAttribute("teams", teams);
        logger.info("Exiting findAllTeams method..");
        return "teamDashboard";
    }

    //url para probar el método: http://localhost:8080/teams/form
    @GetMapping("/form")
    public String showCreateForm(Model model) {
        model.addAttribute("team", new TeamRequestDto());
        return "create-team";
    }

    @PostMapping("/save")
    public String handleForm(@ModelAttribute("team") TeamRequestDto team) {
        teamService.create(team);
        return "redirect:/teams/list"; //hacer que redireccione al team dashboard
    }

    /**
     * Muestra el formulario para editar un team
     */
    @GetMapping("/showUpdateForm/{dni}")
    public String showUpdateForm(@PathVariable String dni, Model model) {
        TeamResponseDto existing = teamService.findOne(dni);
        TeamRequestDto formDto = new TeamRequestDto();
        formDto.setName(existing.getName());
        formDto.setDni(existing.getDni());
        model.addAttribute("team", formDto);
        return "update-team";  // El nombre del html con el formulario para editar
    }

    /**
     * Procesa el submit del formulario de edición
     */
    @PostMapping("/update")
    public String handleUpdate(@ModelAttribute("team") TeamRequestDto team) {
        teamService.update(team);
        return "redirect:/teams/list";
    }

    @GetMapping("/disableTeam/{dni}")
    public String disableTeam(@PathVariable String dni) {
        logger.info("Entering disableTeam method for DNI: {}", dni);
        teamService.disable(dni);
        return "redirect:/teams/list";
    }

    @GetMapping("/enableTeam/{dni}")
    public String enableTeam(@PathVariable String dni) {
        logger.info("Entering enableTeam method for DNI: {}", dni);
        teamService.enableTeam(dni);
        return "redirect:/teams/list";
    }

    @GetMapping("/searchBar")
    public String searchTeams(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean status,
            Model model
    ) {
        boolean statusValue = (status != null) ? status : true;
        List<TeamResponseDto> teams = teamService.search(search, statusValue);

        model.addAttribute("teams", teams);
        model.addAttribute("paramSearch", search);
        model.addAttribute("paramStatus", status);
        return "teamManagement";
    }

    /**
     * Muestra el detalle del equipo
     */
    @GetMapping("/detail/{dni}")
    public String showTeamDetail(@PathVariable String dni, Model model) {
        logger.info("Entering showTeamDetail method for DNI: {}", dni);
        TeamResponseDto team = teamService.findOne(dni);
        List<UserResponseDto> allUsers = userService.findAll();
        model.addAttribute("team", team);
        model.addAttribute("allUsers", allUsers);
        return "teamDetail";
    }

    @PostMapping("/{teamDni}/users/{userDni}")
    public String addUserToTeam(
            @PathVariable("teamDni") String teamDni,
            @PathVariable("userDni") String userDni
    ) {
        teamService.addUserToTeam(userDni, teamDni);
        return "redirect:/teams/detail/" + teamDni;
    }

    @PostMapping(value = "/{teamDni}/users/{userDni}", params = "_method=DELETE")
    public String removeUserFromTeam(
            @PathVariable("teamDni") String teamDni,
            @PathVariable("userDni") String userDni
    ) {
        teamService.removeUserFromTeam(userDni, teamDni);
        return "redirect:/teams/detail/" + teamDni;
    }

}
