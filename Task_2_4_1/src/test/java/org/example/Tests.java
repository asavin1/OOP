package org.example;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тестим скрипты.
 */
public class Tests {
    /**
     * Метод для удаления папки.
     */
    private void deleteRepositories(){
        File currentDir = new File("./repositories");
        if (deleteDirectory(currentDir)) {
            System.out.println("Directory deleted successfully.");
        } else {
            System.out.println("Failed to delete directory.");
        }
    }

    /**
     * Метод удаляет содержимое папки.
     */
    public static boolean deleteDirectory(File dir) {
        if (!dir.exists()) {
            return false;
        }
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (String child : children) {
                    boolean success = deleteDirectory(new File(dir, child));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }

    /**
     * Тестируем клонирование.
     */
    @Test
    public void test1() {
        deleteRepositories();
        try {
            GroovyScriptEngine engine =
                    new GroovyScriptEngine("src/main/resources/scripts");
            Binding binding = new Binding();
            engine.run("clone.groovy", binding);
        } catch (Exception e) {
            System.out.println("Repositories could not be cloned.");
        }

        assertTrue(new File("./repositories/RuslanChudinov/Task_1_1_1").exists());
        assertTrue(new File("./repositories/RuslanChudinov/Task_1_1_2").exists());
        assertTrue(new File("./repositories/RuslanChudinov/Task_1_2_1").exists());
        assertTrue(new File("./repositories/RuslanChudinov/Task_1_3_1").exists());
        assertTrue(new File("./repositories/RuslanChudinov/Task_1_4_1").exists());
        assertTrue(new File("./repositories/RuslanChudinov/Task_1_5_1").exists());
        assertTrue(new File("./repositories/RuslanChudinov/Task_1_5_2").exists());
        assertTrue(new File("./repositories/RuslanChudinov/Task_2_1_1").exists());
        assertTrue(new File("./repositories/RuslanChudinov/Task_2_2_1").exists());
        assertTrue(new File("./repositories/RuslanChudinov/Task_2_3_1").exists());
        assertTrue(new File("./repositories/RuslanChudinov/Task_2_4_1").exists());

        assertTrue(new File("./repositories/AnatolySavin/Task_1_1_1").exists());
        assertTrue(new File("./repositories/AnatolySavin/Task_1_1_2").exists());
        assertTrue(new File("./repositories/AnatolySavin/Task_1_2_1").exists());
        assertTrue(new File("./repositories/AnatolySavin/Task_1_3_1").exists());
        assertTrue(new File("./repositories/AnatolySavin/Task_1_4_1").exists());
        assertTrue(new File("./repositories/AnatolySavin/Task_2_1_1").exists());
        assertTrue(new File("./repositories/AnatolySavin/Task_2_2_1").exists());

    }

    /**
     * Тестируем build.
     */
    @Test
    public void test2() {
        deleteRepositories();
        try {
            GroovyScriptEngine engine =
                    new GroovyScriptEngine("src/main/resources/scripts");
            Binding binding = new Binding();
            LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> tasks;
            tasks = (LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>>) engine.run("buildChecker.groovy", binding);

            assertTrue(tasks.containsKey("Task_1_1_1"));
            assertTrue(tasks.containsKey("Task_1_1_2"));
            assertTrue(tasks.containsKey("Task_1_2_1"));
            assertTrue(tasks.containsKey("Task_1_2_2"));
            assertTrue(tasks.containsKey("Task_1_3_1"));
            assertTrue(tasks.containsKey("Task_1_4_1"));
            assertTrue(tasks.containsKey("Task_1_5_1"));
            assertTrue(tasks.containsKey("Task_1_5_2"));
            assertTrue(tasks.containsKey("Task_2_1_1"));
            assertTrue(tasks.containsKey("Task_2_1_2"));
            assertTrue(tasks.containsKey("Task_2_2_1"));
            assertTrue(tasks.containsKey("Task_2_3_1"));
            assertTrue(tasks.containsKey("Task_2_4_1"));

            assertTrue(tasks.get("Task_1_1_1").containsKey("RuslanChudinov"));
            assertTrue(tasks.get("Task_1_1_1").containsKey("AnatolySavin"));

            assertTrue(tasks.get("Task_1_1_2").get("RuslanChudinov").containsKey("build"));
            assertTrue(tasks.get("Task_1_1_2").get("RuslanChudinov").containsKey("test"));
            assertTrue(tasks.get("Task_1_1_2").get("RuslanChudinov").containsKey("javadoc"));

        } catch (Exception e) {
            System.out.println("Repositories could not be cloned.");
        }
    }

    /**
     * Тестируем генерацию html.
     */
    @Test
    public void test3() {
        deleteRepositories();
        try {
            GroovyScriptEngine engine =
                    new GroovyScriptEngine("src/main/resources/scripts");
            Binding binding = new Binding();
            engine.run("html.groovy", binding);
        } catch (Exception e) {
            System.out.println("Repositories could not be cloned.");
        }

        String filePath = "./report.html";
        StringBuilder html = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                html.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(html.toString().contains("<caption>Task_1_1_1</caption>"));
        assertTrue(html.toString().contains("<caption>Task_1_1_2</caption>"));
        assertTrue(html.toString().contains("<caption>Task_1_2_1</caption>"));
        assertTrue(html.toString().contains("<caption>Task_1_2_2</caption>"));
        assertTrue(html.toString().contains("<caption>Task_1_3_1</caption>"));
        assertTrue(html.toString().contains("<caption>Task_1_4_1</caption>"));
        assertTrue(html.toString().contains("<caption>Task_1_5_1</caption>"));
        assertTrue(html.toString().contains("<caption>Task_1_5_2</caption>"));
        assertTrue(html.toString().contains("<caption>Task_2_1_1</caption>"));
        assertTrue(html.toString().contains("<caption>Task_2_1_2</caption>"));
        assertTrue(html.toString().contains("<caption>Task_2_2_1</caption>"));
        assertTrue(html.toString().contains("<caption>Task_2_3_1</caption>"));
        assertTrue(html.toString().contains("<caption>Task_2_4_1</caption>"));
    }
}
