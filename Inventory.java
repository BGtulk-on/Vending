import java.io.*;
import java.util.Scanner;

public class Inventory {
    private int water, coffee, milk, sugar, cups;
    private final String FILE_NAME = "inventory_data.txt";

    public Inventory() {
        if (!load()) refillAll();
    }

    private boolean load() {
        File f = new File(FILE_NAME);
        if (!f.exists()) 
            return false;
        try (Scanner sc = new Scanner(f)) {
            water = sc.nextInt();
            coffee = sc.nextInt();
            milk = sc.nextInt();
            sugar = sc.nextInt();
            cups = sc.nextInt();
            return true;
        } catch (Exception e) { 
            return false; 
        }
    }

    public void save() {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
            out.println(water);
            out.println(coffee);
            out.println(milk);
            out.println(sugar);
            out.println(cups);
        } catch (IOException e) {}
    }

    public boolean hasEnough(Drink d, int extraSugar, int extraMilk) {
        if (water < d.getWaterAmount()) 
            return false;
        if (coffee < d.getCoffeeAmount()) 
            return false;
        if (sugar < (d.getBaseSugar() + extraSugar)) 
            return false;
        if (milk < (d.getBaseMilk() + extraMilk)) 
            return false;
        if (cups <= 0) 
            return false;
        return true;
    }

    public void use(Drink d, int extraSugar, int extraMilk) {
        water -= d.getWaterAmount();
        coffee -= d.getCoffeeAmount();
        sugar -= (d.getBaseSugar() + extraSugar);
        milk -= (d.getBaseMilk() + extraMilk);
        cups--;
        save(); 
    }

    public void refillAll() {
        water = 1000; 
        coffee = 500; 
        milk = 500; 
        sugar = 100; 
        cups = 50;
        save();
    }
}