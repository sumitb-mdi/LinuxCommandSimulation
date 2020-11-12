package com.sumitbmdi.linux_path_traversal.directory;

import com.sumitbmdi.linux_path_traversal.constants.Constants;
import com.sumitbmdi.linux_path_traversal.entity.Directory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by sumit on 21/01/17.
 */

public class HeirarchyManager {

    //region MemberVariables
    private Directory rootDirectory;
    private Directory currentWorkingDirectory;
    //endregion


    //region Constructor
    public HeirarchyManager() {
        this.rootDirectory = new Directory(null, Constants.ROOT_DIRECTORY_SYMBOL);  //Parent of Root will be null.
        this.currentWorkingDirectory = this.rootDirectory;
    }
    //endregion


    //region Getter-Setters

    public Directory getRootDirectory() {
        return this.rootDirectory;
    }

    public Directory getCurrentWorkingDirectory() {
        return currentWorkingDirectory;
    }

    public void setCurrentWorkingDirectory(Directory currentWorkingDirectory) {
        this.currentWorkingDirectory = currentWorkingDirectory;
    }

    //endregion


    //region MemberMethods
    public void reset() {
        this.rootDirectory = new Directory(null, Constants.ROOT_DIRECTORY_SYMBOL);
    }

    public Optional<Directory> getDirectoryFromPath(String path) {
        if (path.substring(0, 1).equalsIgnoreCase(Constants.ROOT_DIRECTORY_SYMBOL)) {
            return getDirectoryFromAbsolutePath(path);
        } else {
            return getDirectoryFromRelativePath(path);
        }
    }
    //endregion


    private Optional<Directory> getDirectoryFromAbsolutePath(String absolutePath) {
        List<String> directoryNames = new ArrayList<>(Arrays.asList(absolutePath.split(Constants.PATH_SEPARATOR)));

        if (directoryNames.size() > 0 && directoryNames.get(0).length() == 0) {
            directoryNames.remove(0);
        }

        return findDirectory(directoryNames, this.rootDirectory);
    }

    private Optional<Directory> getDirectoryFromRelativePath(String relativePath) {
        List<String> directoryNames = new ArrayList<>(Arrays.asList(relativePath.split(Constants.PATH_SEPARATOR)));
        if (directoryNames.size() > 0 && directoryNames.get(0).equalsIgnoreCase(Constants.BACK_SYMBOL)) {
            if (!this.currentWorkingDirectory.getName().equalsIgnoreCase(Constants.ROOT_DIRECTORY_SYMBOL)) {
                Directory parentDirectory = this.currentWorkingDirectory.getParentDirectory();
                int indexOfSeparator = relativePath.indexOf(Constants.PATH_SEPARATOR);
                if (indexOfSeparator > 0) {
                    return getDirectoryFromAbsolutePath(parentDirectory.getFullPath() + Constants.PATH_SEPARATOR + relativePath.substring(indexOfSeparator + 1, relativePath.length()));
                } else {
                    return getDirectoryFromAbsolutePath(parentDirectory.getFullPath());
                }
            } else {
                //TODO
            }
        }

        return findDirectory(directoryNames, getCurrentWorkingDirectory());
    }

    private Optional<Directory> findDirectory(List<String> directoryNames, Directory startingDirectory) {
        Optional<Directory> actualDirectory = Optional.of(startingDirectory);
        Optional<Directory> currentDirectory = Optional.of(startingDirectory);
        Optional<Directory> linkedDirectory = Optional.of(startingDirectory);
        for (int i = 0; i < directoryNames.size(); i++) {
            if (currentDirectory.isPresent()) {
                actualDirectory = currentDirectory.get().getChildDirectory(directoryNames.get(i));
                if (!actualDirectory.isPresent()) {
                    //Check for symlink :
                    linkedDirectory = currentDirectory.get().getSymlink(directoryNames.get(i));
                    if (linkedDirectory.isPresent()) {
                        currentDirectory = linkedDirectory;
                    }
                } else{
                    currentDirectory = actualDirectory;
                }
            } else {
                break;
            }
        }
        return currentDirectory;
    }
}
