package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "apartments")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportApartmentRootDTO {


    @XmlElement(name = "apartment")
    private List<ImportApartmentDTO> importApartmentDTOList;

    public List<ImportApartmentDTO> getImportApartmentDTOList() {
        return importApartmentDTOList;
    }

    public void setImportApartmentDTOList(List<ImportApartmentDTO> importApartmentDTOList) {
        this.importApartmentDTOList = importApartmentDTOList;
    }
}
