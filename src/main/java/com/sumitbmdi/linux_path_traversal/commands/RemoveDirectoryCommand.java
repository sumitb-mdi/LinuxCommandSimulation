package com.sumitbmdi.linux_path_traversal.commands;

import com.sumitbmdi.linux_path_traversal.constants.CommandsHelp;
import com.sumitbmdi.linux_path_traversal.directory.HeirarchyManager;
import com.sumitbmdi.linux_path_traversal.entity.Directory;
import com.sumitbmdi.linux_path_traversal.exceptions.InvalidArgumentException;
import com.sumitbmdi.linux_path_traversal.exceptions.InvalidOptionsException;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by sumit on 21/01/17.
 */
public class RemoveDirectoryCommand implements Command {
    private static final String COMMAND_NAME = "rm";
    private static final Logger logger = Logger.getLogger(RemoveDirectoryCommand.class.getName());

    @Override
    public void execute(List<String> options, List<String> arguments, HeirarchyManager heirarchyManager) throws InvalidOptionsException, InvalidArgumentException {
        logger.log(Level.INFO, "Executing Remove Directory Command");

        if (!isOptionsValid(options)) {
            throw new InvalidOptionsException("Invalid Options.");
        }
        if (!isValidArgument(arguments)) {
            throw new InvalidArgumentException("Invalid Argument.");
        }

        Optional<Directory> directoryOptional = heirarchyManager.getDirectoryFromPath(arguments.get(0));
        if (directoryOptional.isPresent() && directoryOptional.get() != heirarchyManager.getRootDirectory()) {
            directoryOptional.get().getParentDirectory().deleteSubDirectory(directoryOptional.get().getName());
            System.out.println("SUCC: DELETED");
        } else {
            System.out.println("ERR: INVALID PATH");
        }
    }

    @Override
    public void printHelp() {
        System.out.println(CommandsHelp.RM_COMMAND_HELP);
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }


    private boolean isOptionsValid (List<String> optionsList) {
        // TODO: Wire up the Logic.
        return true;
    }

    private boolean isValidArgument (List<String> argumentsList) {
        // TODO : Wire up the logic.
        return true;
    }
}
