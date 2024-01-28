package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

/**
 * GradeBookTest.
 */
public class GradeBookTest {
    /**
     * Тестируем добавление оценки.
     */
    @Test
    public void test_addGrade() throws IllegalArgumentException {
        var book = new GradeBook("name", "surname", 1);
        book.addGrade("1", 5);

        var actual = new HashMap<Integer, HashMap<String, Integer>>();
        actual.put(1, new HashMap<>());
        actual.get(1).put("1", 5);
        assertEquals(actual, book.getGrades());
    }

    /**
     * Тестируем добавление оценки за квалификационную работу.
     */
    @Test
    public void test_set_qualification_workGrade() throws IllegalArgumentException {
        var book = new GradeBook("name", "surname", 1);
        book.set_qualification_workGrade(5);
        assertEquals(5, book.get_qualification_workGrade());
    }

    /**
     * Тестируем смену семестра.
     */
    @Test
    public void test_nextSemester() throws IllegalArgumentException {
        var book = new GradeBook("name", "surname", 1);
        book.nextSemester();
        assertEquals(2, book.getSemester());
    }

    /**
     * Проверяем повышенную стипендию.
     */
    @Test
    public void test_increasedScholarShip() throws IllegalArgumentException {
        var book = new GradeBook("name", "surname", 1);
        book.addGrade("1", 3);
        book.addGrade("2", 3);
        assertFalse(book.increasedScholarship());
        book.nextSemester();
        book.addGrade("3", 5);
        book.addGrade("4", 5);
        assertTrue(book.increasedScholarship());
    }

    /**
     * Проверяем среднюю оценку.
     */
    @Test
    public void test_averageGrade() throws IllegalArgumentException {
        var book = new GradeBook("name", "surname", 1);
        book.addGrade("1", 5);
        book.addGrade("2", 3);
        book.addGrade("3", 4);
        book.addGrade("4", 4);
        book.nextSemester();
        book.addGrade("5", 4);
        book.addGrade("6", 4);
        book.addGrade("7", 3);
        assertEquals(3.86, book.averageGrade());
    }

    /**
     * Проверяем будет ли красный диплом (должен).
     */
    @Test
    public void test_redDiploma_true() throws IllegalArgumentException {
        var book = new GradeBook("name", "surname", 1);
        book.addGrade("1", 5);
        book.addGrade("2", 5);
        book.addGrade("3", 4);
        book.nextSemester();
        book.addGrade("4", 4);
        book.addGrade("5", 4);
        book.addGrade("3", 5);
        book.nextSemester();
        book.addGrade("4", 5);
        book.addGrade("5", 5);
        book.set_qualification_workGrade(5);
        assertTrue(book.redDiploma());
    }

    /**
     * Проверяем будет ли красный диплом (НЕ должен).
     */
    @Test
    public void testRedDiplomaFalse() throws IllegalArgumentException {
        var book = new GradeBook("name", "surname", 1);
        book.addGrade("1", 5);
        book.addGrade("2", 5);
        book.addGrade("3", 4);
        book.nextSemester();
        book.addGrade("4", 4);
        book.addGrade("5", 4);
        book.addGrade("6", 3);
        book.set_qualification_workGrade(5);
        assertFalse(book.redDiploma());
    }
}