import java.io.*;
import java.util.Scanner;

public class CashRegister {
    private int b20, b10, b5, c200, c100, c50, c20, c10; 
    private final String FILE_NAME = "cash_data.txt";

    public CashRegister() {
        if (!load()) refillCash();
    }

    public void refillCash() {
        b20 = 20; b10 = 20; b5 = 20;
        c200 = 20; c100 = 20; c50 = 20; c20 = 20; c10 = 20;
        save();
    }

    public void save() {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
            out.println(b20 + "," + b10 + "," + b5 + "," + c200 + "," + c100 + "," + c50 + "," + c20 + "," + c10);
        } catch (IOException e) { }
    }

    private boolean load() {
        File f = new File(FILE_NAME);
        if (!f.exists()) return false;
        try (Scanner sc = new Scanner(f)) {
            String[] p = sc.nextLine().split(",");
            b20 = Integer.parseInt(p[0]); 
            b10 = Integer.parseInt(p[1]); 
            b5 = Integer.parseInt(p[2]);
            c200 = Integer.parseInt(p[3]); 
            c100 = Integer.parseInt(p[4]); 
            c50 = Integer.parseInt(p[5]); 
            c20 = Integer.parseInt(p[6]); 
            c10 = Integer.parseInt(p[7]);
            return true;
        } catch (Exception e) { return false; }
    }

    public boolean isValid(double amount) {
        return amount == 20.0 || amount == 10.0 || amount == 5.0 || 
               amount == 2.0 || amount == 1.0 || amount == 0.5 || amount == 0.2 || amount == 0.1;
    }

    public void addMoney(double amount) {
        if (amount == 20.0) b20++; 
        else if (amount == 10.0) b10++;
        else if (amount == 5.0) b5++; 
        else if (amount == 2.0) c200++;
        else if (amount == 1.0) c100++; 
        else if (amount == 0.5) c50++;
        else if (amount == 0.2) c20++; 
        else if (amount == 0.1) c10++;
        save();
    }

    public void refundMoney(double amount) {
        if (amount == 20.0) b20--; 
        else if (amount == 10.0) b10--;
        else if (amount == 5.0) b5--; 
        else if (amount == 2.0) c200--;
        else if (amount == 1.0) c100--; 
        else if (amount == 0.5) c50--;
        else if (amount == 0.2) c20--; 
        else if (amount == 0.1) c10--;
        save();
    }

    public boolean canGiveChange(double change) {
        int remaining = (int) Math.round(change * 100);
        int tb20=b20, tb10=b10, tb5=b5, tc200=c200, tc100=c100, tc50=c50, tc20=c20, tc10=c10;

        while (remaining >= 2000 && tb20 > 0) { 
            remaining -= 2000; tb20--; 
        }
        while (remaining >= 1000 && tb10 > 0) { 
            remaining -= 1000; tb10--; 
        }
        while (remaining >= 500 && tb5 > 0) { 
            remaining -= 500; tb5--; 
        }
        while (remaining >= 200 && tc200 > 0) { 
            remaining -= 200; tc200--; 
        }
        while (remaining >= 100 && tc100 > 0) { 
            remaining -= 100; tc100--; 
        }
        while (remaining >= 50 && tc50 > 0) { 
            remaining -= 50; tc50--; 
        }
        while (remaining >= 20 && tc20 > 0) { 
            remaining -= 20; tc20--; 
        }
        while (remaining >= 10 && tc10 > 0) { 
            remaining -= 10; tc10--; 
        }
        
        return remaining == 0;
    }

    public void returnChange(double change) {
        int remaining = (int) Math.round(change * 100);
        System.out.println("\n--- DISPENSING CHANGE ---");
        while (remaining >= 2000 && b20 > 0) { 
            remaining -= 2000; b20--; System.out.println("20.00 euro x 1"); 
        }
        while (remaining >= 1000 && b10 > 0) { 
            remaining -= 1000; b10--; System.out.println("10.00 euro x 1"); 
        }
        while (remaining >= 500 && b5 > 0) { 
            remaining -= 500; b5--; System.out.println("5.00 euro x 1"); 
        }
        while (remaining >= 200 && c200 > 0) { 
            remaining -= 200; c200--; System.out.println("2.00 euro x 1"); 
        }
        while (remaining >= 100 && c100 > 0) { 
            remaining -= 100; c100--; System.out.println("1.00 euro x 1"); 
        }
        while (remaining >= 50 && c50 > 0) { 
            remaining -= 50; c50--; System.out.println("0.50 euro x 1"); 
        }
        while (remaining >= 20 && c20 > 0) { 
            remaining -= 20; c20--; System.out.println("0.20 euro x 1"); 
        }
        while (remaining >= 10 && c10 > 0) { 
            remaining -= 10; c10--; System.out.println("0.10 euro x 1"); 
        }
        save();
    }
}