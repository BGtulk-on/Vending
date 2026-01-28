package com.coffee.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class CashRegisterService {
    private static final String STATE_FILE = "cash_state.json";
    private final int[] NOMINALS = {2000, 1000, 500, 200, 100, 50, 20, 10, 5};
    private int[] quantitiesPerNominal = new int[9];
    private final ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        if (!loadStateFile()) refillCash();
    }

    private boolean loadStateFile() {
        try {
            File file = new File(STATE_FILE);
            if (file.exists()) {
                this.quantitiesPerNominal = mapper.readValue(file, int[].class);
                return true;
            }
        } catch (Exception e) {
            System.err.println("ERROR: Could not load cash state: " + e.getMessage());
        }
        return false;
    }

    public void saveIntoFile() {
        try {
            mapper.writeValue(new File(STATE_FILE), this.quantitiesPerNominal);
        } catch (Exception e) {
            System.err.println("ERROR: Could not save cash state: " + e.getMessage());
        }
    }

    public void refillCash() {
        for (int i = 0; i < quantitiesPerNominal.length; i++) quantitiesPerNominal[i] += 20;
        saveIntoFile();
    }

    public boolean isValidCoin(double insertedAmount) {
        int val = (int) Math.round(insertedAmount * 100);
        for (int n : NOMINALS) if (n == val) return true;
        return false;
    }

    public void addMoneyToVault(double vaultAmount) {
        int val = (int) Math.round(vaultAmount * 100);
        for (int i = 0; i < NOMINALS.length; i++) {
            if (NOMINALS[i] == val) {
                quantitiesPerNominal[i]++;
                break;
            }
        }
        saveIntoFile();
    }

    public boolean canGiveChange(double changeAmount) {
        int remainingToReturn = (int) Math.round(changeAmount * 100);
        int[] temp = quantitiesPerNominal.clone();

        for (int i = 0; i < NOMINALS.length; i++) {
            int toTake = Math.min(remainingToReturn / NOMINALS[i], temp[i]);
            remainingToReturn -= toTake * NOMINALS[i];
        }
        return remainingToReturn == 0;
    }

    public Map<String, Integer> returnChange(double changeAmount) {
        Map<String, Integer> breakdown = new HashMap<>();
        int remainingToReturn = (int) Math.round(changeAmount * 100);

        for (int i = 0; i < NOMINALS.length; i++) {
            int toTake = Math.min(remainingToReturn / NOMINALS[i], quantitiesPerNominal[i]);
            if (toTake > 0) {
                quantitiesPerNominal[i] -= toTake;
                remainingToReturn -= toTake * NOMINALS[i];
                breakdown.put(String.format("%.2f", NOMINALS[i] / 100.0), toTake);
            }
        }
        saveIntoFile();
        return breakdown;
    }
}