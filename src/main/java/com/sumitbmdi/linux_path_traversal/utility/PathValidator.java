package com.sumitbmdi.linux_path_traversal.utility;

import com.sumitbmdi.linux_path_traversal.constants.Constants;

/**
 * Created by sumit on 21/01/17.
 */
public class PathValidator {
    public static boolean isValidName (String name) {
        if (name.contains(Constants.PATH_SEPARATOR) ||
                name.contains(Constants.ROOT_DIRECTORY_SYMBOL) ||
                name.contains(" ")) {
            return false;
        }

        return true;
    }
}
