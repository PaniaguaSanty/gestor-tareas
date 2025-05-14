package com.todoproject.demo.service;

import com.todoproject.demo.dto.request.TeamRequestDto;
import com.todoproject.demo.dto.response.TeamResponseDto;
import com.todoproject.demo.exception.NotFoundException;
import com.todoproject.demo.mapper.TeamMapper;
import com.todoproject.demo.model.Team;
import com.todoproject.demo.model.User;
import com.todoproject.demo.repository.TeamRepository;
import com.todoproject.demo.repository.UserRepository;
import com.todoproject.demo.util.CRUD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements CRUD<TeamResponseDto, TeamRequestDto> {

    private final Logger logger = LoggerFactory.getLogger(TeamServiceImpl.class);
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamMapper teamMapper;

    public TeamServiceImpl(TeamRepository teamRepository, UserRepository userRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.teamMapper = teamMapper;
    }

    @Override
    public TeamResponseDto create(TeamRequestDto teamRequestDto) {
        try {
            logger.info("Entering in create user Method...");
            Team newTeam = teamMapper.convertToEntity(teamRequestDto);
            Team savedTeam = teamRepository.save(newTeam);
            return teamMapper.convertToDto(savedTeam);
        } catch (NotFoundException e) {
            throw new NotFoundException("Error while creating team..."); //cambiar tipo de excepción.
        }
    }

    @Override
    public TeamResponseDto update(TeamRequestDto teamRequestDto) {
        try {
            logger.info("Entering in update user method...");
            Team existingTeam = teamRepository.findByName(teamRequestDto.getName())
                    .orElseThrow(() -> new NotFoundException("Error, team not found..."));
            existingTeam.setName(teamRequestDto.getName());
            // existingTeam.setUsers();
            // existingTeam.setProjects();
            Team savedTeam = teamRepository.save(existingTeam);
            return teamMapper.convertToDto(savedTeam);
        } catch (NotFoundException e) {
            throw new NotFoundException("Error, while updating team..."); //cambiar tipo de excepción.
        }
    }

    @Override
    public void delete(String id) {
        logger.info("Entering in delete team method...");
        Team team = teamRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new NotFoundException("User not found.."));
        teamRepository.delete(team);
    }

    @Override
    public List<TeamResponseDto> findAll() {
        logger.info("Entering in findAll teams method...");
        List<Team> teams = teamRepository.findAll();

        return teams.stream()
                .map(teamMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TeamResponseDto findOne(String id) {
        logger.info("Entering in findOne method...");
        Team team = teamRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new NotFoundException("team not found.."));
        return teamMapper.convertToDto(team);
    }

    @Override
    public TeamResponseDto disable(String id) {
        logger.info("Entering in disable user method...");
        Team team = teamRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new NotFoundException("Team not found.."));
        team.setActive(false);
        teamRepository.save(team);
        return teamMapper.convertToDto(team);
    }

    public TeamResponseDto setUser(String dni, TeamRequestDto teamRequestDto) {
        logger.info("Entering in setUser method...");
        User user = userRepository.findByDni(dni)
                .orElseThrow(() -> new NotFoundException("User not found with current DNI..."));

        Team existingTeam = teamRepository.findByName(teamRequestDto.getName())
                .orElseThrow(() -> new NotFoundException("Team not found with current name..."));

        List<User> currentUsers = existingTeam.getUsers();
        // Evitar duplicados
        if (!currentUsers.contains(user)) {
            currentUsers.add(user);
        } else {
            logger.info("User already exists in the team.");
        }
        existingTeam.setUsers(currentUsers);
        Team savedTeam = teamRepository.save(existingTeam);
        return teamMapper.convertToDto(savedTeam);
    }


}
