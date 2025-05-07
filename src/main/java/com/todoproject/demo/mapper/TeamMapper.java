package com.todoproject.demo.mapper;

import com.todoproject.demo.dto.request.TeamRequestDto;
import com.todoproject.demo.dto.response.TeamResponseDto;
import com.todoproject.demo.model.Team;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TeamMapper {

    private final ModelMapper modelMapper;

    public TeamMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TeamResponseDto convertToDto(Team team) {
        return modelMapper.map(team, TeamResponseDto.class);
    }

    public Team convertToEntity(TeamRequestDto teamRequestDto) {
        return modelMapper.map(teamRequestDto, Team.class);
    }

}
