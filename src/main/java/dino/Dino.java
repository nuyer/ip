package dino;

import java.util.List;
import java.util.Scanner;

import dino.commands.Command;
import dino.commands.Parser;
import dino.tasks.TaskList;

/**
 * The Dino class is a Java program that acts as a task manager, allowing users to input commands to manage and
 * manipulate a list of tasks.
 */

public class Dino {

    private static final String TASKS_CACHE_PATH = ".dino-cache";
    private static final String HORIZONTAL_LINE = "---------------------------------\n";

    private Storage storage;
    private TaskList tasks;

    public Dino() {
        this.storage = new Storage(TASKS_CACHE_PATH);
        this.tasks = new TaskList();
        try {
            tasks = storage.load();
        } catch (Exception e) {
            System.out.println("Issues occurred while loading tasks: " + e.getMessage());
        }
    }

    public void start() {
        greet();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.equalsIgnoreCase("bye")) {
            System.out.println(HORIZONTAL_LINE);
            String response = getResponse(input);
            System.out.println(response);
            System.out.println(HORIZONTAL_LINE);
            input = scanner.nextLine();
        }
        System.out.println("Bye. Hope to see you again soon!");
        scanner.close();
    }
    private static void greet() {
        String greet = "Hello! I'm Dino\n"
                + "What can I do for you?\n"
                + HORIZONTAL_LINE;
        System.out.println(greet);
    }

    /**
     * The function takes an input, processes it using a parser, executes a command on a list of tasks, saves the
     * tasks to storage, and returns a string representation of any messages generated during the process.
     * @param input A string representing the user's input command.
     * @return string message to be print out later.
     */
    public String getResponse(String input) {
        try {
            Command command = Parser.processInput(input);
            List<String> messages = command.execute(this.tasks);
            storage.save(this.tasks);
            return String.join("\n", messages);
        } catch (Exception e) {
            return "Oops! Something went wrong: " + e.getMessage();
        }
    }

    public static void main(String[] args) {
        new Dino().start();
    }
}
