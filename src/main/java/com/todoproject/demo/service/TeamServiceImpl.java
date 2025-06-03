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

import java.util.List;
import java.util.UUID;
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
        logger.info("Entering in create team Method...");

        Team newTeam = teamMapper.convertToEntity(teamRequestDto);
        newTeam.setActive(true);

        // Generar un DNI único aleatorio (8 caracteres alfanuméricos)
        String generatedDni;
        do {
            generatedDni = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        } while (teamRepository.existsByDni(generatedDni));
        newTeam.setDni(generatedDni);

        Team savedTeam = teamRepository.save(newTeam);
        return teamMapper.convertToDto(savedTeam);
    }

    @Override
    public TeamResponseDto update(TeamRequestDto teamRequestDto) {
        try {
            logger.info("Entering in update team method...");
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
    public void delete(String dni) {
        logger.info("Entering in delete team method...");
        Team team = teamRepository.findByDni(dni)
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
    public TeamResponseDto findOne(String dni) {
        logger.info("Entering in findOne method...");
        Team team = teamRepository.findByDni(dni)
                .orElseThrow(() -> new NotFoundException("team not found.."));
        return teamMapper.convertToDto(team);
    }

    public void enableTeam(String dni) {
        logger.info("Entering in enableTeam method...");
        Team team = teamRepository.findByDni(dni)
                .orElseThrow(() -> new NotFoundException("team not found.."));
        team.setActive(true);
        teamRepository.save(team);
        teamMapper.convertToDto(team);
    }

    @Override
    public TeamResponseDto disable(String dni) {
        logger.info("Entering in disable team method...");
        Team team = teamRepository.findByDni(dni)
                .orElseThrow(() -> new NotFoundException("Team not found.."));
        team.setActive(false);
        teamRepository.save(team);
        return teamMapper.convertToDto(team);
    }

    public TeamResponseDto addUserToTeam(String userDni, String teamDni) {
        logger.info("Entering in addUserToTeam method...");

        User user = userRepository.findByDni(userDni)
                .orElseThrow(() -> new NotFoundException("User not found with DNI " + userDni));

        Team team = teamRepository.findByDni(teamDni)
                .orElseThrow(() -> new NotFoundException("Team not found with DNI " + teamDni));

        if (!team.getUsers().contains(user)) {
            user.setTeam(team);             // 1. Set team on user (owner of relation)
            userRepository.save(user);     // 2. Save the user first

            team.getUsers().add(user);     // 3. Optional, keeps list updated in memory (not needed for DB)
            // teamRepository.save(team);  // 4. Not necessary unless other changes in Team
        } else {
            logger.info("User already exists in the team.");
        }

        return teamMapper.convertToDto(team);
    }

    public TeamResponseDto removeUserFromTeam(String userDni, String teamDni) {
        logger.info("Entering in removeUserFromTeam method...");

        User user = userRepository.findByDni(userDni)
                .orElseThrow(() -> new NotFoundException("User not found with DNI " + userDni));
        Team team = teamRepository.findByDni(teamDni)
                .orElseThrow(() -> new NotFoundException("Team not found with DNI " + teamDni));

        if (team.getUsers().contains(user)) {
            user.setTeam(null);                // 1. Romper la relación
            userRepository.save(user);        // 2. Guardar al user para actualizar en la DB

            team.getUsers().remove(user);     // 3. Solo para mantener sincronizado en memoria
            logger.info("User {} removed from team {}", userDni, teamDni);
        } else {
            logger.info("User {} is not in team {}, nothing to remove.", userDni, teamDni);
        }

        return teamMapper.convertToDto(team);
    }

    public List<TeamResponseDto> search(String search, boolean status) {
        List<Team> teams = teamRepository.search(search, status);
        return teams.stream()
                .map(teamMapper::convertToDto)
                .toList();
    }


}
