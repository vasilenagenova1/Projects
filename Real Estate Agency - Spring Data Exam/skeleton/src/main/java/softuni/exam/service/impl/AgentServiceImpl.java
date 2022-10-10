package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportAgentDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.repository.AgentRepository;
import softuni.exam.service.AgentService;
import softuni.exam.service.TownService;
import softuni.exam.util.Validation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class AgentServiceImpl implements AgentService {
    private static final String READ_AGENTS_FILE_PATH = "skeleton/src/main/resources/files/json/agents.json";

    private final AgentRepository agentRepository;
    private final TownService townService;

    private final Gson gson;
    private final Validation validator;
    private final ModelMapper mapper;

    @Autowired
    public AgentServiceImpl(AgentRepository agentRepository, TownService townService, Gson gson, Validation validator, ModelMapper mapper) {
        this.agentRepository = agentRepository;
        this.townService = townService;
        this.gson = gson;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(Path.of(READ_AGENTS_FILE_PATH));
    }

    @Override
    public String importAgents() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson
                        .fromJson(readAgentsFromFile(), ImportAgentDTO[].class))
                .filter(importAgentDTO -> {
                    boolean isValid = validator.isValid(importAgentDTO);

                    boolean doesntExist = agentRepository.findAgentByFirstName(importAgentDTO.getFirstName()).isEmpty();
                    if (!doesntExist){
                        isValid = false;
                    }

                    sb
                            .append(isValid
                                    ? String.format("Successfully import agent - %s %s",
                                    importAgentDTO.getFirstName(), importAgentDTO.getLastName())
                                    : "Invalid agent")
                            .append(System.lineSeparator());


                    return isValid;
                })
                .map(importAgentDTO -> {
                    Agent agent = mapper.map(importAgentDTO, Agent.class);
                    agent.setTown(townService.findTownByName(importAgentDTO.getTown()));
                    return agent;
                })
                .forEach(agentRepository::save);

        return sb.toString();
    }

    @Override
    public Agent getAgentByName(String name) {
        return agentRepository.findAgentByFirstName(name).orElse(null);
    }
}
