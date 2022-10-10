package catHouse.repositories;

import catHouse.common.ExceptionMessages;
import catHouse.entities.toys.interfaces.Toy;
import catHouse.repositories.interfaces.Repository;

import java.util.ArrayList;

public class ToyRepository implements Repository {

    private ArrayList<Toy> toys;

    public ToyRepository(ArrayList<Toy> toys) {
        this.toys = new ArrayList<>();
    }

    @Override
    public void buyToy(Toy toy) {
        toys.add(toy);
    }

    @Override
    public boolean removeToy(Toy toy) {
        if (toys.contains(toy)){
            toys.remove(toy);
            return true;
        }
        throw new IllegalArgumentException(String.format(ExceptionMessages.NO_TOY_FOUND, toy.getClass().getSimpleName()));
    }

    @Override
    public Toy findFirst(String type) {
        return toys.stream().filter(toy -> toy.getClass().getSimpleName().equals(type)).findFirst().orElse(null);
    }
}
