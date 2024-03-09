package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private ConsoleService consoleService = new ConsoleService();
    private AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private AuthenticatedUser currentUser;
    private boolean isTransferMenu;
    private static int transferId;
    public App(ConsoleService consoleService, AuthenticationService authenticationService) {
        this.consoleService = consoleService;
        this.authenticationService = authenticationService;
    }


    //notes for testing - user : user pass : pass, other user id : 1002

    public static void main(String[] args) {
        App app = new App(new ConsoleService(), new AuthenticationService(API_BASE_URL));
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                isTransferMenu = true;
                while (isTransferMenu){
                    viewTransferHistory();
                }
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
		// TODO Auto-generated method stub
        AccountService accountService = new AccountServiceREST(API_BASE_URL, currentUser);
        System.out.println("Your current balance: " + accountService.getBalance());
		
	}

	private void viewTransferHistory() {
		// TODO Auto-generated method stub
        Scanner scanner = new Scanner(System.in);
        listTransfers();
        System.out.println("Please enter transfer ID to view details (0 to cancel):");
        int transferId = scanner.nextInt();
        transferDetails(transferId);
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
		// TODO Auto-generated method stub
        AccountServiceREST account = new AccountServiceREST(API_BASE_URL, currentUser);
        Scanner scanner = new Scanner(System.in);
        TransferServiceREST send = new TransferServiceREST(API_BASE_URL, currentUser);
        listUsers();
        System.out.println("Enter ID of User you want to send Bucks to : ");
        int userTo = scanner.nextInt();
        System.out.println("Enter amount you want to send : ");
        BigDecimal bucks = scanner.nextBigDecimal();
        if (account.getBalance().compareTo(bucks) < 0) {
            System.out.println("Insufficient funds.");
        } else if (bucks.compareTo(BigDecimal.valueOf(0)) <= 0) {
            System.out.println("Transfer amount must be above 0.");
        } else {
            transferIdMaker();
            int accountId = account.getAccountByUserId(currentUser.getUser().getId()).getId();
            int userToAccountId = account.getAccountByUserId(userTo).getId();
            send.createTransfer(transferId, accountId, userToAccountId, 2, bucks);
        }
	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		//note - type id for requestbucks is 1
	}

    private int transferIdMaker(){
        TransferServiceREST transferServ = new TransferServiceREST(API_BASE_URL, currentUser);
        Transfer[] transfers = transferServ.getAllTransfers();
        int highestId = 0;
        for (Transfer transfer : transfers){
            if (transfer.getId() > highestId) {
                highestId = transfer.getId();
            }
        }
        transferId = highestId + 1;
        return transferId;
    }

    private void listUsers(){
        UserServiceREST userServ = new UserServiceREST(API_BASE_URL, currentUser);
        User[] users = userServ.getUsers();
        System.out.println("Users");
        System.out.println("---------------------");
        System.out.println("    Id    |    Name    " );
        System.out.println("---------------------");
        for (User user : users){
            System.out.println(user.getId() + "      |    " + user.getUsername());
        }
    }

    private void listTransfers(){
        UserServiceREST userServ = new UserServiceREST(API_BASE_URL, currentUser);
        AccountServiceREST account = new AccountServiceREST(API_BASE_URL, currentUser);
        TransferServiceREST transferServ = new TransferServiceREST(API_BASE_URL, currentUser);
        Transfer[] transfers = transferServ.getTransfersByUserId(currentUser.getUser().getId());
        // this is for grabbing the user
        int accountFrom = 0;
        int accountTo = 0;
        System.out.println("Transfers");
        System.out.println("---------------------------------------------------------------");
        System.out.println("    Id    |    Account From    |    Account To    |    Amount    " );
        System.out.println("---------------------------------------------------------------");
        for (Transfer transfer : transfers){
            accountTo = account.getAccountById(transfer.getAccountTo()).getUserId();
            accountFrom = account.getAccountById(transfer.getAccountFrom()).getUserId();
            User accountToUser = userServ.getUserById(accountTo);
            User accountFromUser = userServ.getUserById(accountFrom);
            System.out.println("    " + transfer.getId() + "              " + accountFromUser.getUsername()
            + "              " + accountToUser.getUsername() + "              $" + transfer.getAmount() );
        }
    }

    private void transferDetails(int transferId){
        TransferServiceREST transferServ = new TransferServiceREST(API_BASE_URL, currentUser);
        AccountServiceREST accountServ = new AccountServiceREST(API_BASE_URL, currentUser);
        UserServiceREST userServ = new UserServiceREST(API_BASE_URL, currentUser);
        TransferTypeServiceREST transferTypeServ = new TransferTypeServiceREST(API_BASE_URL, currentUser);
        TransferStatusServiceREST transferStatusServ = new TransferStatusServiceREST(API_BASE_URL, currentUser);
        Transfer[] transfers = transferServ.getTransfersByUserId(currentUser.getUser().getId());
        boolean found = false;
        if(transferId == 0){
            isTransferMenu = false;
            return;
        } else {
            for (Transfer transfer : transfers){
                if (transfer.getId() == transferId){
                    found = true;
                    User fromUser = userServ.getUserById(accountServ.getAccountById(transfer.getAccountFrom()).getUserId());
                    User toUser = userServ.getUserById(accountServ.getAccountById(transfer.getAccountTo()).getUserId());
                    TransferType transferType = transferTypeServ.getTransferTypeById(transfer.getTransferTypeId());
                    TransferStatus transferStatus = transferStatusServ.getTransferStatusById(transfer.getTransferStatusId());
                    System.out.println("-------------------------------------");
                    System.out.println("        Transfer Details");
                    System.out.println("-------------------------------------");
                    System.out.println("Id : " + transfer.getId());
                    System.out.println("From: " + fromUser.getUsername());
                    System.out.println("To: " + toUser.getUsername());
                    System.out.println("Type: " + transferType.getDescription());
                    System.out.println("Status: " + transferStatus.getDescription());
                    System.out.println("Amount: $" + transfer.getAmount());
                    break;
                }
            } if (!found) {
                System.out.println("Transfer not found");
            }
        }   consoleService.pause();
    }
}
