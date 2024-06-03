package org.example;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import java.util.Scanner;

/**
 * Приложение.
 */
public class App {
    /**
     * Запускает парс.
     */
    public static void start() {
        String commands = """
                "exit" - stop working
                "commands" - list of known commands
                "clone" - clone repositories
                "build" - build checker
                "html" - generate html result""";
        System.out.println("Hello!\n" + commands);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                System.out.println("Goodbye!");
                System.exit(0);
            } else if (input.equals("commands")) {
                System.out.println(commands);
            } else if (input.equals("clone")) {
                try {
                    GroovyScriptEngine engine = new GroovyScriptEngine("src/main/resources/scripts");
                    Binding binding = new Binding();
                    engine.run("clone.groovy", binding);
                    System.out.println("Clone finished");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (input.equals("build")) {
                try {
                    GroovyScriptEngine engine = new GroovyScriptEngine("src/main/resources/scripts");
                    Binding binding = new Binding();
                    engine.run("build.groovy", binding);
                    System.out.println("Build finished");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (input.equals("html")) {
                try {
                    GroovyScriptEngine engine = new GroovyScriptEngine("src/main/resources/scripts");
                    Binding binding = new Binding();
                    engine.run("html.groovy", binding);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Unknown command, enter \"help\".");
            }
        }
    }
}
