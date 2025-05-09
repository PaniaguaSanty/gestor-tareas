package com.todoproject.demo.controller;

import com.todoproject.demo.dto.request.TeamRequestDto;
import com.todoproject.demo.dto.response.TeamResponseDto;
import com.todoproject.demo.service.TeamServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Controller
public class TeamController implements CrudController<TeamRequestDto, TeamResponseDto> {

    private final TeamServiceImpl teamService;
    private final Logger logger = LoggerFactory.getLogger(TeamController.class);

    public TeamController(TeamServiceImpl teamService) {
        this.teamService = teamService;
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<TeamResponseDto> create(TeamRequestDto REQUEST) {
        logger.info("Entering in create method..");
        TeamResponseDto RESPONSE = teamService.create(REQUEST);
        logger.info("Exiting create method..");
        return new ResponseEntity<>(RESPONSE, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<TeamResponseDto> update(TeamRequestDto REQUEST) {
        logger.info("Entering in update method..");
        TeamResponseDto RESPONSE = teamService.update(REQUEST);
        logger.info("Exiting update method..");
        return new ResponseEntity<>(RESPONSE, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(Long id) {
        logger.info("Entering in delete method..");
        teamService.delete(String.valueOf(id));
        logger.info("Exiting delete method..");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    @GetMapping("/find/{id}")
    public ResponseEntity<TeamResponseDto> findById(Long id) {
        logger.info("Entering in findById method..");
        TeamResponseDto RESPONSE = teamService.findOne(String.valueOf(id));
        logger.info("Exiting findById method..");
        return new ResponseEntity<>(RESPONSE, HttpStatus.FOUND);
    }

    @Override
    @GetMapping("/findAll")
    public ResponseEntity<List<TeamResponseDto>> findAll() {
        logger.info("Entering in findAll method..");
        List<TeamResponseDto> RESPONSES = teamService.findAll();
        logger.info("Exiting findAll method..");
        return new ResponseEntity<>(RESPONSES, HttpStatus.FOUND);
    }
}
