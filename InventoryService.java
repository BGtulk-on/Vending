package com.coffee.service;

import com.coffee.model.Drink;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.File;

@Service
public class InventoryService {
    private static final String STATE_FILE = "inventory_state.json";
    private int[] resourcesAmounts = new int[5];
    private final ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        if (!loadStateFile()) refillAll();
    }

    private boolean loadStateFile() {
        try {
            File file = new File(STATE_FILE);
            if (file.exists()) {
                this.resourcesAmounts = mapper.readValue(file, int[].class);
                return true;
            }
        } catch (Exception e) {
            System.err.println("ERROR: Loading inventory failed: " + e.getMessage());
        }
        return false;
    }

    public void saveIntoFile() {
        try {
            mapper.writeValue(new File(STATE_FILE), this.resourcesAmounts);
        } catch (Exception e) {
            System.err.println("ERROR: Saving inventory failed: " + e.getMessage());
        }
    }

    public void refillAll() {
        this.resourcesAmounts = new int[]{1000, 1000, 1000, 1000, 50};
        saveIntoFile();
    }

    public boolean hasEnoughProducts(Drink currentDrink, int extraSugar, int extraMilk) {
        if (resourcesAmounts[0] < currentDrink.getWaterAmount()) return false;
        if (resourcesAmounts[1] < currentDrink.getCoffeeAmount()) return false;
        if (resourcesAmounts[2] < (currentDrink.getBaseMilk() + extraMilk)) return false;
        if (resourcesAmounts[3] < (currentDrink.getBaseSugar() + extraSugar)) return false;
        if (resourcesAmounts[4] < 1) return false;
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