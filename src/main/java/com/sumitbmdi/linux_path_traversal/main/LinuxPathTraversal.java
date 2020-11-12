package com.sumitbmdi.linux_path_traversal.main;

import com.sumitbmdi.linux_path_traversal.constants.Constants;
import com.sumitbmdi.linux_path_traversal.directory.HeirarchyManager;
import com.sumitbmdi.linux_path_traversal.exceptions.CommandNotFoundException;
import com.sumitbmdi.linux_path_traversal.executor.Executor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Created by sumit on 21/01/17.
 */
public class LinuxPathTraversal {
    private static final Logger logger = Logger.getLogger(LinuxPathTraversal.class.getName());
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private Executor executor;
    private HeirarchyManager heirarchyManager;

    public LinuxPathTraversal() {
        this.heirarchyManager = new HeirarchyManager();
        this.executor = new Executor(this.heirarchyManager);
    }

    public void reset() {
        this.heirarchyManager = new HeirarchyManager();
        this.executor = new Executor(this.heirarchyManager);
    }

    public static void main(String[] args) throws IOException {
        LogManager.getLogManager().reset();
        LinuxPathTraversal linuxPathTraversal = new LinuxPathTraversal();
        String input;
        System.out.println("Application Started ... ");

        while (true) {
            input = bufferedReader.readLine();
            if (input.length() < 1) continue;
            if (input.equalsIgnoreCase(Constants.SESSION_CLEAR)) {
                logger.log(Level.INFO, "Session clear requested.");
                linuxPathTraversal.reset();
                System.out.println("SUCC: CLEARED: RESET TO ROOT");
            } else {
                try {
                    linuxPathTraversal.executor.processUserCommand(input);
                } catch (CommandNotFoundException e) {
                    logger.log(Level.SEVERE, "Check the command name : " + e.getMessage());
                    System.out.println("ERR: CANNOT RECOGNIZE INPUT.");
                }
            }
        }

    }
}
