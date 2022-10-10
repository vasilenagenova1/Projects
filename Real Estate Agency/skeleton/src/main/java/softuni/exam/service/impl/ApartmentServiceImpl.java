package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportApartmentRootDTO;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.service.TownService;
import softuni.exam.util.Validation;
import softuni.exam.util.XMLParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ApartmentServiceImpl implements ApartmentService {
    private static final String READ_APARTMENTS_FILE_PATH = "skeleton/src/main/resources/files/xml/apartments.xml";

    private final ApartmentRepository apartmentRepository;
    private final TownService townService;
    private final XMLParser parser;
    private final Validation validator;
    private final ModelMapper mapper;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, TownService townService, XMLParser parser, Validation validator, ModelMapper mapper) {
        this.apartmentRepository = apartmentRepository;
        this.townService = townService;
        this.parser = parser;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(Path.of(READ_APARTMENTS_FILE_PATH));
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        parser.fromFile(READ_APARTMENTS_FILE_PATH, ImportApartmentRootDTO.class)
                .getImportApartmentDTOList()
                .stream()
                .filter(importApartmentDTO -> {
                    boolean isValid = validator.isValid(importApartmentDTO);

                    boolean doesntExist = apartmentRepository.findApartmentByAreaAndTown(importApartmentDTO.getArea(), townService.findTownByName(importApartmentDTO.getTown()))
                            .isEmpty();
                    if (!doesntExist) {
                        isValid = false;
                    }

                    sb.append(isValid
                                    ? String.format("Successfully import apartment %s - %.2f",
                                    importApartmentDTO.getApartmentType(), importApartmentDTO.getArea())
                                    : "Invalid apartment")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(importApartmentDTO -> {
                    Apartment apartment = mapper.map(importApartmentDTO, Apartment.class);
                    Town townByName = townService.findTownByName(importApartmentDTO.getTown());
                    apartment.setTown(townByName);

                    return apartment;
                })
                .forEach(apartmentRepository::save);

        return sb.toString();
    }

    @Override
    public Apartment findById(long id) {
        return apartmentRepository.findById(id).orElse(null);
    }
}
