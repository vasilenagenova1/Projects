package catHouse.repositories.interfaces;

import catHouse.entities.toys.interfaces.Toy;

public interface Repository {

    void buyToy(Toy toy);

    boolean removeToy(Toy toy);

    Toy findFirst(String type);
}
