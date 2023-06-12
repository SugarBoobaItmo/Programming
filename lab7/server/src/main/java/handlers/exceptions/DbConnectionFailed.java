package handlers.exceptions;

public class DbConnectionFailed extends Exception{
    private String message;

    public DbConnectionFailed() {
        this.message = "Could not connect to the database. Please, try again later.";
    }

    public DbConnectionFailed(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    
}
