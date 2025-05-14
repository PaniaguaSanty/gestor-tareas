package com.todoproject.demo.controller;

import com.todoproject.demo.dto.request.TeamRequestDto;
import com.todoproject.demo.dto.response.TeamResponseDto;
import com.todoproject.demo.service.TeamServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teams")
public class TeamController {

    private final TeamServiceImpl teamService;
    private final Logger logger = LoggerFactory.getLogger(TeamController.class);

    public TeamController(TeamServiceImpl teamService) {
        this.teamService = teamService;
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

}
