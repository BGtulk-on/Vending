import java.util.Scanner;

public class CoffeeMachine {
    private Inventory inventory = new Inventory();
    private CashRegister cashRegister = new CashRegister();
    private Scanner inputScanner = new Scanner(System.in);

    public void startTheMachine() {
        while (true) {
            Menu.printMenu();
            System.out.print("Enter your choice: ");
            int menuSelection = inputScanner.nextInt();

            if (menuSelection == 22412) break;
            if (menuSelection == 22418) {
                inventory.refillAll(); 
                cashRegister.refillCash();
                System.out.println("SYSTEM: All resources have been refilled!");
                continue;
            }

            Drink drinkChoice = Menu.getDrink(menuSelection);
            if (drinkChoice == null) {
                System.out.println("Invalid selection.");
                continue;
            }

            System.out.print("Extra Sugar (0-5): "); int extraSugar = inputScanner.nextInt();
            System.out.print("Extra Milk (0-3): "); int extraMilk = inputScanner.nextInt();

            if (!inventory.hasEnoughProducts(drinkChoice, extraSugar, extraMilk)) {
                System.out.println("ERROR: Out of stock!");
                continue;
            }

            double insertedMoney = 0;
            while (insertedMoney < drinkChoice.getPrice()) {
                System.out.printf("Price: %.2f | Inserted amount: %.2f | Remaining amount: %.2f\n", 
                                    drinkChoice.getPrice(), insertedMoney, (drinkChoice.getPrice() - insertedMoney));
                System.out.print("Insert money/coins: ");
                double inputCoinValue = inputScanner.nextDouble();

                if (cashRegister.isValidCoin(inputCoinValue)) {
                    cashRegister.addMoneyToVault(inputCoinValue);
                    insertedMoney += inputCoinValue;
                } else {
                    System.out.println("Coin rejected.");
                }
            }

            double change = insertedMoney - drinkChoice.getPrice();
            if (change > 0 && !cashRegister.canGiveChange(change)) {
                System.out.println("ERROR: No small change available. Refunding your money...");
                cashRegister.refundMoney(insertedMoney);
                continue;
            }

            inventory.useProductsForDrink(drinkChoice, extraSugar, extraMilk);
            if (change > 0) cashRegister.returnChange(change);
            System.out.println("READY: Please take your " + drinkChoice.getName());
        }
    }
}