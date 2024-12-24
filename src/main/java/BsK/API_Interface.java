package BsK;



interface API_Interface {
    /*
     * This class is used to define the API interface for the application.
     * It will contain the methods that will be used to interact with the API.
     */

    boolean loginUser(String username, String password);
    /*
    * This method is used to login the user.
    * It will take the username and password as input and return the user object.
    */

    boolean registerUser(String username, String password);
    /*
    * This method is used to register the user.
    * It will take the username and password as input and return the user object.
    */

    

}
