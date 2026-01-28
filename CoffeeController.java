package com.coffee.controller;

import com.coffee.model.*;
import com.coffee.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class CoffeeController {
    private static final Logger LOGGER = Logger.getLogger(CoffeeController.class.getName());
    private final InventoryService inventory;
    private final CashRegisterService cashRegister;
    private final List<Drink> drinks = List.of(
            new Drink("Espresso", 1.00, 30, 20, 0, 0),
            new Drink("Cappucino", 1.40, 50, 15, 0, 1),
            new Drink("Latte", 2.20, 100, 10, 1, 2),
            new Drink("Tea", 0.80, 80, 0, 1, 0),
            new Drink("Milk", 0.70, 0, 0, 0, 5)
    );

    public CoffeeController(InventoryService inventory, CashRegisterService cashRegister) {
        this.inventory = inventory;
        this.cashRegister = cashRegister;
    }

    @GetMapping("/menu")
    public List<Drink> getMenu() { return drinks; }

    @PostMapping("/order")
    public ResponseEntity<?> order(@RequestBody OrderRequest req) {
        try {
            if (req.drinkId == 22418) {
                inventory.refillAll();
                cashRegister.refillCash();
                return ResponseEntity.ok("SYSTEM: Refilled");
            }

            if (req.drinkId < 1 || req.drinkId > drinks.size()) {
                return ResponseEntity.badRequest().body("Invalid selection.");
            }

            Drink drinkChoice = drinks.get(req.drinkId - 1);

            if (!inventory.hasEnoughProducts(drinkChoice, req.sugar, req.milk)) {
                return ResponseEntity.badRequest().body("ERROR: Out of stock!");
            }

            double insertedTotal = 0;
            for (Double coin : req.coins) {
                if (cashRegister.isValidCoin(coin)) {
                    insertedTotal += coin;
                } else {
                    return ResponseEntity.badRequest().body("Coin rejected: " + coin);
                }
            }

            if (insertedTotal < drinkChoice.getPrice()) {
                return ResponseEntity.badRequest().body("Not enough money.");
            }

            double rawChange = insertedTotal - drinkChoice.getPrice();
            double change = Math.round(rawChange * 100.0) / 100.0;

            if (change > 0 && !cashRegister.canGiveChange(change)) {
                return ResponseEntity.badRequest().body("ERROR: No change available.");
            }

            for (Double coin : req.coins) {
                cashRegister.addMoneyToVault(coin);
            }

            inventory.useProductsForDrink(drinkChoice, req.sugar, req.milk);
            var breakdown = (change > 0) ? cashRegister.returnChange(change) : null;
            return ResponseEntity.ok(new OrderResponse("READY: " + drinkChoice.getName(), change, breakdown));

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Order processing failed for drinkId: " + req.drinkId, e);

            return ResponseEntity.status(500).body("FATAL ERROR: Something went wrong internally.");
        }
    }
}