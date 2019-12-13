package MainPack;

import Processes.Process;
import Schedulers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

// TODO use proper OOP structure?
// TODO build menu?
// TODO assert custom comparators are working properly
// TODO create roundRobin variable and read round robin time quantum

public class Main {
    public static Scanner inputReader;
    private static Integer contextSwitchingDuration;
    private static List<Process> processes = new ArrayList<>();
    //    public static Scanner inputReader = new Scanner(System.in);
    private static Integer nProcesses;

    static {
        try {
            inputReader = new Scanner(new File("./input.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        inputProcesses();

        while (true) {
            for (int i = 0; i < 10; i++) System.out.println();
            System.out.println("1- Input Processes.\n" +
                    "2- Shortest Job First.\n" +
                    "3- Shortest Remaining Time First.\n" +
                    "4- Priority Scheduling.\n" +
                    "5- AG Factor Scheduling.\n" +
                    "6- Exit.\n");

            Integer choice = inputReader.nextInt();

            Scheduler s;
            switch (choice) {
                case 1: {
                    inputProcesses();
                    break;
                }
                case 2: {
                    s = new ShortestJobFirst(nProcesses, contextSwitchingDuration, new LinkedList<>(processes));
                    s.simulate();
                    break;
                }
                case 3: {
                    s = new SRTF(new LinkedList<>(processes), contextSwitchingDuration);
                    s.simulate();
                    break;
                }
                case 4: {
                    s = new PriorityScheduler(nProcesses, contextSwitchingDuration, new LinkedList<>(processes));
                    s.simulate();
                    break;
                }
                case 5: {
                    s = new AGScheduler(nProcesses, contextSwitchingDuration, new LinkedList<>(processes));
                    s.simulate();
                    break;
                }
                case 6: {
                    System.out.println("Exiting...");
                    return;
                }
                default: {
                    System.out.println("Please enter a valid option...\n");
                }
            }
        }
    }

    private static void inputProcesses() {
        System.out.print("Input number of processes: ");
        nProcesses = Integer.parseInt(inputReader.nextLine());

        System.out.print("Input duration of context switching: ");
        contextSwitchingDuration = Integer.parseInt(inputReader.nextLine());

        processes.clear();
        for (int i = 1; i <= nProcesses; i++) {
            Process p = new Process();

            System.out.print("Input the ID of process #" + i + ": ");
            p.PID = inputReader.nextLine();

            System.out.print("Input the arrival time of process #" + i + ": ");
            p.arrivalTime = Integer.parseInt(inputReader.nextLine());
            assert (p.arrivalTime >= 0);

            System.out.print("Input the burst time of process #" + i + ": ");
            p.burstTime = Integer.parseInt(inputReader.nextLine());
            assert (p.burstTime != 0);

            System.out.print("Input the priority number of process #" + i + ": ");
            p.priorityNumber = Integer.parseInt(inputReader.nextLine());

            processes.add(p);
            System.out.println(Scheduler.spacer);
        }
    }
}
