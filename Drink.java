public class Drink {
    private String name;
    private double price;
    private int waterAmount;
    private int coffeeAmount;
    private int baseSugar;
    private int baseMilk;

    public Drink(String name, double price, int waterAmount, int coffeeAmount, int baseSugar, int baseMilk) {
        this.name = name;
        this.price = price;
        this.waterAmount = waterAmount;
        this.coffeeAmount = coffeeAmount;
        this.baseSugar = baseSugar;
        this.baseMilk = baseMilk;
    }

    public String getName() { 
        return name; 
    }

    public double getPrice() { 
        return price; 
    }

    public int getWaterAmount() { 
        return waterAmount; 
    }

    public int getCoffeeAmount() { 
        return coffeeAmount; 
    }

    public int getBaseSugar() { 
        return baseSugar; 
    }

    public int getBaseMilk() { 
        return baseMilk; 
    }
    
}
