import backEnd.*;

import java.io.IOException;
import java.util.Scanner;

public class ShoppingCart {
    private static void print(Wallet wallet, Pocket pocket) throws Exception {
        System.out.println("Your current balance is: " + wallet.getBalance() + " credits.");
        System.out.println(Store.asString());
        System.out.println("Your current pocket is:\n" + pocket.getPocket());
    }

    private static String scan(Scanner scanner) throws Exception {
        System.out.print("What do you want to buy? (type quit to stop) ");
        return scanner.nextLine();
    }

    private static boolean enoughMoney(String product, Wallet userWallet) throws IOException, Exception{
        /********************************************************************************************
        * Inputs : Name of the product ; Wallet of the user
        * Output : Boolean
        * Description : Compare the price of the product the user wants to buy with its balance. 
        * Returns true if user has enough money, returns false otherwise.
        *********************************************************************************************/
        int price = Store.getProductPrice(product); // Get the price of the product
        int money = userWallet.getBalance();        // Get balance of user 
        if (price>money){
            System.out.println("Not enough money in your balance ! Exiting...");
            return false;
        } else {
            return true;
        }
    }

    private static void payArticle(String product, Wallet userWallet) throws IOException, Exception{
        /********************************************************************************************
        * Inputs : Name of the product ; Wallet of the user
        * Output : None
        * Description : Substract the price of the product to the current balance of the user. 
        * Set the new balance as the current balance of the user.
        *********************************************************************************************/
        int price = Store.getProductPrice(product);                                     // Get the price of the chosen product
        int money = userWallet.getBalance();                                            // Get the balance of the user
        int finalMoney = money - price;                                                 // Set a temp variable equals to the balance after payment
        System.out.println("You bought : " + product + ". The price is : " + price);
        try {
            Thread.sleep(10000);                                                        // Add a timer to make exploit easier
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        userWallet.setBalance(finalMoney);                                              // Set the balance of user 
    }

    public static void addArticle(String product, Pocket userPocket) throws Exception{
        /********************************************************************************************
        * Inputs : Name of the product ; Pocket of the user
        * Output : None
        * Description : Add article to the pocket of user
        *********************************************************************************************/
        userPocket.addProduct(product);
    }

    public static int getNewBalance (Wallet userWallet) throws IOException{
        int balance = userWallet.getBalance();
        return balance;
    }

    public static void main(String[] args) throws Exception {
        Wallet wallet = new Wallet();
        Pocket pocket = new Pocket();
        Scanner scanner = new Scanner(System.in);

        print(wallet, pocket);
        String product = scan(scanner);

        while(!product.equals("quit")) {

            if(enoughMoney(product,wallet)){                                                //Check if there is enough money
                payArticle(product,wallet);                                                 //Withdraw the price of the product from wallet 
                addArticle(product,pocket);                                                 //Add name product to the pocket
                System.out.println("Your balance is now :" + getNewBalance(wallet));        //Print new balance
            }
            else {
                break;
            }
            // Just to print everything again...
            print(wallet, pocket);
            product = scan(scanner);
        }
    }
}
