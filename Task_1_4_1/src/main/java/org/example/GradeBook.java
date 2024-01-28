package org.example;

import java.util.HashMap;

/**
 * GradeBook.
 */
public class GradeBook {
    private String name;
    private String surname;
    private int group;
    private int semester;
    private HashMap<Integer, HashMap<String, Integer>> grades;
    private int qualificationworkGrade;
    private HashMap<String, Integer> gradesDiploma;

    /**
     * Конструктор.
     *
     * @param name
     * @param surname
     * @param group
     */
    public GradeBook(String name, String surname, int group) {
        this.name = name;
        this.surname = surname;
        this.group = group;
        this.semester = 1;
        this.grades = new HashMap<>();
        this.grades.put(this.semester, new HashMap<>());
        this.qualificationworkGrade = 0;
        this.gradesDiploma = new HashMap<>();
    }

    /**
     * Getter для оценки квалификационной работу.
     */
    public int get_qualification_workGrade() {
        return this.qualificationworkGrade;
    }

    /**
     * Getter для списка оценок.
     */
    public HashMap<Integer, HashMap<String, Integer>> getGrades() {
        return this.grades;
    }

    /**
     * Getter для номера текущего семестра.
     */
    public int getSemester() {
        return this.semester;
    }

    /**
     * Выставляем оценку для квалификационной работы.
     *
     * @param grade
     * @throws IllegalArgumentException
     */
    public void set_qualification_workGrade(int grade) throws IllegalArgumentException {
        if (grade > 5 || grade < 2) {
            throw new IllegalArgumentException("The grade must be between 2 and 5");
        }
        this.qualificationworkGrade = grade;
    }

    /**
     * Добавление оценки
     *
     * @param subj
     * @param grade
     * @throws IllegalArgumentException
     */
    public void addGrade(String subj, int grade) throws IllegalArgumentException {
        if (grade > 5 || grade < 2) {
            throw new IllegalArgumentException("The grade must be between 2 and 5");
        }
        if (gradesDiploma.containsKey(subj)) {
            if (gradesDiploma.get(subj) < grade) {
                gradesDiploma.put(subj, grade);
            }
        } else {
            gradesDiploma.put(subj, grade);
        }
        this.grades.get(this.semester).put(subj, grade);
    }


    /**
     * Переход на следующий семестр.
     *
     * @throws IllegalArgumentException
     */
    public void nextSemester() throws IllegalArgumentException {
        if (this.semester == 8) {
            throw new IllegalArgumentException("This semester is last");
        }
        this.semester++;
        this.grades.put(this.semester, new HashMap<>());
    }

    /**
     * Повышенная стипендия.
     */
    public boolean increasedScholarship() {
        for (var grade : this.grades.get(this.semester).values()) {
            if (grade < 5) {
                return false;
            }
        }
        return true;
    }

    /**
     * Средняя оценка.
     */
    public double averageGrade() {
        int sum = 0;
        int countGrades = 0;
        for (int sem = 1; sem <= this.semester; sem++) {
            for (var grade : this.grades.get(sem).values()) {
                sum += grade;
                countGrades++;
            }
        }
        return Math.round((double) sum / countGrades * Math.pow(10, 2)) / Math.pow(10, 2);
    }

    /**
     * Проверка на красный диплом.
     */
    public boolean redDiploma() {
        if (this.qualificationworkGrade != 5) {
            return false;
        }
        for (var grade : this.gradesDiploma.values()) {
            if (grade <= 3) {
                return false;
            }
        }

        int numberOfFive = 0;
        for (var grade : this.gradesDiploma.values()) {
            if (grade == 5) {
                numberOfFive++;
            }
        }
        if (numberOfFive < 0.75 * this.gradesDiploma.size()) {
            return false;
        }
        return true;
    }

    /**
     * Принт.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Student: ").append(this.name).append(" ").append(this.surname);
        str.append("\nGroup: ").append(this.group);

        for (int sem = 1; sem <= 8; sem++) {
            str.append("\nSemester ").append(sem);
            if (this.grades.containsKey(sem)) {
                if (this.grades.get(sem).isEmpty()) {
                    str.append("\n\t-");
                }
                for (var subj : this.grades.get(sem).keySet()) {
                    str.append("\n\t").append(this.grades.get(sem).get(subj));
                    str.append("\t").append(subj);
                }
            } else {
                str.append("\n\t-");
            }

        }
        return str.toString();
    }
}