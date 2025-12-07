import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Account{
    private int accounNumber;
    private String holderName;
    private String pin;
    private double balance;
    Account(int accounNumber,String holderName,String pin,double balance){
        this.accounNumber=accounNumber;
        this.holderName=holderName;
        this.pin=pin;
        this.balance=balance;
    }
    public int getAccountNumber(){
        return accounNumber;
    }
    public String getHolderName(){
        return holderName;
    }
    public String getPin(){
        return pin;
    }
    public double getBalance(){
        return balance;
    }
    public void deposit(double amount){
        balance=balance+amount;
    }
    public boolean withdraw(double amount){
        if(amount<=balance){
            balance=balance-amount;
            return true;
        }
        return false;
    }
}
class ATMService{
    private List<Account> accounts;
    ATMService(List<Account> accounts){
        this.accounts=accounts;
    }
    public Account authenticate(int accounNumber,String pin){
        for(Account acc : accounts){
            if(acc.getAccountNumber()==accounNumber && acc.getPin().equals(pin)){
                return acc;
            }
        }
        return null;
    }
    public void showMenu(Account acc){
        Scanner sc = new Scanner(System.in);
        int choice;
        do { 
            System.out.println("\n=== ATM Menu ===");
            System.out.println("1. Balance Inquiry");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.println("Enter choice: ");
            choice=sc.nextInt();

            switch(choice) {
                case 1:
                System.out.println("Current Balance: $" + acc.getBalance());
                break;
                case 2:
                System.out.println("Enter amount to deposit: ");
                double deposit=sc.nextDouble();
                acc.deposit(deposit);
                System.out.println("Deposited $" + deposit);
                break;
                case 3:
                System.out.println("Enter amount to withdraw: ");
                double withdraw=sc.nextDouble();
                if(acc.withdraw(withdraw)){
                    System.out.println("Withdraw $" + withdraw);
                }else{
                    System.out.println("Insufficient balance!");
                }
                break;
                case 4:
                System.out.println("Thank you for using ATM!");
                break;
                default:
                System.out.println("Invalid choice!");

            }
        } while (choice!=4);
    }
}
class FileService{
    private static final String FILE_NAME = "data/accounts.txt";
    public List<Account> loadAccounts(){
        List<Account> accounts=new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))){
            String line;
            while((line=br.readLine()) !=null){
                String[] data = line.split(",");
                int accNum=Integer.parseInt(data[0]);
                String name = data[1];
                String pin = data[2];
                double balance=Double.parseDouble(data[3]);
                accounts.add(new Account(accNum,name,pin,balance));
            }
        }catch(IOException e){
            System.out.println("Error loading accounts: " + e.getMessage());
        }
        return accounts;

    }
    public void saveAccounts(List<Account> accounts){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))){
            for(Account acc : accounts){
                bw.write(acc.getAccountNumber() + "," + acc.getHolderName() + "," + acc.getPin() + "," + acc.getBalance());
                bw.newLine();
            }
        }catch(IOException e){
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }
}
public class ATM {
    public static void main(String[] args) {
        FileService fileService = new FileService();
        List<Account> accounts = fileService.loadAccounts();
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Welcome to ATM Simulation ===");
        System.out.print("Enter Account Number: ");
        int accNum=sc.nextInt();
        sc.nextLine();
        System.out.print("Enter PIN: ");
        String pin = sc.nextLine();
        ATMService atm = new ATMService(accounts);
        Account acc = atm.authenticate(accNum, pin);
        if(acc!=null){
            System.out.println("Login successfull Welcome " + acc.getHolderName());
            atm.showMenu(acc);
            fileService.saveAccounts(accounts);
        }else{
            System.out.println("Invalid credentials!");
        }
    }
    
}

