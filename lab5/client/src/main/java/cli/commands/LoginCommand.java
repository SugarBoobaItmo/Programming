package cli.commands;

import java.util.ArrayList;
import java.util.List;

import cli.ServerAdapter;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;

public class LoginCommand extends AbstractCommand {
    private ServerAdapter serverAdapter;

    public LoginCommand(ServerAdapter serverAdapter) {
        super("login", "Login to the server", new ArrayList<>());
        this.serverAdapter = serverAdapter;
    }

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
                output.writeLine("Login is incorrect, please try again, you stupid dummy"+"\n");
                output.writeLine("Please enter your login (only numb or symb): ");

            }
        }
        // throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    public boolean checkLogin(String login) {
        if (login.matches("[a-zA-Z0-9]+")) {
            return true;
        } else {
            return false;
        }

    }

}
