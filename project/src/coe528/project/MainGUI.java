/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

//JavaFX imports
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Scarlett Li
 */
public class MainGUI extends Application {
    private Stage window;
    private Scene sceneLogin, sceneManager, sceneCustomer; //main scenes
    private Scene sceneAddCustomer, sceneDeleteCustomer; //scenes within Manager
    private Scene sceneDeposit, sceneWithdraw, sceneBalance, scenePurchase; //scenes within Customer
    private Manager manager = Manager.getInstance();
    private Customer customer;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        
        //setting initial login page buttons and textfields
        TextField username = new TextField();
        TextField password = new TextField();
        Button bLogin = new Button("login");
        
        //login page layout
        FlowPane loginLayout = new FlowPane();
        loginLayout.setPadding(new Insets(15, 10, 20, 10)); //inset(top, right, bottom, left)
        loginLayout.setHgap(4); //horizontal gap between columns
        loginLayout.setVgap(10); //vertical gap between rows
        loginLayout.getChildren().addAll(new Label("Username: "), username, new Label("Password: "), password, bLogin);
        sceneLogin = new Scene(loginLayout, 250, 130);
        
        
        //setting manager buttons and textfields
        Button bLogoutManager = new Button("Logout");
        //add customer
        Button bCancelAdd = new Button("cancel");
        Button bConfirmAdd = new Button("confirm");
        Button bAddCustomer = new Button("Add Customer");
        TextField addUsername = new TextField();
        TextField addPassword = new TextField();
        //delete customer
        Button bCancelDelete = new Button("cancel");
        Button bConfirmDelete = new Button("confirm");
        Button bDeleteCustomer = new Button("Delete Customer");
        TextField deleteUser = new TextField();
        
        //manager page layout
        VBox managerLayout = new VBox(5);
        managerLayout.getChildren().addAll(bAddCustomer, bDeleteCustomer, bLogoutManager);
        sceneManager = new Scene(managerLayout, 150, 100);
        
        //new scene for add function
        FlowPane addLayout = new FlowPane();
        addLayout.setPadding(new Insets(15, 10, 20, 10));
        addLayout.setHgap(4);
        addLayout.setVgap(10);
        addLayout.getChildren().addAll(new Label("Customer username: "), addUsername, new Label("Customer password: "), addPassword, bConfirmAdd, bCancelAdd);
        sceneAddCustomer = new Scene(addLayout, 310, 130);
        
        //new scene for delete function
        FlowPane deleteLayout = new FlowPane();
        deleteLayout.setPadding(new Insets(15, 10, 20, 10));
        deleteLayout.setHgap(4);
        deleteLayout.setVgap(10);
        deleteLayout.getChildren().addAll(new Label("Customer: "), deleteUser, bConfirmDelete, bCancelDelete);
        sceneDeleteCustomer = new Scene(deleteLayout, 250, 100);
        
        
        //setting customer buttons and textfields
        Button bLogoutCustomer = new Button("Logout");
        //deposit
        Button bDeposit = new Button("Deposit");
        TextField depositAmt = new TextField();
        Button bConfirmDep = new Button("confirm");
        Button bCancelDep = new Button("cancel");
        //withdraw
        Button bWithdraw = new Button("Withdraw");
        TextField withdrawAmt = new TextField();
        Button bConfirmWith = new Button("confirm");
        Button bCancelWith = new Button("cancel");
        //view balance
        Button bBalance = new Button("Current Balance");
        Button bBackBal = new Button("back");
        //online purchase
        Button bPurchase = new Button("Make Online Purchase");
        TextField purchaseAmt = new TextField();
        Button bConfirmPur = new Button("confirm");
        Button bCancelPur = new Button("cancel");
        
        //customer page layout
        VBox customerLayout = new VBox(5);
        customerLayout.getChildren().addAll(bDeposit, bWithdraw, bBalance, bPurchase, bLogoutCustomer);
        sceneCustomer = new Scene(customerLayout, 150, 160);
        
        //new scene for deposit function
        FlowPane depositLayout = new FlowPane();
        depositLayout.setPadding(new Insets(15, 10, 20, 10));
        depositLayout.setHgap(4);
        depositLayout.setVgap(10);
        depositLayout.getChildren().addAll(new Label("Enter deposit amount: "), depositAmt, bConfirmDep, bCancelDep);
        sceneDeposit = new Scene(depositLayout, 300, 90);
        
