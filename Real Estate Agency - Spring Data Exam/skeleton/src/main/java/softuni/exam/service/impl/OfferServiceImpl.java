package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportOfferRootDTO;
import softuni.exam.models.entity.Offer;
import softuni.exam.models.entity.enums.ApartmentType;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.AgentService;
import softuni.exam.service.ApartmentService;
import softuni.exam.service.OfferService;
import softuni.exam.service.TownService;
import softuni.exam.util.Validation;
import softuni.exam.util.XMLParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {
    private static final String READ_OFFERS_FILE_PATH = "skeleton/src/main/resources/files/xml/offers.xml";
    private final OfferRepository offerRepository;

    private final ApartmentService apartmentService;
    private final AgentService agentService;
    private final TownService townService;

    private final XMLParser parser;
    private final Validation validator;
    private final ModelMapper mapper;

    public OfferServiceImpl(OfferRepository offerRepository, ApartmentService apartmentService,
                            AgentService agentService, TownService townService, XMLParser parser,
                            Validation validator, ModelMapper mapper) {
        this.offerRepository = offerRepository;
        this.apartmentService = apartmentService;
        this.agentService = agentService;
        this.townService = townService;
        this.parser = parser;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(READ_OFFERS_FILE_PATH));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        ImportOfferRootDTO importOfferRootDTO = parser
                .fromFile(READ_OFFERS_FILE_PATH, ImportOfferRootDTO.class);

        importOfferRootDTO.getOfferRootDtoList()
                .stream()
                .filter(importOfferDTO -> {
                    boolean isValid = validator.isValid(importOfferDTO);

                    if (agentService.getAgentByName(importOfferDTO.getName().getName()) == null) {
                        isValid = false;
                    }

                    sb.append(isValid
                                    ? String.format("Successfully import offer %.2f", importOfferDTO.getPrice())
                                    : "Invalid offer")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(importOfferDTO -> {
                    Offer offer = mapper.map(importOfferDTO, Offer.class);
                    offer.setAgent(agentService.getAgentByName(importOfferDTO.getName().getName()));
                    offer.setApartment(apartmentService.findById(importOfferDTO.getApartment().getId()));

                    return offer;
                })
                .forEach(offerRepository::save);

        return sb.toString();
    }

    @Override
    public String exportOffers() {
        StringBuilder sb = new StringBuilder();

        List<Offer> offerListThreeRooms = offerRepository.findOffersByApartmentTypeOrderByApartmentAreaThenPrice(ApartmentType.three_rooms);

        offerListThreeRooms
                .forEach(offer -> {
                    sb.append(String.format("Agent %s %s with offer №%d:%n" +
                                            "   -Apartment area: %.2f%n" +
                                            "   --Town: %s%n" +
                                            "   ---Price: %.2f$",
                                    offer.getAgent().getFirstName(),
                                    offer.getAgent().getLastName(),
                                    offer.getId(),
                                    offer.getApartment().getArea(),
                                    offer.getApartment().getTown().getTownName(),
                                    offer.getPrice()))
                            .append(System.lineSeparator());
                });

        return sb.toString();
    }
}
