package com.sumitbmdi.linux_path_traversal.commands;

import com.sumitbmdi.linux_path_traversal.constants.CommandsHelp;
import com.sumitbmdi.linux_path_traversal.directory.HeirarchyManager;
import com.sumitbmdi.linux_path_traversal.entity.Directory;
import com.sumitbmdi.linux_path_traversal.exceptions.DirectoryAlreadyPresent;
import com.sumitbmdi.linux_path_traversal.exceptions.InvalidArgumentException;
import com.sumitbmdi.linux_path_traversal.exceptions.InvalidOptionsException;
import com.sumitbmdi.linux_path_traversal.exceptions.NoSuchDirectoryException;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by sumit on 22/01/17.
 */
public class SymLinkCommand implements Command {
    private static final String name = "ln";
    private static final Logger logger = Logger.getLogger(SymLinkCommand.class.getName());

    @Override
    public String getCommandName() {
        return this.name;
    }

    @Override
    public void execute(List<String> options, List<String> arguments, HeirarchyManager heirarchyManager) throws InvalidArgumentException, InvalidOptionsException, NoSuchDirectoryException, DirectoryAlreadyPresent {
        logger.log(Level.INFO, "Executing Remove Directory Command");

        if (!isOptionsValid(options)) {
            throw new InvalidOptionsException("Invalid Options.");
        }
        if (!isValidArgument(arguments)) {
            throw new InvalidArgumentException("Invalid Argument.");
        }

        String symlinkName = arguments.get(0);
        String pathToTargetDir = arguments.get(1);

        Optional<Directory> targetDirectoryOptional = heirarchyManager.getDirectoryFromPath(pathToTargetDir);
        if (targetDirectoryOptional.isPresent()) {
            heirarchyManager.getCurrentWorkingDirectory().createNewSymLink(symlinkName, targetDirectoryOptional.get());
        } else {
            throw new NoSuchDirectoryException("No Such Directory Exists.");
        }

    }

    @Override
    public void printHelp() {
        System.out.println(CommandsHelp.RM_COMMAND_HELP);
    }


    private boolean isOptionsValid (List<String> optionsList) {
        // TODO: Wire up the Logic.
        return true;
    }

    private boolean isValidArgument (List<String> argumentsList) {
        // TODO : Wire up the logic.
        if (argumentsList.size() != 2) return false;
        return true;
    }
}
