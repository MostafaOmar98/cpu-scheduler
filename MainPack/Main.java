package MainPack;

import Processes.Process;
import Schedulers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// TODO use proper OOP structure?
// TODO build menu?
// TODO assert custom comparators are working properly
// TODO create roundRobin variable and read round robin time quantum

public class Main {
    //    public static Scanner inputReader = new Scanner(System.in);
    public static Scanner inputReader;

    static {
        try {
            inputReader = new Scanner(new File("./input.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Integer nProcesses;
        Integer contextSwitchingDuration;

        System.out.print("Input number of processes: ");
        nProcesses = Integer.parseInt(inputReader.nextLine());

        System.out.print("Input duration of context switching: ");
        contextSwitchingDuration = Integer.parseInt(inputReader.nextLine());

        List<Process> processes = new ArrayList<>();
        for (int i = 1; i <= nProcesses; i++) {
            Process p = new Process();

            System.out.print("Input the ID of process #" + i + ": ");
            p.PID = inputReader.nextLine();

            System.out.print("Input the arrival time of process #" + i + ": ");
            p.arrivalTime = Integer.parseInt(inputReader.nextLine());
            assert(p.arrivalTime >= 0);

            System.out.print("Input the burst time of process #" + i + ": ");
            p.burstTime = Integer.parseInt(inputReader.nextLine());
            assert(p.burstTime != 0);

            System.out.print("Input the priority number of process #" + i + ": ");
            p.priorityNumber = Integer.parseInt(inputReader.nextLine());

            processes.add(p);
            String spacer = "================================================";
            System.out.println(spacer);
        }

        Scheduler s;

        s = new AGScheduler(nProcesses, contextSwitchingDuration, processes);
        s.simulate();

        s = new PriorityScheduler(nProcesses, contextSwitchingDuration, processes);
        s.simulate();

        s = new ShortestJobFirst(nProcesses, contextSwitchingDuration, processes);
        s.simulate();

        s = new SRTF(processes, contextSwitchingDuration);
        s.simulate();
    }
}
