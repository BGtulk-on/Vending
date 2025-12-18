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
        this.count1euro = 0;
        this.count2euro = 0;
        this.count5euro = 0;
        this.count10euro = 0;
        this.count20euro = 0;
        this.count50euro = 0;
        this.countCoins50 = 0;
        this.countCoins20 = 0;
        this.countCoins10 = 0;
        this.totalBalance = 0.0;
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
        return change <= totalBalance;
    }

    public boolean returnChange(double change) {
        if (change > totalBalance) {
            System.out.println("No money for change!");
            return false;
        }

        double remaining = change;

        int temp50 = count50euro;
        int temp20 = count20euro;
        int temp10 = count10euro;
        int temp5 = count5euro;
        int temp2 = count2euro;
        int temp1 = count1euro;
        int tempC50 = countCoins50;
        int tempC20 = countCoins20;
        int tempC10 = countCoins10;

        while (remaining >= 50.0 && temp50 > 0) {
            temp50--;
            remaining -= 50.0;
            remaining = Math.round(remaining * 100.0) / 100.0;
        }
        while (remaining >= 20.0 && temp20 > 0) {
            temp20--;
            remaining -= 20.0;
            remaining = Math.round(remaining * 100.0) / 100.0;
        }
        while (remaining >= 10.0 && temp10 > 0) {
            temp10--;
            remaining -= 10.0;
            remaining = Math.round(remaining * 100.0) / 100.0;
        }
        while (remaining >= 5.0 && temp5 > 0) {
            temp5--;
            remaining -= 5.0;
            remaining = Math.round(remaining * 100.0) / 100.0;
        }
        while (remaining >= 2.0 && temp2 > 0) {
            temp2--;
            remaining -= 2.0;
            remaining = Math.round(remaining * 100.0) / 100.0;
        }
        while (remaining >= 1.0 && temp1 > 0) {
            temp1--;
            remaining -= 1.0;
            remaining = Math.round(remaining * 100.0) / 100.0;
        }
        while (remaining >= 0.50 && tempC50 > 0) {
            tempC50--;
            remaining -= 0.50;
            remaining = Math.round(remaining * 100.0) / 100.0;
        }
        while (remaining >= 0.20 && tempC20 > 0) {
            tempC20--;
            remaining -= 0.20;
            remaining = Math.round(remaining * 100.0) / 100.0;
        }
        while (remaining >= 0.10 && tempC10 > 0) {
            tempC10--;
            remaining -= 0.10;
            remaining = Math.round(remaining * 100.0) / 100.0;
        }

        if (remaining > 0.009) {
            System.out.println("Cannot return exact change! cancel.");
            return false;
        }

        count50euro = temp50;
        count20euro = temp20;
        count10euro = temp10;
        count5euro = temp5;
        count2euro = temp2;
        count1euro = temp1;
        countCoins50 = tempC50;
        countCoins20 = tempC20;
        countCoins10 = tempC10;

        totalBalance -= change;
        totalBalance = Math.round(totalBalance * 100.0) / 100.0;

        System.out.println("Change returned.");
        return true;
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    public int getCount1euro() {
        return count1euro;
    }

    public int getCount2euro() {
        return count2euro;
    }

    public int getCount5euro() {
        return count5euro;
    }

    public int getCount10euro() {
        return count10euro;
    }

    public int getCount20euro() {
        return count20euro;
    }

    public int getCount50euro() {
        return count50euro;
    }

    public int getCountCoins50() {
        return countCoins50;
    }

    public int getCountCoins20() {
        return countCoins20;
    }

    public int getCountCoins10() {
        return countCoins10;
    }
}
