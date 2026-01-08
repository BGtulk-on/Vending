import java.util.Scanner;

public class CoffeeMachine {
    private Inventory inv = new Inventory();
    private CashRegister cash = new CashRegister();
    private Scanner sc = new Scanner(System.in);

    public void start() {
        while (true) {
            Menu.printMenu();
            System.out.print("Choice: ");
            int choice = sc.nextInt();

            if (choice == 0) 
                break;
            if (choice == 22418) {
                inv.refillAll(); 
                cash.refillCash();
                System.out.println("SYSTEM: refilled!"); 
                continue;
            }

            Drink d = Menu.getDrink(choice);
            if (d == null) { 
                System.out.println("Invalid choice"); 
                continue; 
            }
            
            System.out.print("Sugar (0-5): "); int s = sc.nextInt();
            System.out.print("Milk (0-3): "); int m = sc.nextInt();

            if (!inv.hasEnough(d, s, m)) {
                System.out.println("SORRY: Machine is out of stock!"); 
                continue;
            }

            double inserted = 0;
            while (inserted < d.getPrice()) {
                System.out.printf("Price: %.2f euro | Inserted: %.2f euro | Needed: %.2f euro\n", 
                                  d.getPrice(), inserted, (d.getPrice() - inserted));
                System.out.print("Insert: ");
                double coin = sc.nextDouble();

                if (cash.isValid(coin)) {
                    cash.addMoney(coin);
                    inserted += coin;
                } else {
                    System.out.println("Rejected");
                }
            }

            double change = inserted - d.getPrice();
        
            if (change > 0 && !cash.canGiveChange(change)) {
                System.out.println("ERROR: Not enough change");
                cash.refundMoney(inserted);
                continue; 
            }

            inv.use(d, s, m);
            if (change > 0) {
                cash.returnChange(change);
            }
            
            System.out.println("SUCCESS: Enjoy your " + d.getName() + "!");
        }
    }
}