package by.edu.web.command;

import by.edu.web.command.constant.CommandName;

public class CommandRedirect {
    public static String page(CommandName commandName) {
        return "Controller?command=" + String.valueOf(commandName).toLowerCase();
    }
}

