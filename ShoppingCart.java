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
        int price = Store.getProductPrice(product);
        int money = userWallet.getBalance();
        if (price>money){
            System.out.println("Not enough money in your balance ! Exiting...");
            return false;
        } else {
            return true;
        }
    }

    private static void payArticle(String product, Wallet userWallet) throws IOException, Exception{
        int price = Store.getProductPrice(product);
        int money = userWallet.getBalance();
        int finalMoney = money - price;
        System.out.println("You bought : " + product + ". The price is : " + price);
        try {
            Thread.sleep(10000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        userWallet.setBalance(finalMoney);
    }

    public static void addArticle(String product, Pocket userPocket) throws Exception{
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

            if(enoughMoney(product,wallet)){                    //Check if there is enough money
                try {
                    Thread.sleep(10000);
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                payArticle(product,wallet);                     //Withdraw the price of the product from wallet 
                addArticle(product,pocket);                     //Add name product to the pocket
                System.out.println("Your balance is now :" + getNewBalance(wallet));      //Print new balance
            }
            else {
                break;
            }

            
            /* TODO:
               - check if the amount of credits is enough, if not stop the execution.
               - otherwise, withdraw the price of the product from the wallet.
               - add the name of the product to the pocket file.
               - print the new balance.
            */

            // Just to print everything again...
            print(wallet, pocket);
            product = scan(scanner);
        }
    }
}
