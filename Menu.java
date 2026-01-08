class Menu {
    private static Drink[] drinks = {
            new Drink("Coffe", 1.50, 100, 10, 0, 0),
            new Drink("Cappucino", 2.00, 100, 10, 0, 1),
            new Drink("Latte", 2.20, 100, 10, 1, 2)
    };

    public static void printMenu() {
        System.out.println("\n===== COFFEE MENU =====");
        for (int i = 0; i < drinks.length; i++) {
            System.out.printf("%d. %-12s | %.2f euro\n", (i+1), drinks[i].getName(), drinks[i].getPrice());
        }
    }

    public static Drink getDrink(int choice) {
        if (choice >= 1 && choice <= drinks.length) 
            return drinks[choice - 1];
        return null;
    }
}