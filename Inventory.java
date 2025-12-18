public class Inventory {
    int waterStock = 1000;   // ml
    int coffeeStock = 500;   // g
    int milkStock = 500;     // ml
    int sugarStock = 100;    // пакета по 1 лъжичка
    int cupsStock = 50;

    public boolean hasEnoughWater(int amount) { 
        return waterStock >= amount; 
    }

    public boolean hasEnoughCoffee(int amount) { 
        return coffeeStock >= amount; 
    }

    public boolean hasEnoughMilk(int amount) {
        return milkStock >= amount; 
    }

    public boolean hasEnoughSugar(int amount) { 
        return sugarStock >= amount; 
    }

    public boolean hasEnoughCups() { 
        return cupsStock > 0; 
    }

/*=======================================================*/

    public void useWater(int amount) { 
        waterStock -= amount; 
    }

    public void useCoffee(int amount) { 
        coffeeStock -= amount; 
    }

    public void useMilk(int amount) { 
        milkStock -= amount; 
    }

    public void useSugar(int amount) { 
        sugarStock -= amount; 
    }

    public void useCup() { 
        cupsStock -= 1; 
    }

    public void refillAll() {
        waterStock = 1000;
        coffeeStock = 500;
        milkStock = 500;
        sugarStock = 100;
        cupsStock = 50;
    }
}
