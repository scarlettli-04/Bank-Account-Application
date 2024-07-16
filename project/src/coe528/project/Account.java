/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

/**
 *
 * @author Scarlett Li
 */
public abstract class Account {
    Customer customer;
    
    public Account(Customer customer){
        this.customer = customer;
    }
    
    abstract void changeLevel(Customer customer);
    
    abstract void onlinePurchase(double amount);
}
