package by.edu.web.command;

import by.edu.web.command.constant.CommandName;
import by.edu.web.command.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private final Map<CommandName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandName.GO_TO_MAIN_PAGE, new GoToMainPage());
        commands.put(CommandName.GO_TO_NEWS_PAGE, new GoToNewsPage());
        commands.put(CommandName.GO_TO_REGISTRATION_PAGE, new GoToRegistrationPage());
        commands.put(CommandName.GO_TO_EDIT_PAGE, new GoToEditPage());
        commands.put(CommandName.GO_TO_ADD_NEWS_PAGE, new GoToAddNewsPage());
        commands.put(CommandName.GO_TO_SIGN_IN_PAGE, new GoToSignInPage());
        commands.put(CommandName.GO_TO_FULL_NEWS_PAGE, new GoToFullNewsPage());
        commands.put(CommandName.ADD_NEWS, new AddNews());
        commands.put(CommandName.GO_TO_CHANGE_ROLE_PAGE, new GoToChangeRolePage());

        commands.put(CommandName.LOCALIZATION, new Localization());

        commands.put(CommandName.DO_REGISTRATION, new DoRegistration());
        commands.put(CommandName.DO_SIGN_IN, new DoSignIn());
        commands.put(CommandName.DO_SIGN_OUT, new DoSignOut());
        commands.put(CommandName.DO_CHANGE_ROLE, new DoChangeRole());
        commands.put(CommandName.DO_CHANGE_NEWS, new DoChangeNews());

        commands.put(CommandName.GO_TO_USER_LIST, new GoToUserList());

        commands.put(CommandName.NO_SUCH_COMMAND, new NoSuchCommand());
    }

    public Command takeCommand(String userCommand) {
        CommandName commandName;
        Command command;

        try {
            commandName = CommandName.valueOf(userCommand.toUpperCase());
            command = commands.get(commandName);


        } catch (IllegalArgumentException | NullPointerException e) {
            command = commands.get(CommandName.NO_SUCH_COMMAND);

        }

        return command;
    }
}
