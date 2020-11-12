package com.sumitbmdi.linux_path_traversal.entity;

import com.sumitbmdi.linux_path_traversal.constants.Constants;
import com.sumitbmdi.linux_path_traversal.exceptions.DirectoryAlreadyPresent;
import com.sumitbmdi.linux_path_traversal.utility.PathValidator;

import java.util.*;

/**
 * Created by sumit on 21/01/17.
 */
public class Directory extends HierarchyBaseEntity {
    //region MemberVariables
    private final Map<String, Directory> childDirectoriesMap;
    private final Map<String, File> childFilesMap;
    private final Map<String, Directory> symLinksMap;

    private Directory parentDirectory;
    //endregion


    //region Constructor
    public Directory(Directory parentDirectory, String name) {
        this.childDirectoriesMap = new HashMap<>();
        this.childFilesMap = new HashMap<>();
        this.symLinksMap = new HashMap<>();
        this.parentDirectory = parentDirectory;
        this.setName(name);
    }
    //endregion


    //region GetterSetters
    public Directory getParentDirectory() {
        return parentDirectory;
    }
    //endregion


    //region MemberMethods
    public Optional<Directory> getChildDirectory (String name) {
        Directory subDirectory = this.childDirectoriesMap.get(name);
        if (subDirectory != null) {
            return Optional.of(subDirectory);
        } else {
            return Optional.empty();
        }
    }

    public Optional<Directory> getSymlink (String name) {
        Directory linkedDirectory = this.symLinksMap.get(name);
        if (linkedDirectory != null) {
            return Optional.of(linkedDirectory);
        } else {
            return Optional.empty();
        }
    }

//    public Optional<Directory> getSubDirectory (String name) {
////        Optional<Directory>
//    }


    public String getFullPath () {
        Stack<String> ancestorStack = new Stack<>();

        Directory currentDirectory  = this;
        while (currentDirectory != null) {
            ancestorStack.push(currentDirectory.getName());
            currentDirectory = currentDirectory.getParentDirectory();
        }

        return generatePathFromAncestorStack(ancestorStack);
    }


    public void createNewSubDirectory (String name) throws IllegalArgumentException, DirectoryAlreadyPresent {
        if (!PathValidator.isValidName(name)) {
            throw new IllegalArgumentException("Not a valid name " + name);
        }
        if (this.childDirectoriesMap.containsKey(name)) {
            throw new DirectoryAlreadyPresent("Directory with name " + name + "already exists.");
        }

        Directory directory = new Directory(this, name);
        this.childDirectoriesMap.put(name, directory);
    }

    public void createNewSymLink (String name, Directory targetDirectory) throws DirectoryAlreadyPresent {
        if (!PathValidator.isValidName(name)) {
            throw new IllegalArgumentException("Not a valid name " + name);
        }

        if (this.symLinksMap.containsKey(name)) {
            throw new DirectoryAlreadyPresent("Directory with name " + name + "already exists.");
        }

        this.symLinksMap.put(name, targetDirectory);
        System.out.println("SUCC : Symlink Created." );
    }



    public void deleteSubDirectory (String name) throws IllegalArgumentException {
        if (!this.childDirectoriesMap.containsKey(name)) {
            throw new IllegalArgumentException("Directory with name " + name + "doesn't exists");
        }

        this.childDirectoriesMap.remove(name);
    }

    public void printSubDirectories (String prefix) {
        System.out.print(prefix);
        for (String name : this.childDirectoriesMap.keySet()) {
            System.out.print("  " + name);
        }

        for (String name : this.symLinksMap.keySet()) {
            System.out.println(" " + name);
        }
        System.out.println();
    }

    //endregion


    //region UtilityMethods
    private String generatePathFromAncestorStack (Stack<String> ancestorsStack) {
        String path = "";
        ancestorsStack.pop();   //Removing the root directory.

        if (ancestorsStack.size() > 0 ) {
            while (ancestorsStack.size() > 0) {
                path = path + Constants.PATH_SEPARATOR + ancestorsStack.pop();
            }
        } else {
            path = Constants.ROOT_DIRECTORY_SYMBOL;  // Because it is the root directory itself.
        }

        return path;
    }
    //endregion

}
