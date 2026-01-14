import java.io.*;

public class CashRegister implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int[] NOMINALS = {2000, 1000, 500, 200, 100, 50, 20, 10, 5};
    private int[] quantitiesPerNominal = new int[9]; 
    private static final String STATE_FILE = "cash_state.dat";

    public CashRegister() {
        if (!loadStateFile()) refillCash();
    }

    private boolean loadStateFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(STATE_FILE))) {
            this.quantitiesPerNominal = (int[]) in.readObject();
            return true;
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: Cash state file not found. Initializing vault.");
            return false;
        } catch (Exception e) {
            System.err.println("ERROR: Error loading cash register: " + e.getMessage());
            return false;
        }
    }

    public void saveIntoFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(STATE_FILE))) {
            out.writeObject(this.quantitiesPerNominal);
        } catch (IOException e) {
            System.err.println("ERROR: Could not save cash state: " + e.getMessage());
        }
    }

    public void refillCash() {
        for (int i = 0; i < quantitiesPerNominal.length; i++) quantitiesPerNominal[i] = 20;
        saveIntoFile();
    }

    public boolean isValidCoin(double insertedAmount) {
        int nominalInCents = (int) Math.round(insertedAmount * 100);
        for (int nominal : NOMINALS) if (nominal == nominalInCents) return true;
        return false;
    }

    public void addMoneyToVault(double vaultAmount) {
        int nominalInCents = (int) Math.round(vaultAmount * 100);
        for (int i = 0; i < NOMINALS.length; i++) {
            if (NOMINALS[i] == nominalInCents) {
                quantitiesPerNominal[i]++;
                break;
            }
        }
        saveIntoFile(); 
    }

    public boolean canGiveChange(double changeAmount) {
        int remainingToReturn = (int) Math.round(changeAmount * 100);
        for (int i = 0; i < NOMINALS.length; i++) {
            int toTake = Math.min(remainingToReturn / NOMINALS[i], quantitiesPerNominal[i]);
            remainingToReturn -= toTake * NOMINALS[i];
        }
        return remainingToReturn == 0;
    }

    public void returnChange(double changeAmount) {
        int remainingToReturn = (int) Math.round(changeAmount * 100);
        System.out.println("\n-CHANGE-");
        for (int i = 0; i < NOMINALS.length; i++) {
            int toTake = Math.min(remainingToReturn / NOMINALS[i], quantitiesPerNominal[i]);
            if (toTake > 0) {
                System.out.printf("%.2f euro x %d\n", NOMINALS[i] / 100.0, toTake);
                quantitiesPerNominal[i] -= toTake;
                remainingToReturn -= toTake * NOMINALS[i];
            }
        }
        saveIntoFile();
    }

    public void refundMoney(double InsertedAmount) {
        returnChange(InsertedAmount);
    }
}