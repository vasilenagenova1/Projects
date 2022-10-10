package softuni.exam.models.dto;

import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportOfferDTO {
    @XmlElement(name = "price")
    private BigDecimal price;
    @XmlElement(name = "agent")
    private AgentNameDTO name;
    @XmlElement(name = "apartment")
    private ApartmentIdDTO apartment;
    @XmlElement(name = "publishedOn")
    private String publishedOn;


    @Positive
    public BigDecimal getPrice() {
        return price;
    }

    public ImportOfferDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public AgentNameDTO getName() {
        return name;
    }

    public ImportOfferDTO setName(AgentNameDTO name) {
        this.name = name;
        return this;
    }

    public ApartmentIdDTO getApartment() {
        return apartment;
    }

    public ImportOfferDTO setApartment(ApartmentIdDTO apartment) {
        this.apartment = apartment;
        return this;
    }

    public String getPublishedOn() {
        return publishedOn;
    }

    public ImportOfferDTO setPublishedOn(String publishedOn) {
        this.publishedOn = publishedOn;
        return this;
    }
}
