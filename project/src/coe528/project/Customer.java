/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Scarlett Li
 */
public class Customer {
    // Overview: The Customer class represents a customer which has a username, password, role, balance and an
    //           account. The account level of a customer changes based on their balance. A customer can make  
    //           a deposit, withdrawl, and purchase. Customers are mutable.
    // 
    // The abstraction function is:
    // AF(c) = a customer A where
    //         A username = c.username &&
    //         A password = c.password &&
    //         A role = c.role &&
    //         A balance = c.balance
    //
    // The rep invariant is:
    // c.account != null &&
    // c.balance >= 0 &&
    // c.username != null && 
    // c.username != "" &&
    // c.password != null &&
    // c.password != "" &&
    // c.role = "customer"
    // 
    
    private String username;
    private String password;
    private String role;
    private double balance;
    //state change applies to account
    private Account account;
    
    public Customer(String username, String password){
        //EFFECTS: Creates a new Customer object. Initializes username and password to corresponding string 
        //         parameters. Initializes role to be "customer" and account to a new silver level account.
        this.username = username;
        this.password = password;
        role = "customer";
        //initial state is LevelSilver since there is only $100 (deposited by manager)
        account = new LevelSilver(this);
    }
    
    public String getUsername(){
        //EFFECTS: Returns the username of the customer.
        return username;
    }
    
    public String getPassword(){
        //EFFECTS: Returns the password of the customer.
        return password;
    }
    
    /**
     * Method to get balance of account
     * @return 
     */
    public double getBalance(){
        //EFFECTS: Returns the balance of the account.
        return balance;
    }
    
    /**
     * Method to change level(state) of customer account
     * @param level 
     */
    public void setLevel(Account level){
        //REQUIRES: An account that is not null.
        //MODIFIES: The state(level) of the account.
        //EFFECTS: NONE
        if(level != null){
            account = level;
        }
    }
    
    /**
     * Method to return the current account level as a string
     * @return 
     */
    public String levelString(){
        //EFFECTS: Returns a string corresponding to the account level (silver, gold, platinum).
        if(balance<10000){
            return "Silver";
        }else if(balance>=10000 && balance<20000){
            return "Gold";
        }else if(balance>=20000){
            return "Platinum";
        }
        return null;
    }
    
    /**
     * Method creates and writes file; 
     * Stores each customers username, password, and role for authentication
     * @throws IOException 
     */
    public void customerInfoFile() throws IOException{
        //EFFECTS: Creates a new file called the customers username and writes their username, password, 
        //         role, and balance in it. If file cannot be created, IOException is thrown.
        File customerInfo = new File(username+".txt");
        
        if(customerInfo.createNewFile()){
            FileWriter writeToFile = new FileWriter(username+".txt");
            writeToFile.write(username+"\n"+password+"\n"+role+"\n"+balance);
            writeToFile.close();
            System.out.println("Customer "+username+" added.");
        }else{
            throw new IOException("Customer already exists.");
        }
    }
    
    /**
     * Method deposits money into account; 
     * Update account level
     * @param amount 
     */
    public void deposit(double amount){
        //REQUIRES: A number(amount) that is greater then 0.
        //MODIFIES: Balance and the level of the account.
        //EFFECTS: Amount is added to the balance. If amount is 0 or less then 0, NullPointerException
        //         is thrown.
        if(amount>0){
            balance = balance+amount;
            account.changeLevel(this);
        }else{
            throw new NullPointerException();
        }
    }
    
    /**
     * Method withdraws money from account; 
     * Update account level
     * @param amount 
     */
    public void withdraw(double amount){
        //REQUIRES: A number(amount) that is greater then 0 but not more then the balance in the account.
        //MODIFIES: Balance and the level of the account.
        //EFFECTS: Amount is subtracted from balance. If amount is 0 or less then 0 or greater than the current 
        //         account balance, NullPointerException is thrown.
        if(amount>0 && amount<=balance){
            balance = balance-amount;
            account.changeLevel(this);
        }else{
            throw new NullPointerException();
        }
    }
    
    /**
     * Method for online purchase; 
     * Update account level
     * @param amount
     * @throws IOException 
     */
    public void onlinePurchase(double amount) throws IOException{
        //REQUIRES: A real positive number that is greater then or equal to 50.
        //MODIFIES: Balance and the level of the account.
        //EFFECTS: Amount is subtracted from the balance with an additional fee based on the current  
        //         account level. If amount is less then 50 IOException is thrown.
        if(amount>=50){
            account.onlinePurchase(amount);
            account.changeLevel(this);
        }else{
            throw new IOException("Purchase amount must be minimum of $50.");
        }
    }
    
    public boolean repOK(){
        //EFFECTS: Returns true if the rep invarient holds true for this object, otherwise returns false.
        if(account!=null && balance>=0){
            return true;
        }else if(username!=null && !username.equals("")){
            return true;
        }else if(password!=null && !password.equals("")){
            return true;
        }else if(role.equals("customer")){
            return true;
        }else{
            return false;
        }
    }
    
    public String toString(){
        //EFFECTS: Returns a string that contains the customers username, password, role, and account balance.
        //         Implements the abstraction function.
        return "Username: "+username+"\nPassword: "+password+"\nRole: "+role+"\nBalance: "+balance;
    }
}