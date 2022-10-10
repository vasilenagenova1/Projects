package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AgentNameDTO {
    @XmlElement(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public AgentNameDTO setName(String name) {
        this.name = name;
        return this;
    }
}
