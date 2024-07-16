/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Scarlett Li
 */
public class Manager {
    private String username;
    private String password;
    private String role;
    private static Manager instance;
    
    private Manager(){
        username = "admin";
        password = "admin";
        role = "manager";
    }
    
    //singleton pattern used: only 1 manager object
    public static Manager getInstance() {
        if (instance == null){
            instance = new Manager();
        }
        return instance;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getPassword(){
        return password;
    }
    
    /**
     * Method adds a new customer; 
     * Create the account of the customer when added and deposit $100
     * @param username
     * @param password 
     */
    public void addCustomer(String username, String password) throws IOException{
        //new file to store customer information is in constructor of customer
        Customer customer = new Customer(username, password);
        customer.deposit(100);
        customer.customerInfoFile();
    }
    
    /**
     * Method removes specified customer; 
     * Delete user info file to remove customer
     * @param name 
     * @throws java.io.FileNotFoundException 
     */
    public void deleteCustomer(String name) throws FileNotFoundException{
        File removeFile = new File(name+".txt");
        
        if(removeFile.delete()){
            System.out.println("Customer "+name+" removed.");
        }else{
            throw new FileNotFoundException("Customer deletion unsuccessful.");
        }
    }
}