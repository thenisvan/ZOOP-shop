package Controller;

import Helper.File.fileHandler;
import Helper.Output_STD_functions;
import Helper.UserInput.InputChecker;
import Model.Buyer;
import Model.shopOwner;
import Model.Product;
import Model.BuyProcess;
import View.PersonView;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class personActions {
    private static final List<Product> InventoryProducts_Shop = shopOwner.inventory;
    private static final List<BuyProcess> moneyMovements = shopOwner.movements;
    private final List<Product> productsOnCard;
    private final List<Product> customerInventory;
    private final Scanner uInput = new Scanner(System.in);
    private final Buyer buyer;
    private final PersonView personView = new PersonView();

    public personActions(Buyer buyer) {
        this.buyer = buyer;
        productsOnCard = buyer.getMyCart();
        customerInventory = buyer.getBoughtProducts();
    }

    public void chooseFromDashboard() {
        while (true) {
            personView.showDashboard();
            String input = uInput.nextLine();

            if (InputChecker.containLetter(input)) continue;

            int choice = Integer.parseInt(input);

            switch (choice) {
                case 1 -> cashIn();
                case 2 -> goShopping();
                case 3 -> personView.showUserInfo(buyer);
                case 4 -> personView.showUserBalance(buyer);
                case 5 -> personView.viewMyCart(productsOnCard);
                case 6 -> clearCart();
                case 7 -> checkOut();
                case 8 -> personView.viewMyBoughtProducts(customerInventory);
                case 9 -> {
                    return;
                }
            }

        }
    }

    private void cashIn() {
        try {
            int max = 1000;
            int min = 500;

            System.out.println("Enter the amount of money (Min: 500 ; Max: 1000)");
            double inputMoney = Double.parseDouble(uInput.nextLine());

            // Calls the cashIn method again if the money was out of range.
            if (inputMoney < min || inputMoney > max) {
                Output_STD_functions.sleep(1, "Out of range!");
                cashIn();
            } else {
                Output_STD_functions.sleep(1, String.format("Success! you cashed in P%.1f", inputMoney));
                buyer.setBalance(buyer.getBalance() + inputMoney);
            }
            // Code would reach here if the user input a numeric char.
        } catch (NumberFormatException e) {
            InputChecker.printNumberFormatExceptionMessage();
            cashIn();
        }
    }

    private void goShopping() {
        Product chosenProduct;

        //?  This is for java to immediately exit the method if there are no products.
        if (InventoryProducts_Shop.size() == 0) {
            Output_STD_functions.sleep(1, "No Available products as of now!");
            return;
        }

        System.out.println("""
                _________________________
                |                       |
                | WELCOME TO THE SHOP ! |
                |                       |
                -------------------------
                """);

        int i = 0;
        for (Product product : InventoryProducts_Shop) {
            if (product.getProductQuantity() == 0) continue;

            System.out.println("----------------------------");
            System.out.printf("""
                    product number: %d
                    product name: %s
                    price: %.1f
                    quantity: %d
                    """, i, product.getProductName(), product.getProductPrice(), product.getProductQuantity());
            System.out.println("----------------------------");
            i++;
        }
        try {
            System.out.print("\nEnter the product number: ");
            int productNumber = Integer.parseInt(uInput.nextLine());
            chosenProduct = InventoryProducts_Shop.get(productNumber);

            System.out.println("""
                    \nDo you want to add to cart or buy now?
                    1 -> Add to cart
                    2 -> Buy now
                    """);

            int choice = Integer.parseInt(uInput.nextLine());

            if (choice == 1) addToCart(chosenProduct);
            else if (choice == 2) buyNow(chosenProduct);
            else {
                InputChecker.printIndexOutOfBoundsExceptionMessage();
                goShopping();
            }
        } catch (NumberFormatException e) {
            InputChecker.printNumberFormatExceptionMessage();
            goShopping();
        } catch (IndexOutOfBoundsException e) {
            InputChecker.printIndexOutOfBoundsExceptionMessage();
            goShopping();
        }
    }

    private void addToCart(Product chosenProduct) {
        try {
            System.out.print("Enter quantity: ");
            int qty = Integer.parseInt(uInput.nextLine());
            chosenProduct.setAmount_toBuy(qty);

            if (qty > chosenProduct.getProductQuantity()) {
                Output_STD_functions.sleep(1, "We don't have enough stock for that quantity!");
                return;
            }

            // ? sets the new quantity of that product
            chosenProduct.setProductQuantity(chosenProduct.getProductQuantity() - qty);

            // ? make and write the file to where that product will be stored.
            fileHandler.makeFile("src/CSV/customerCart.csv", "CustomerName,ProductName,ProductPrice,ProductBoughtQuantity\n");
            fileHandler.writeToFile(new File("src/CSV/customerCart.csv"), String.format("%s,%s,%.1f,%d\n", buyer.getFirstName(), chosenProduct.getProductName(), chosenProduct.getProductPrice(), chosenProduct.getAmount_toBuy()));

            productsOnCard.add(chosenProduct);

        } catch (NumberFormatException e) {
            InputChecker.printNumberFormatExceptionMessage();
            addToCart(chosenProduct);
        }
    }

    private void clearCart() {
        if (InputChecker.isCartEmpty(productsOnCard)) return;

        Output_STD_functions.sleep(1, "Cart successfully cleared!");
        productsOnCard.clear();
    }

    private void buyNow(Product chosenProduct) {
        System.out.print("\nEnter quantity: ");
        int qty = Integer.parseInt(uInput.nextLine());
        chosenProduct.setAmount_toBuy(qty);

        //? This is for when the user input 0 , we set it to 1 to avoid multiplying to 0
        if (qty == 0) qty = 1;
        if (qty > chosenProduct.getProductQuantity()) {
            Output_STD_functions.sleep(1, "We don't have enough stock for that quantity!");
            return;
        }

        BuyProcess buyProcess = new BuyProcess(buyer, chosenProduct);
        MoneyMovementActions moneyMovementActions = new MoneyMovementActions(buyProcess);

        //  if transaction failed
        if (!moneyMovementActions.startTransaction()) return;

        moneyMovements.add(buyProcess);
        customerInventory.add(chosenProduct);

        ProductActions productActions = new ProductActions(chosenProduct);
        productActions.updateProductQuantity(qty);
        productActions.updateProduct();

        // ? This is for making/writing to transactionsCSV to load later when the program runs again
        fileHandler.makeFile("src/CSV/transactions.csv", "CustomerName,ProductName,ProductPrice,ProductQuantity\n");
        fileHandler.writeTransactions(buyProcess + "\n");
    }

    private void checkOut() {
        int totalPrice = 0;

        if (productsOnCard.size() == 0) {
            Output_STD_functions.sleep(1, "You have nothing in cart!");
            return;
        }

        // ? This totals the products from the customer cart
        for (Product product : productsOnCard) {
            if (product.getProductQuantity() <= 0) {
                Output_STD_functions.sleep(1, "No stocks for " + product.getProductName());
                return;
            }
            totalPrice += product.getProductPrice() * product.getAmount_toBuy();
        }

        if (buyer.getBalance() < totalPrice) {
            Output_STD_functions.sleep(1, "You don't have enough money to pay for that!");
            return;
        }

        // ? Loops throughout the customerCart and make transactions in each of the products
        for (Product product : productsOnCard) {
            BuyProcess buyProcess = new BuyProcess(buyer, product);
            MoneyMovementActions moneyMovementActions = new MoneyMovementActions(buyProcess);

            Output_STD_functions.sleep(2, "Processing your request..");
            moneyMovementActions.startTransaction();


            moneyMovements.add(buyProcess);
            customerInventory.add(product);

            ProductActions productActions = new ProductActions(product);
            productActions.updateProduct();

            fileHandler.makeFile("src/CSV/transactions.csv", "CustomerName,ProductName,ProductPrice,ProductQuantity\n");
            fileHandler.writeTransactions(buyProcess + "\n");
        }

        Output_STD_functions.sleep(1, "Checkout done!");

        // ? Updates the file (customerCart.csv) by removing the customer who just checked out.
        fileHandler.updateCustomerCartCSV(buyer.getFirstName());
        productsOnCard.clear();
    }
}
