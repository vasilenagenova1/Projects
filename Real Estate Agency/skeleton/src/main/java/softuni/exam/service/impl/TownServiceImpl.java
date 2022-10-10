package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportTownDTO;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.Validation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class TownServiceImpl implements TownService {
    private static final String READ_TOWNS_FILE_PATH = "skeleton/src/main/resources/files/json/towns.json";

    private final TownRepository townRepository;
    private final Gson gson;
    private final Validation validator;
    private final ModelMapper mapper;


    @Autowired
    public TownServiceImpl(TownRepository townRepository, Gson gson, Validation validator, ModelMapper mapper) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(READ_TOWNS_FILE_PATH));
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson
                        .fromJson(readTownsFileContent(), ImportTownDTO[].class))
                .filter(importTownDTO -> {
                    boolean isValid = validator.isValid(importTownDTO);
                    sb.append(isValid ? String.format("Successfully imported town %s - %s", importTownDTO.getTownName()
                                    , importTownDTO.getPopulation())
                                    : "Invalid town")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(importTownDTO -> mapper.map(importTownDTO, Town.class))
                .forEach(townRepository::save);

        return sb.toString();
    }

    @Override
    public Town findTownByName(String townName) {
        Optional<Town> townByTownName = townRepository.getTownByTownName(townName);
        if (townByTownName.isEmpty()) {
            return null;
        }
        return townByTownName.get();
    }
}
