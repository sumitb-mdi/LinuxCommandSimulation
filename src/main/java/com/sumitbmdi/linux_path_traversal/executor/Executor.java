package com.sumitbmdi.linux_path_traversal.executor;

import com.sumitbmdi.linux_path_traversal.commands.*;
import com.sumitbmdi.linux_path_traversal.constants.Constants;
import com.sumitbmdi.linux_path_traversal.directory.HeirarchyManager;
import com.sumitbmdi.linux_path_traversal.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sumit on 21/01/17.
 */
public class Executor {
    private final HeirarchyManager heirarchyManager;
    private final Map<String, Command> commandsMap;

    public Executor(HeirarchyManager heirarchyManager) {
        this.heirarchyManager = heirarchyManager;

        this.commandsMap = new HashMap<>();

        registerCommands();
    }

    public void processUserCommand(String userInput) throws CommandNotFoundException {
        String[] splittedInput = userInput.split(" ");
        String commandName = getCommandName(splittedInput);
        List<String> commandOptions = getCommandOptions(splittedInput);
        List<String> commandArguments = getCommandArguments(splittedInput);

        if (this.commandsMap.containsKey(commandName)) {
            try {
                this.commandsMap.get(commandName).execute(commandOptions, commandArguments, this.heirarchyManager);
            } catch (InvalidArgumentException e) {
                System.out.println("ERR: Invalid arguments");
//                e.printStackTrace();
            } catch (InvalidOptionsException e) {
                System.out.println("ERR: Invalid Options.");
//                e.printStackTrace();
            } catch (NoSuchDirectoryException e) {
                System.out.println("ERR : No Such Directory.");
//                e.printStackTrace();
            } catch (DirectoryAlreadyPresent directoryAlreadyPresent) {
                System.out.println("ERR : Directory already present.");
            }
        } else {
            throw new CommandNotFoundException (commandName + "Command Not Found.");
        }
    }


    private void registerCommands() {
        ChangeDirectoryCommand changeDirectoryCommand = new ChangeDirectoryCommand();
        MakeDirectoryCommand makeDirectoryCommand = new MakeDirectoryCommand();
        RemoveDirectoryCommand removeDirectoryCommand = new RemoveDirectoryCommand();
        PresentWorkingDirectoryCommand presentWorkingDirectoryCommand = new PresentWorkingDirectoryCommand();
        ListCommand listCommand = new ListCommand();
        SymLinkCommand symLinkCommand = new SymLinkCommand();

        this.commandsMap.put(changeDirectoryCommand.getCommandName(), changeDirectoryCommand);
        this.commandsMap.put(makeDirectoryCommand.getCommandName(), makeDirectoryCommand);
        this.commandsMap.put(removeDirectoryCommand.getCommandName(), removeDirectoryCommand);
        this.commandsMap.put(presentWorkingDirectoryCommand.getCommandName(), presentWorkingDirectoryCommand);
        this.commandsMap.put(listCommand.getCommandName(), listCommand);
        this.commandsMap.put(symLinkCommand.getCommandName(), symLinkCommand);
    }

    private String getCommandName(String[] spaceSeparatedInput) {
        return spaceSeparatedInput[0];
    }

    private List<String> getCommandOptions(String[] spaceSeparatedInput) {
        List<String> commandOptions = new ArrayList<>();
        for (String str : spaceSeparatedInput) {
            if (str.substring(0, 1).equalsIgnoreCase(Constants.COMMAND_OPTION_PREFIX)) {
                commandOptions.add(str);
            }
        }
        return commandOptions;
    }

    private List<String> getCommandArguments(String[] spaceSeparatedInput) {
        // First word will be the command name, then options and then arguments.
        List<String> commandArguments = new ArrayList<>();

        for (int i = 1; i < spaceSeparatedInput.length; i++) {
            if (!spaceSeparatedInput[i].substring(0, 1).equalsIgnoreCase(Constants.COMMAND_OPTION_PREFIX)) {
                commandArguments.add(spaceSeparatedInput[i]);
            }
        }

        return commandArguments;
    }

}
