package com.sumitbmdi.linux_path_traversal.commands;

import com.sumitbmdi.linux_path_traversal.directory.HeirarchyManager;
import com.sumitbmdi.linux_path_traversal.exceptions.DirectoryAlreadyPresent;
import com.sumitbmdi.linux_path_traversal.exceptions.InvalidArgumentException;
import com.sumitbmdi.linux_path_traversal.exceptions.InvalidOptionsException;
import com.sumitbmdi.linux_path_traversal.exceptions.NoSuchDirectoryException;

import java.util.List;

/**
 * Created by sumit on 21/01/17.
 */

public interface Command {
    String getCommandName ();

    void execute(List<String> options, List<String> arguments, HeirarchyManager heirarchyManager) throws InvalidArgumentException, InvalidOptionsException, NoSuchDirectoryException, DirectoryAlreadyPresent;

    void printHelp();
}
