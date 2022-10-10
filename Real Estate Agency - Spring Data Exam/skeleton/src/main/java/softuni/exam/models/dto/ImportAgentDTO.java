package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class ImportAgentDTO {
    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private String town;

    @Expose
    private String email;

    @Size(min = 2)
    public String getFirstName() {
        return firstName;
    }

    public ImportAgentDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Size(min = 2)
    public String getLastName() {
        return lastName;
    }

    public ImportAgentDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getTown() {
        return town;
    }

    public ImportAgentDTO setTown(String town) {
        this.town = town;
        return this;
    }

    @Email
    public String getEmail() {
        return email;
    }

    public ImportAgentDTO setEmail(String email) {
        this.email = email;
        return this;
    }
}
