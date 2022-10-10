package softuni.exam.models.dto;

import softuni.exam.models.entity.enums.ApartmentType;

import javax.validation.constraints.DecimalMin;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportApartmentDTO {
    @XmlElement(name = "apartmentType")
    private ApartmentType apartmentType;
    @XmlElement(name = "area")
    private double area;
    @XmlElement(name = "town")
    private String town;

    public ApartmentType getApartmentType() {
        return apartmentType;
    }

    public ImportApartmentDTO setApartmentType(ApartmentType apartmentType) {
        this.apartmentType = apartmentType;
        return this;
    }

    @DecimalMin(value = "40.00")
    public double getArea() {
        return area;
    }

    public ImportApartmentDTO setArea(double area) {
        this.area = area;
        return this;
    }

    public String getTown() {
        return town;
    }

    public ImportApartmentDTO setTown(String town) {
        this.town = town;
        return this;
    }
}
