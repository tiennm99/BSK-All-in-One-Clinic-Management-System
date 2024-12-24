package BsK;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class API implements API_Interface, Runnable {
    
    private static final String DATABASE_URL = "jdbc:sqlite:./Project5DB.db";
    private static List<long[]> activeUsers;
    private static AtomicInteger currentToken;
    private PrintWriter writer;
    private Socket socket;
    private int currentUser;

    /**
     * API constructor, creating a new API object with a writer attached to the
     * specified client, and index of the current user within the activeUsers array.
     *
     * @param socket      a socket that connects to a client for interaction
     * @param currentUser the current user that is awaiting interaction
     */
    public API(Socket socket, int currentUser) {
        this.socket = socket;
        this.currentUser = currentUser;
    }

    /**
     * Main method, facilitating interactions with multiple clients asynchronously.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        // Create a synchronized list of long arrays
        activeUsers = Collections.synchronizedList(new ArrayList<long[]>());
        // Start a token count at 0
        currentToken = new AtomicInteger(0);

        // Open a new socket server at port 54842
        try (ServerSocket socketServer = new ServerSocket(54842)) {
            System.out.println("Awaiting Connections");
            // Will be removed in production
            if (args.length > 0)
                socketServer.setSoTimeout(5000);
            while (true) {

                // Wait for a client to accept a connection
                Socket s = socketServer.accept();

                // Once the client has accepted a connection, add a new user identification to
                // activeUsers
                activeUsers.add(new long[3]);
                // Create a new Thread object, with the following Runnable
                Thread acceptRequest = new Thread(new API(s, activeUsers.size() - 1));

                // Accept a connection request
                acceptRequest.start();
            }
        } catch (IOException e) {
            System.err.println("An unknown server error occurred");
        }
    }

    /**
     * Handle client interaction, with given socket.
     *
     * @param s The client's socket
     */
    public void run() {
        // Connect to the client's input and output streams
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter pWriter = new PrintWriter(socket.getOutputStream())) {
            this.writer = pWriter;

            System.out.println("Client " + socket + " connected");
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    // Split input by "|" character
                    String[] info = line.split("\\|", -1);
                    // Run command, and print client info, command name, and success/failure
                    switch (Integer.parseInt(info[0])) {
                        case 1:
                            if (info.length != 3)
                                throw new Exception();
                            System.out.println(socket + " -> Create User:"
                                    + (this.loginUser(info[1], info[2]) ? "Success" : "Failure"));
                            break;
                        default:
                            // If an invalid command id is entered, send back error message
                            pWriter.write("Error: Invalid command");
                            pWriter.println();
                            pWriter.flush();
                            System.err.println(socket + " -> Error: Invalid command");
                            break;
                    }
                } catch (Exception e) {
                    // Bad input has been received
                    pWriter.write("Error: Bad input data");
                    pWriter.println();
                    pWriter.flush();
                    System.err.println(socket + " -> Error: Bad input data");
                }
            }
            // When connection closes, null current client index so no clients can access
            activeUsers.set(currentUser, null);
            // Log that the client has disconnected
            System.out.println("Client " + socket + " disconnected");
        } catch (IOException e) {
            System.err.println("Error when trying to read and write from server " + socket);
        } catch (Exception e) {
            System.err.println("An unknown error occurred");
        }
    }

    public boolean loginUser(String userName, String passWord) {
        System.out.println("Login User");

        return true;
    }
    
    public boolean registerUser(String userName, String passWord) {
        System.out.println("Register User");
        return true;
    }

}