        //new scene for withdraw function
        FlowPane withdrawLayout = new FlowPane();
        withdrawLayout.setPadding(new Insets(15, 10, 20, 10));
        withdrawLayout.setHgap(4);
        withdrawLayout.setVgap(10);
        withdrawLayout.getChildren().addAll(new Label("Enter withdraw amount: "), withdrawAmt, bConfirmWith, bCancelWith);
        sceneWithdraw = new Scene(withdrawLayout, 310, 90);
        
        //new scene for balance view
        StackPane balanceLayout = new StackPane();
        balanceLayout.getChildren().addAll(bBackBal);
        sceneBalance = new Scene(balanceLayout, 100, 80);
        
        //new scene for online purchase function
        FlowPane purchaseLayout = new FlowPane();
        purchaseLayout.setPadding(new Insets(15, 10, 20, 10));
        purchaseLayout.setHgap(4);
        purchaseLayout.setVgap(10);
        purchaseLayout.getChildren().addAll(new Label("Purchase amount: "), purchaseAmt, bConfirmPur, bCancelPur);
        scenePurchase = new Scene(purchaseLayout, 310, 90);
        
        
        //initial scene displayed is login page
        window.setScene(sceneLogin);
        window.setTitle("Bank");
        window.show();
        
        //verify login information once login button is pressed
        bLogin.setOnAction(e -> {
            //verify if manager login
            if(username.getText().equals(manager.getUsername()) && password.getText().equals(manager.getPassword())){
                window.setScene(sceneManager);
                
                //set action for add customer
                bAddCustomer.setOnAction(add -> {
                    window.setScene(sceneAddCustomer);
                    bConfirmAdd.setOnAction(confirmAdd -> {
                        try{
                            //call addCustomer method in the manager class
                            manager.addCustomer(addUsername.getText(), addPassword.getText());
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setHeaderText("Customer "+addUsername.getText()+" added");
                            alert.showAndWait();
                        }catch(IOException ex){
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setHeaderText("Customer not added");
                            alert.setContentText("Account "+addUsername.getText()+" already exists");
                            alert.showAndWait();
                        }
                    });
                    bCancelAdd.setOnAction(cancel ->{
                        window.setScene(sceneManager);
                    });
                });
                
                //set action for delete customer
                bDeleteCustomer.setOnAction(delete -> {
                    window.setScene(sceneDeleteCustomer);
                    bConfirmDelete.setOnAction(confirmDelete -> {
                        try {
                            //call deleteCustomer method in the manager class
                            manager.deleteCustomer(deleteUser.getText());
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setHeaderText("Customer "+deleteUser.getText()+" deleted");
                            alert.showAndWait();
                        }catch(FileNotFoundException ex){
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setHeaderText("Deletion failed");
                            alert.setContentText("Account does not exist");
                            alert.showAndWait();
                        }
                    });
                    bCancelDelete.setOnAction(cancel ->{
                        window.setScene(sceneManager);
                    });
                });
                
                //set action to logout
                bLogoutManager.setOnAction(logout -> {
                    window.setScene(sceneLogin);
                });
            }
            
            //verify if customer login
            else{
                File customerFile;
                Scanner readFile;
                String verifyUsername, verifyPassword, role;
                double balance;
                
                try{
                    //open file
                    customerFile = new File(username.getText()+".txt");
                    readFile = new Scanner(customerFile);
                    
                    //read file
                    verifyUsername = readFile.nextLine();
                    verifyPassword = readFile.nextLine();
                    role = readFile.nextLine();
                    balance = readFile.nextDouble();
                    
                    readFile.close();
                    
                    if(verifyUsername.equals(username.getText()) && verifyPassword.equals(password.getText())){
                        customer = new Customer(verifyUsername, verifyPassword);
                        
                        if(balance>0){
                            customer.deposit(balance);
                        }
                        
                        window.setScene(sceneCustomer);
                        
                        //set action for deposit
                        bDeposit.setOnAction(deposit ->{
                            window.setScene(sceneDeposit);
                            bConfirmDep.setOnAction(depConfirm ->{
                                try{
                                    String amount = depositAmt.getText();
                                    //convert string input into double using parseDouble() method
                                    double depAmt = Double.parseDouble(amount);
                                    //call deposit method from customer class
                                    customer.deposit(depAmt);
                                    Alert alert = new Alert(AlertType.INFORMATION);
                                    alert.setHeaderText("$"+depAmt+" deposited");
                                    alert.showAndWait();
                                }catch(NumberFormatException num){
                                    Alert alert = new Alert(AlertType.ERROR);
                                    alert.setHeaderText("Deposit failed");
                                    alert.setContentText("Trying to deposit a none numerical value");
                                    alert.showAndWait();
                                }catch(NullPointerException n){
                                    Alert alert = new Alert(AlertType.ERROR);
                                    alert.setHeaderText("Deposit failed");
                                    alert.setContentText("Deposit value must be greater then 0");
                                    alert.showAndWait();
                                }
                            });
                            bCancelDep.setOnAction(depCancel -> {
                                window.setScene(sceneCustomer);
                            });
                        });
                        
                        //set action for withdraw
                        bWithdraw.setOnAction(withdraw ->{
                            window.setScene(sceneWithdraw);
                            bConfirmWith.setOnAction(withConfirm ->{
                                try{
                                    String amount = withdrawAmt.getText();
                                    //convert string input into double using parseDouble() method
                                    double withAmt = Double.parseDouble(amount);
                                    //call withdraw method from customer class
                                    customer.withdraw(withAmt);
                                    Alert alert = new Alert(AlertType.INFORMATION);
                                    alert.setHeaderText("$"+withAmt+" withdrawn");
                                    alert.showAndWait();
                                }catch(NumberFormatException num){
                                    Alert alert = new Alert(AlertType.ERROR);
                                    alert.setHeaderText("Withdraw failed");
                                    alert.setContentText("Please enter a numerical value");
                                    alert.showAndWait();
                                }catch(NullPointerException n){
                                    Alert alert = new Alert(AlertType.ERROR);
                                    alert.setHeaderText("Withdraw failed");
                                    alert.showAndWait();
                                }
                            });
                            bCancelWith.setOnAction(withCancel -> {
                                window.setScene(sceneCustomer);
                            });
                        });
                        
                        //set action for balance view
                        bBalance.setOnAction(view ->{
                            window.setScene(sceneBalance);
                            Alert alert = new Alert(AlertType.INFORMATION);
                            //call getBalance method from customer class to print out balance
                            alert.setHeaderText("Your current account balance is: $"+customer.getBalance());
                            //call getLevel method from customer class to print current account level
                            alert.setContentText("Current level is "+customer.levelString());
                            alert.showAndWait();
                            bBackBal.setOnAction(goBack -> {
                                window.setScene(sceneCustomer);
                            });
                        });
                        
                        //set action for purchase 
                        bPurchase.setOnAction(purchase ->{
                            window.setScene(scenePurchase);
                            bConfirmPur.setOnAction(purConfirm ->{
                                try{
                                    String amount = purchaseAmt.getText();
                                    //convert string input into double using parseDouble() method
                                    double purAmt = Double.parseDouble(amount);
                                    //call onlinePurchase method from customer class
                                    //NOTE: additional fee for purchase depends on account level
                                    customer.onlinePurchase(purAmt);
                                    Alert alert = new Alert(AlertType.INFORMATION);
                                    alert.setHeaderText("Purchase of $"+purAmt+" successful");
                                    alert.showAndWait();
                                }catch(IOException ex){
                                    Alert alert = new Alert(AlertType.ERROR);
                                    alert.setHeaderText("Purchase failed");
                                    alert.setContentText("Must be over $50.00");
                                    alert.showAndWait();
                                }catch(NumberFormatException num){
                                    Alert alert = new Alert(AlertType.ERROR);
                                    alert.setHeaderText("Purchase failed");
                                    alert.setContentText("Please enter a numerical value");
                                    alert.showAndWait();
                                }catch(IllegalArgumentException arg){
                                    Alert alert = new Alert(AlertType.ERROR);
                                    alert.setHeaderText("Purchase failed");
                                    alert.setContentText("Account balance is not enough to cover purchase");
                                    alert.showAndWait();
                                }
                            });
                            bCancelPur.setOnAction(purCancel -> {
                                window.setScene(sceneCustomer);
                            });
                        });
                        
                        //set action to logout
                        bLogoutCustomer.setOnAction(logout ->{
                            try{
                                //update customer file (to save any changes to balance)
                                FileWriter updateFile = new FileWriter(verifyUsername+".txt");
                                updateFile.write(verifyUsername+"\n"+verifyPassword+"\n"+role+"\n"+customer.getBalance());
                                updateFile.close();
                                System.out.println(verifyUsername+" logged out. Information saved.");
                            }catch(IOException ex){
                                Alert alert = new Alert(AlertType.ERROR);
                                alert.setHeaderText("Logout failed");
                                alert.showAndWait();
                            }
                            window.setScene(sceneLogin);
                        });
                    }else{
                        throw new IOException("Customer file not found");
                    }
                }catch(FileNotFoundException exceptionA){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("Login failed");
                    alert.setContentText("Account does not exist");
                    alert.showAndWait();
                }catch(IOException exceptionB){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("Login failed");
                    alert.setContentText("Incorrect information entered");
                    alert.showAndWait();
                }
            }
        });
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }
}