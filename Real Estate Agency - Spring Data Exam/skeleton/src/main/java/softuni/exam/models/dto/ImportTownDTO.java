package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class ImportTownDTO {
    @Expose
    private String townName;

    @Expose
    private int population;

    @Size(min = 2)
    public String getTownName() {
        return townName;
    }

    public ImportTownDTO setTownName(String townName) {
        this.townName = townName;
        return this;
    }

    @Positive
    public int getPopulation() {
        return population;
    }

    public ImportTownDTO setPopulation(int population) {
        this.population = population;
        return this;
    }
}
