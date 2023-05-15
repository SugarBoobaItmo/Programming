package durgaapi;

import java.util.HashMap;

/**
 * Request
 * 
 * This class is used to represent a request.
 */
public class Request implements java.io.Serializable{
    private String command;
    private HashMap<String,Object> data;
    private String login;
    
    /**
     * Constructor
     * 
     * @param command
     * @param data
     * @param login
     */
    public Request(String command, HashMap<String, Object> data, String login) {
        this.command = command;
        this.data = data;
        this.login = login;
        
    }

    /**
     * method to get the command from the request
     * 
     * @return command
     */
    public String getCommand() {
        return command;
    }

    /**
     * method to get the data from the request
     * 
     * @return data
     */
    public HashMap<String, Object> getData() {
        return data;
    }

    /**
     * method to get the login from the request
     * 
     * @return login
     */
    public String getLogin() {
        return login;
    }
}
