package com.codes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

class StudentClass {
    public int _start;
    public int _end;
    public String _name;

    public StudentClass(String name, int start, int end) {
        _name = name;
        _start = start;
        _end = end;
    }

    public boolean overlapsWith(StudentClass other) {
        return _start < other._end && _end > other._start;
    }

    public String toString() {
        return "[" + _start + " - " + _end + "] " + _name;
    }
}

public class Schedule {

    List<StudentClass> _classes;

    public Schedule() {
        this._classes = new LinkedList<>();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int noOfDays = Integer.parseInt(sc.nextLine().trim());

        for (int i = 1; i <= noOfDays; i++) {
            int noOfSubjects = Integer.parseInt(sc.nextLine().trim());

            Schedule s = new Schedule();
            for (int j = 0; j < noOfSubjects; j++) {
                String[] line = sc.nextLine().split(" ");

                s.addClass(line[0], Integer.parseInt(line[1].replace(":", "")), Integer.parseInt(line[2].replace(":", "")));
            }
            System.out.println("maximum classes: " + s.getMaxSchedule());
        }

    }

    public void addClass(String name, int startTime, int endTime) {
        _classes.add(new StudentClass(name, startTime, endTime));
    }

    private int getMaxSchedule(int index, Collection<StudentClass> selected) {
        // check if we reached the end of the array
        if (index >= _classes.size()) {
            return 0;
        }

        StudentClass current = _classes.get(index);

        // check if taking this class doesn't conflict with the
        // previously-selected set of classes
        boolean canTakeThisClass = true;
        for (StudentClass other : selected) {
            if (current.overlapsWith(other)) {
                canTakeThisClass = false;
                break;
            }
        }

        // check best schedule if we don't take this class
        int best = getMaxSchedule(index + 1, selected);

        // check best schedule if we take this class (if such is possible)
        if (canTakeThisClass) {
            selected.add(current);
            best = Math.max(best, 1 + getMaxSchedule(index + 1, selected));
            selected.remove(current);
        }

        return best;
    }

    public int getMaxSchedule() {
        Collection<StudentClass> selected = new ArrayList<>();
        return getMaxSchedule(0, selected);
    }
}

