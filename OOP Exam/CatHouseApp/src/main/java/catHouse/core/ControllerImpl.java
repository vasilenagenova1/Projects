package catHouse.core;

import catHouse.common.ConstantMessages;
import catHouse.common.ExceptionMessages;
import catHouse.entities.cat.LonghairCat;
import catHouse.entities.cat.ShorthairCat;
import catHouse.entities.cat.interfaces.Cat;
import catHouse.entities.houses.LongHouse;
import catHouse.entities.houses.ShortHouse;
import catHouse.entities.houses.interfaces.House;
import catHouse.entities.toys.Ball;
import catHouse.entities.toys.Mouse;
import catHouse.entities.toys.interfaces.Toy;
import catHouse.repositories.ToyRepository;

import java.util.LinkedHashMap;
import java.util.Map;

public class ControllerImpl implements Controller {
    private ToyRepository toys;

    private LinkedHashMap<String, House> houses;

    public ControllerImpl(ToyRepository toys, LinkedHashMap<String, House> houses) {
        this.toys = toys;
        this.houses = new LinkedHashMap<>();
    }

    @Override
    public String addHouse(String type, String name) {
        if (type.equals("ShortHouse")){
            houses.put(name, new ShortHouse(name));
        } else if (type.equals("LongHouse")) {
            houses.put(name, new LongHouse(name));
        } else {
            throw new NullPointerException(ExceptionMessages.INVALID_HOUSE_TYPE);
        }

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_HOUSE_TYPE, type);
    }

    @Override
    public String buyToy(String type) {
        if (type.equals("Mouse")){
            toys.buyToy(new Mouse());
        } else if (type.equals("Ball")) {
            toys.buyToy(new Ball());
        } else {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_TOY_TYPE);
        }

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_TOY_TYPE, type);
    }

    @Override
    public String toyForHouse(String houseName, String toyType) {
        Toy toyToBuy = toys.findFirst(toyType);
        if (toyToBuy != null){
            toys.removeToy(toyToBuy);
            houses.get(houseName).buyToy(toyToBuy);

            return String.format(ConstantMessages.SUCCESSFULLY_ADDED_TOY_IN_HOUSE, toyType, houseName);
        }
        return String.format(ExceptionMessages.NO_TOY_FOUND, toyType);
    }

    @Override
    public String addCat(String houseName, String catType, String catName, String catBreed, double price) {
        House houseToLive = houses.get(houseName);

        if (catType.equals("ShorthairCat")){
            Cat cat = new ShorthairCat(catName, catBreed, price);

            if (houseToLive.getClass().getSimpleName().startsWith("Short")){
                houseToLive.addCat(cat);
            } else {
                return ConstantMessages.UNSUITABLE_HOUSE;
            }

        } else if (catType.equals("LonghairCat")) {
            Cat cat = new LonghairCat(catName, catBreed, price);

            if (houseToLive.getClass().getSimpleName().startsWith("Long")){
                houseToLive.addCat(cat);
            } else {
                return ConstantMessages.UNSUITABLE_HOUSE;
            }

        } else {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_CAT_TYPE);
        }
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_CAT_IN_HOUSE, catType, houseName);
    }

    @Override
    public String feedingCat(String houseName) {
        House house = houses.get(houseName);
        house.feeding();

        return String.format(ConstantMessages.FEEDING_CAT, house.getCats().size());
    }

    @Override
    public String sumOfAll(String houseName) {
        double priceOfCats = houses.get(houseName).getCats().stream().mapToDouble(Cat::getPrice).sum();
        double priceOfToys = houses.get(houseName).getToys().stream().mapToDouble(Toy::getPrice).sum();

        double priceOfHouse = priceOfToys + priceOfCats;

        return String.format(ConstantMessages.VALUE_HOUSE, houseName, priceOfHouse);
    }

    @Override
    public String getStatistics() {
        StringBuilder statistics = new StringBuilder();
        for (Map.Entry<String, House> entry : houses.entrySet()) {
            House house = entry.getValue();
            statistics.append(house.getStatistics());
        }
        return statistics.toString();
    }
}
