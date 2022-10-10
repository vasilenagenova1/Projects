package catHouse.entities.houses;

import catHouse.common.ConstantMessages;
import catHouse.common.ExceptionMessages;
import catHouse.entities.cat.interfaces.Cat;
import catHouse.entities.houses.interfaces.House;
import catHouse.entities.toys.interfaces.Toy;

import java.util.ArrayList;
import java.util.Collection;

public abstract class BaseHouse implements House {
    private String name;

    private int capacity;

    private Collection<Toy> toys;

    private Collection<Cat> cats;

    protected BaseHouse(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.toys = new ArrayList<>();
        this.cats = new ArrayList<>();
    }

    @Override
    public int sumSoftness() {
        return this.toys.stream().mapToInt(Toy::getSoftness).sum();
    }

    @Override
    public void addCat(Cat cat) {
        if (this.capacity <= 0){
            throw new IllegalStateException(ConstantMessages.NOT_ENOUGH_CAPACITY_FOR_CAT);
        }
        cats.add(cat);
        this.capacity = capacity - 1;
    }

    @Override
    public void removeCat(Cat cat) {
        cats.remove(cat);
    }

    @Override
    public void buyToy(Toy toy) {
        toys.add(toy);
    }

    @Override
    public void feeding() {
        cats.forEach(Cat::eating);
    }
/*"{houseName} {houseType}:
Cats: {catName1} {catName2} {catName3} ... / Cats: none
Toys: {toysCount} Softness: {sumSoftness}"
*/
    @Override
    public String getStatistics() {
        String result = String.format("%s %s:\n" +
                        "Cats: none\n" +
                        "Toys: %d Softness: %d",  this.getName(),
                this.getClass().getSimpleName(),
                toys.size(), this.sumSoftness());

        if (!this.cats.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for (Cat cat : cats) {
                sb.append(cat.getName());
                sb.append(" ");
            }

            result = String.format("%s %s:\n" +
                            "Cats: %s\n" +
                            "Toys: %d Softness: %d\n",  this.getName(),
                    this.getClass().getSimpleName(),
                    sb,
                    toys.size(), this.sumSoftness());
        }

        return result;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()){
            throw new NullPointerException(ExceptionMessages.HOUSE_NAME_CANNOT_BE_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    @Override
    public Collection<Cat> getCats() {
        return this.cats;
    }

    @Override
    public Collection<Toy> getToys() {
        return this.toys;
    }
}
