package cli.commands;

import java.util.ArrayList;
import java.util.List;

import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import serveradapter.ServerAdapter;

/**
 * 
 * The LoginCommand class is responsible for handling the login command to the
 * server.
 * 
 * It prompts the user to enter a login consisting of only alphanumeric
 * characters, and sets the login on the ServerAdapter.
 */
public class LoginCommand extends AbstractCommand {
    private ServerAdapter serverAdapter;

    /**
     * 
     * Constructs a new LoginCommand with the specified ServerAdapter.
     * 
     * @param serverAdapter the ServerAdapter to use for communication with the
     *                      server
     */
    public LoginCommand(ServerAdapter serverAdapter) {
        super("login", "Login to the server", new ArrayList<>());
        this.serverAdapter = serverAdapter;
    }

    /**
     * 
     * Executes the login command by prompting the user to enter a valid login, and
     * setting the login on the ServerAdapter.
     * 
     * @param inlineParams    a list of inline parameters for the command (not used
     *                        in this case)
     * 
     * @param input           the input reader to use for reading user input
     * 
     * @param output          the output writer to use for writing output to the
     *                        user
     * 
     * @param disableAttempts a flag indicating whether to disable attempts at
     *                        executing the command
     * 
     * @throws ExecuteError if an error occurs while executing the command
     */
    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output, boolean disableAttempts)
            throws ExecuteError {

        output.writeLine("Please enter your login (only numb or symb): ");
        boolean login_flag = false;
        while (!login_flag) {
            String login = input.readLine();
            login_flag = checkLogin(login);
            if (login_flag) {
                serverAdapter.setLogin(login);
            } else {
                output.writeLine("Login is incorrect, please try again, you stupid dummy" + "\n");
                output.writeLine("Please enter your login (only numb or symb): ");

            }
        }
    }

    /**
     * 
     * Checks whether the given login consists of only alphanumeric characters.
     * 
     * @param login the login to check
     * @return true if the login consists of only alphanumeric characters, false
     *         otherwise
     */
    public boolean checkLogin(String login) {
        if (login.matches("[a-zA-Z0-9]+")) {
            return true;
        } else {
            return false;
        }

    }

}
