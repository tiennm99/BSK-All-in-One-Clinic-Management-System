package BsK;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Client_Interface {
    private static BufferedReader socketReader;
    private static PrintWriter socketWriter;
    private static int currentToken = -1;
    private static User loggedInUser = null;

    /**
     * Gets the current user's token.
     *
     * @return the user's token
     */
    public static int getCurrentToken() {
        return currentToken;
    }

    /**
     * Sets the current user's token.
     *
     * @param token
     *            the user's new token
     */
    public static void setCurrentToken(int token) {
        currentToken = token;
    }

    /**
     * Sends a request to the server and gets any output returned.
     *
     * @param request
     *            the request to send
     * @return the response from the server
     */
    public static String sendRequest(String request) {
        try {
            socketWriter.write(request);
            socketWriter.println();
            socketWriter.flush();
            String output = socketReader.readLine();
            
            while (output.isEmpty()) {
                output = socketReader.readLine();
            }
            return output;
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getLocalizedMessage());
        }
    }

    /**
     * Connect to server with host and port, setting reader and writer.
     */
    public static void connectToServer() {
        Socket s = null;
        while (s == null) {
            try {
                s = new Socket(Client_Interface.HOST, Client_Interface.PORT);
                socketReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
                socketWriter = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
            } catch (IOException e) {
                System.err.println("Server is not active, sleeping for 1000ms before retrying");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException f) {
                    System.err.println("Interrupted");
                }
            }
        }
    }

    /**
     * Disconnect from server with host and port, closing reader and writer.
     */
    public static void disconnectFromServer() {
        try {
            socketReader.close();
            socketWriter.close();
        } catch (IOException e) {
            System.err.println("Unable to close socket");
        }
    }

    /**
     * CLI Client, presenting options to interact with other users.
     *
     * @param args
     *            unused
     */
    public static void main(String[] args) {
        // Connect client to server
        connectToServer();

        String option = null;
        User selectedUser = null;
        //Infinite menu loop
        while (true) {
            // If the user is logged in, show the main menu
            System.out.println("Enter 1|username|password to login");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String choice = input.split("\\|")[0];
            switch (choice) {
                case "1":
                        String response = sendRequest(input);
                    break;
            
        
            }
        }

        // while(true) {
        //     System.out.println("Welcome to BsK! By D&T Solutions");
        //     System.out.println("Please select an option:");
        //     System.out.println("1. Login");
        //     System.out.println("2. Register");
        //     System.out.println("3. Exit");
        //     Scanner scanner = new Scanner(System.in);
        //     String input = scanner.nextLine();
        //     String choice = input.split("\\|")[0];
        //     switch(choice) {
        //         case "1":
        //             System.out.println("Enter your username:");
        //             String username = scanner.nextLine();
        //             System.out.println("Enter your password:");
        //             String password = scanner.nextLine();
        //             String response = sendRequest("1|" + username + "|" + password);
        //             if (response.equals("Invalid username or password")) {
        //                 System.out.println("Invalid username or password");
        //             } else {
        //                 loggedInUser = new User(username, password, Integer.parseInt(response.split("\\|")[0]), Integer.parseInt(response.split("\\|")[1]), true, response.split("\\|")[2]);
        //                 System.out.println("Successfully logged in as " + username);
        //             }
        //             break;
        //         case "2":
        //             System.out.println("Enter your username:");
        //             username = scanner.nextLine();
        //             System.out.println("Enter your password:");
        //             password = scanner.nextLine();
        //             // response = sendRequest("2|" + username + "|" + password);
        //             if (response.equals("Username already exists")) {
        //                 System.out.println("Username already exists");
        //             } else {
        //                 loggedInUser = new User(username, password, Integer.parseInt(response.split("\\|")[0]), response.split("\\|")[1]);
        //                 System.out.println("Successfully registered as " + username);
        //             }
        //             break;
        //     }
        //}
    }
}

