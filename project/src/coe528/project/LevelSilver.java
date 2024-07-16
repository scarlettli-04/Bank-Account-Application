/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

/**
 *
 * @author Scarlett Li
 */
public class LevelSilver extends Account {
    public LevelSilver(Customer customer){
        super(customer);
    }
    
    @Override
    public void changeLevel(Customer customer){
        if(customer.getBalance()>=10000 && customer.getBalance()<20000){
            customer.setLevel(new LevelGold(customer));
        }else if(customer.getBalance()>=20000){
            customer.setLevel(new LevelPlatinum(customer));
        }
    }
    
    @Override
    public void onlinePurchase(double amount) {
        if(customer.getBalance()>=(amount+20)){
            customer.withdraw(amount+20);
            changeLevel(customer);
        }else{
            throw new IllegalArgumentException("Not enough balance in account to cover purchase.");
        }
    }
}