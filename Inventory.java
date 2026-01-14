import java.io.*;

public class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;
    private int[] resourcesAmounts = new int[5]; 
    private static final String STATE_FILE = "inventory_state.dat";
    private static final String SETTINGS_FILE = "inventory_settings.dat";

    public Inventory() {
        if (!loadStateFile()) refillAll();
    }

    private boolean loadStateFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(STATE_FILE))) {
            this.resourcesAmounts = (int[]) in.readObject();
            return true;
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: Inventory state file not found. Starting with defaults.");
            return false;
        } catch (Exception e) {
            System.err.println("ERROR: Error loading inventory: " + e.getMessage());
            return false;
        }
    }

    public void saveIntoFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(STATE_FILE))) {
            out.writeObject(this.resourcesAmounts);
        } catch (IOException e) {
            System.err.println("Could not save inventory to file: " + e.getMessage());
        }
    }

    public void refillAll() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(SETTINGS_FILE))) {
            this.resourcesAmounts = (int[]) in.readObject();
            System.out.println("SYSTEM: Inventory loaded from settings file.");
        } catch (Exception e) {
            System.err.println("ERROR: Settings file missing or corrupt");
        }
        saveIntoFile();
    }

    public boolean hasEnoughProducts(Drink currentDrink, int extraSugar, int extraMilk) {
        if (resourcesAmounts[0] < currentDrink.getWaterAmount()) 
            return false;
        if (resourcesAmounts[1] < currentDrink.getCoffeeAmount()) 
            return false;
        if (resourcesAmounts[2] < (currentDrink.getBaseMilk() + extraMilk)) 
            return false;
        if (resourcesAmounts[3] < (currentDrink.getBaseSugar() + extraSugar)) 
            return false;
        if (resourcesAmounts[4] < 1) 
            return false;
        return true;
    }

    public void useProductsForDrink(Drink currentDrink, int extraSugar, int extraMilk) {
        resourcesAmounts[0] -= currentDrink.getWaterAmount();
        resourcesAmounts[1] -= currentDrink.getCoffeeAmount();
        resourcesAmounts[2] -= (currentDrink.getBaseMilk() + extraMilk);
        resourcesAmounts[3] -= (currentDrink.getBaseSugar() + extraSugar);
        resourcesAmounts[4] -= 1;
        saveIntoFile();
    }
}