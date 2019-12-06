import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

// TODO use proper OOP structure?
// TODO build menu?
// TODO assert custom comparators are working properly
// TODO create roundRobin variable and read round robin time quantum

public class Main {
    static String spacer = "================================================";
    static String miniSpacer = "===============";

    public static void main(String[] args) throws IOException {
//        Scanner inputReader = new Scanner(new File("C:\\Users\\Ahmed\\Desktop\\inputFile.TXT"));
        Scanner inputReader = new Scanner(System.in);
        Integer nProcesses;
        Integer contextSwitchingDuration;

        System.out.print("Input number of processes: ");
        nProcesses = Integer.parseInt(inputReader.nextLine());

        System.out.print("Input duration of context switching: ");
        contextSwitchingDuration = Integer.parseInt(inputReader.nextLine());

        List<Process> processes = new ArrayList<>();
        for(int i=1; i<=nProcesses; i++){
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
            System.out.println(spacer);
        }


        Scheduler s = new ShortestJobFirst(nProcesses, contextSwitchingDuration, processes);
        s.simulate();
        s = new SRTF(processes, contextSwitchingDuration);
        s.simulate();
    }
}