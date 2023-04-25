package durgaapi;

import java.util.HashMap;

public class Request implements java.io.Serializable{
    private String command;
    private HashMap<String,Object> data;
    private String login;
    

    public Request(String command, HashMap<String, Object> data, String login) {
        this.command = command;
        this.data = data;
        this.login = login;
        
    }

    public String getCommand() {
        return command;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public String getLogin() {
        return login;
    }
}
