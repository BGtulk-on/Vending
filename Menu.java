class Menu {
    private static Drink[] drinks = {
            new Drink("Coffe", 1.50, 100, 10, 0, 0),
            new Drink("Cappucino", 2.00, 100, 10, 0, 1),
            new Drink("Latte", 2.20, 100, 10, 1, 2)
    };

    public static void printMenu() {
        System.out.println("Choose a drink:");
        for (int i = 0; i < drinks.length; i++) {
            System.out.println((i + 1) + ". " + drinks[i].getName() + " (" + drinks[i].getPrice() + " euro)");
        }
    }

    public static Drink getDrink(int choice) {
        if (choice >= 1 && choice <= drinks.length) {
            return drinks[choice - 1];
        } else {
            return null;
        }
    }
}
