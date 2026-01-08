public class CashRegister {
    private int count1euro;
    private int count2euro;
    private int count5euro;
    private int count10euro;
    private int count20euro;
    private int count50euro;
    private int countCoins50;
    private int countCoins20;
    private int countCoins10;
    private double totalBalance; 

    public CashRegister() {
        this.count1euro = 10;
        this.count2euro = 10;
        this.count5euro = 10;
        this.count10euro = 10;
        this.count20euro = 10;
        this.count50euro = 0;
        this.countCoins50 = 0;
        this.countCoins20 = 0;
        this.countCoins10 = 4;
        this.totalBalance = 194.40; 
    }

    public void addMoney(double amount) {
        double epsilon = 0.001;      

        if (Math.abs(amount - 50.0) < epsilon) {
            count50euro++;
        } else if (Math.abs(amount - 20.0) < epsilon) {
            count20euro++;
        } else if (Math.abs(amount - 10.0) < epsilon) {
            count10euro++;
        } else if (Math.abs(amount - 5.0) < epsilon) {
            count5euro++;
        } else if (Math.abs(amount - 2.0) < epsilon) {
            count2euro++;
        } else if (Math.abs(amount - 1.0) < epsilon) {
            count1euro++;
        } else if (Math.abs(amount - 0.50) < epsilon) {
            countCoins50++;
        } else if (Math.abs(amount - 0.20) < epsilon) {
            countCoins20++;
        } else if (Math.abs(amount - 0.10) < epsilon) {
            countCoins10++;
        }

        totalBalance += amount;
        totalBalance = Math.round(totalBalance * 100.0) / 100.0;
    }

    public boolean hasEnoughChange(double change) {
        return simulateChangeReturn(change);
    }

    private boolean simulateChangeReturn(double change) {
        int remaining = (int) Math.round(change * 100);
        
        int t50 = count50euro; int t20 = count20euro; int t10 = count10euro;
        int t5 = count5euro; int t2 = count2euro; int t1 = count1euro;
        int tc50 = countCoins50; int tc20 = countCoins20; int tc10 = countCoins10;

        while (remaining >= 5000 && t50 > 0) { remaining -= 5000; t50--; }
        while (remaining >= 2000 && t20 > 0) { remaining -= 2000; t20--; }
        while (remaining >= 1000 && t10 > 0) { remaining -= 1000; t10--; }
        while (remaining >= 500 && t5 > 0) { remaining -= 500; t5--; }
        while (remaining >= 200 && t2 > 0) { remaining -= 200; t2--; }
        while (remaining >= 100 && t1 > 0) { remaining -= 100; t1--; }
        while (remaining >= 50 && tc50 > 0) { remaining -= 50; tc50--; }
        while (remaining >= 20 && tc20 > 0) { remaining -= 20; tc20--; }
        while (remaining >= 10 && tc10 > 0) { remaining -= 10; tc10--; }
        return remaining == 0;
    }

    public boolean returnChange(double change) {
        if (!simulateChangeReturn(change)) {
            System.out.println("Cannot return exact change! cancel.");
            return false;
        }

        int remaining = (int) Math.round(change * 100);

        while (remaining >= 5000 && count50euro > 0) { remaining -= 5000; count50euro--; }
        while (remaining >= 2000 && count20euro > 0) { remaining -= 2000; count20euro--; }
        while (remaining >= 1000 && count10euro > 0) { remaining -= 1000; count10euro--; }
        while (remaining >= 500 && count5euro > 0) { remaining -= 500; count5euro--; }
        while (remaining >= 200 && count2euro > 0) { remaining -= 200; count2euro--; }
        while (remaining >= 100 && count1euro > 0) { remaining -= 100; count1euro--; }
        while (remaining >= 50 && countCoins50 > 0) { remaining -= 50; countCoins50--; }
        while (remaining >= 20 && countCoins20 > 0) { remaining -= 20; countCoins20--; }
        while (remaining >= 10 && countCoins10 > 0) { remaining -= 10; countCoins10--; }

        totalBalance -= change;
        totalBalance = Math.round(totalBalance * 100.0) / 100.0;

        System.out.println("Change returned: " + String.format("%.2f", change) + " euro.");
        return true;
    }

    public double getTotalBalance() { return totalBalance; }
}