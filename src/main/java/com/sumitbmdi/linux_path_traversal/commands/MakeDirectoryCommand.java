package com.sumitbmdi.linux_path_traversal.commands;

import com.sumitbmdi.linux_path_traversal.constants.CommandsHelp;
import com.sumitbmdi.linux_path_traversal.constants.Constants;
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
 * Created by sumit on 21/01/17.
 */
public class MakeDirectoryCommand implements Command {
    private static final String COMMAND_NAME = "mkdir";
    private static final Logger logger = Logger.getLogger(MakeDirectoryCommand.class.getName());

    @Override
    public void execute(List<String> options, List<String> arguments, HeirarchyManager heirarchyManager) throws InvalidOptionsException, InvalidArgumentException, NoSuchDirectoryException, DirectoryAlreadyPresent {
        logger.log(Level.INFO, "Executing Make Directory Command");
        if (!isOptionsValid(options)) {
            throw new InvalidOptionsException("Invalid Options.");
        }
        if (!isValidArgument(arguments)) {
            throw new InvalidArgumentException("Invalid Argument.");
        }

        if (arguments.get(0).indexOf(Constants.PATH_SEPARATOR.charAt(0)) > 0) {  //Some internal path for Directory creation.
            String[] directoryNames = arguments.get(0).split(Constants.PATH_SEPARATOR);
            String directoryNameToBeCreated = directoryNames[directoryNames.length - 1];

            Optional<Directory> parentDirectoryForNew = heirarchyManager.getDirectoryFromPath(arguments.get(0).substring(0, arguments.get(0).lastIndexOf(Constants.PATH_SEPARATOR)));

            if (parentDirectoryForNew.isPresent()) {
                createDirectory(parentDirectoryForNew.get(), directoryNameToBeCreated);
            } else {
                throw new NoSuchDirectoryException("No Such Directory :" + arguments.get(0));
            }
        } else {
            createDirectory(heirarchyManager.getCurrentWorkingDirectory(), arguments.get(0));
        }

    }

    @Override
    public void printHelp() {
        System.out.println(CommandsHelp.MKDIR_COMMAND_HELP);
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

        if (argumentsList.size() != 1) return false;
        return true;
    }

    private void createDirectory (Directory parentDirectory, String newDirectoryName) throws DirectoryAlreadyPresent {
        parentDirectory.createNewSubDirectory(newDirectoryName);
        System.out.println("SUCC: CREATED");
    }
}
