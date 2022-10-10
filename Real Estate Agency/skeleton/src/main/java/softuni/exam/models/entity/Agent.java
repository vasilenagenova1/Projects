package softuni.exam.models.entity;

import javax.persistence.*;

@Entity
@Table(name = "agents")
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", unique = true, nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToOne(optional = false)
    private Town town;

    public Agent() {
    }

    public long getId() {
        return id;
    }

    public Agent setId(long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Agent setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Agent setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Agent setEmail(String email) {
        this.email = email;
        return this;
    }

    public Town getTown() {
        return town;
    }

    public Agent setTown(Town town) {
        this.town = town;
        return this;
    }
}
