package BsK;

public class User {
    private String username;
    private String password;
    private int id;
    private int token;
    private boolean loggedIn;
    private String role;


    public User(String username, String password, int id, int token, boolean loggedIn, String role) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.token = token;
        this.loggedIn = loggedIn;
        this.role = role;
    }

    public User(String username, String password, int id, String role) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.role = role;
    }

    public User(String username, String password, int id) {
        this.username = username;
        this.password = password;
        this.id = id;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public int getToken() {
        return token;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
