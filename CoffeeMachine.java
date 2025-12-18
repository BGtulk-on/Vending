import java.util.Scanner;

public class CoffeeMachine {
    private Drink selectedDrink;
    private int extraSugar = 0;
    private int extraMilk = 0;
    private double insertedMoney = 0;

    private Inventory inventory = new Inventory();
    private CashRegister cashRegister = new CashRegister();
    private Scanner scanner = new Scanner(System.in);

    public void selectDrink() {
        Menu.printMenu();
        int choice = scanner.nextInt();
        selectedDrink = Menu.getDrink(choice);

        if (selectedDrink == null) {
            System.out.println("Invalid choice! Try again.");
            selectDrink();
        }
    }

    public void chooseExtras() {
        while (true) {
            System.out.print("Extra sugar? (0-5): ");
            extraSugar = scanner.nextInt();
            if (extraSugar >= 0 && extraSugar <= 5) {
                break;
            } else {
                System.out.println("Invalid input! Please enter 0, 1, 2, 3, 4 or 5.");
            }
        }

        while (true) {
            System.out.print("Extra milk? (0-3): ");
            extraMilk = scanner.nextInt();
            if (extraMilk >= 0 && extraMilk <= 3) {
                break;
            } else {
                System.out.println("Invalid input! Please enter 0, 1, 2 or 3.");
            }
        }
    }


    public void insertMoney() {
        System.out.print("Insert money (euro): ");
        insertedMoney = scanner.nextDouble();
        cashRegister.addMoney(insertedMoney);
    }

    private boolean hasEnoughMoney() {
        return insertedMoney >= selectedDrink.getPrice();
    }

    private boolean hasAllIngredients() {
        return inventory.hasEnoughWater(selectedDrink.getWaterAmount())
                && inventory.hasEnoughCoffee(selectedDrink.getCoffeeAmount())
                && inventory.hasEnoughSugar(selectedDrink.getBaseSugar() + extraSugar)
                && inventory.hasEnoughMilk(selectedDrink.getBaseMilk() + extraMilk)
                && inventory.hasEnoughCups();
    }

    public void makeDrink() {
        if (selectedDrink == null) {
            System.out.println("No drink selected.");
            return;
        }

        if (!hasAllIngredients()) {
            System.out.println("Not enough ingredients!");
            refund();
            return;
        }

        if (!hasEnoughMoney()) {
            System.out.println("Not enough money!");
            return;
        }

        double change = insertedMoney - selectedDrink.getPrice();

        if (!cashRegister.hasEnoughChange(change)) {
            System.out.println("Machine cannot return exact change.");
            refund();
            return;
        }

        inventory.useWater(selectedDrink.getWaterAmount());
        inventory.useCoffee(selectedDrink.getCoffeeAmount());
        inventory.useSugar(selectedDrink.getBaseSugar() + extraSugar);
        inventory.useMilk(selectedDrink.getBaseMilk() + extraMilk);
        inventory.useCup();

        if (change > 0) {
            cashRegister.returnChange(change);
        }

        System.out.println("Your drink is ready: " + selectedDrink.getName());
        System.out.println("Sugar: " + (selectedDrink.getBaseSugar() + extraSugar));
        System.out.println("Milk: " + (selectedDrink.getBaseMilk() + extraMilk));

        insertedMoney = 0;
    }

    public void refund() {
        System.out.println("Returning money: " + insertedMoney + "€");
        insertedMoney = 0;
    }

    public void start() {
        while (true) {
            selectDrink();
            chooseExtras();
            insertMoney();
            makeDrink();

            System.out.print("\nDo you want another drink? (yes/no): ");
            String again = scanner.next();
            if (!again.equalsIgnoreCase("yes")) {
                System.out.println("Thank you for using the coffee machine!");
                break;
            }
        }
    }
}
