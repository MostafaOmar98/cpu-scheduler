package Schedulers;

import Comparators.AGComparator;
import Processes.AGProcess;
import Processes.Process;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import static MainPack.Main.inputReader;
import static java.lang.Math.ceil;
import static java.lang.Math.min;

public class AGScheduler extends Scheduler {
    private AGProcess currentProcess = null;
    private Queue<AGProcess> readyQueue = new LinkedList<>();
    private List<AGProcess> dieList = new LinkedList<>();

    public AGScheduler(Integer nProcesses, Integer contextSwitchingDuration, List<Process> processes) {
        super(nProcesses, contextSwitchingDuration, processes);
    }

    @Override
    public void simulate() {
        System.out.println(spacer);
        convertToAGProcesses();
        Integer runTime = 0;

        for (int time = 0; time <= 200000; time++) {
            __handleArrivedProcesses(time);


            if (currentProcess == null) {
                if (readyQueue.isEmpty()) continue;
                currentProcess = readyQueue.poll();
                System.out.println("Time: " + time + "\tStarting: " + currentProcess);
                readyQueue.remove(currentProcess);
                runTime = 0;
            }

            if (runTime == 0) {
                time += (currentProcess.get_quantum() + 1) / 2;
                runTime += (currentProcess.get_quantum() + 1) / 2;
            }

            Boolean switched = false;
            for (; time <= 200000; time++, runTime++) {
                if (runTime == min(currentProcess.get_quantum(), currentProcess.burstTime)) break;

                __handleArrivedProcesses(time);

                PriorityQueue<AGProcess> agProcessPriorityQueue = new PriorityQueue<>(new AGComparator());
                agProcessPriorityQueue.addAll(readyQueue);

                if (agProcessPriorityQueue.isEmpty()) continue;

                AGProcess agProcess = agProcessPriorityQueue.peek();
                if (agProcess.get_AGFactor() < currentProcess.get_AGFactor()) {
                    System.out.println("Time: " + time + "\tDone: " + currentProcess);
                    pushBackToReady(currentProcess, runTime);
                    currentProcess = agProcess;
                    System.out.println("Time: " + time + "\tStarting: " + currentProcess);
                    readyQueue.remove(currentProcess);
                    runTime = 0;
                    switched = true;
                    break;
                }
            }

            if (!switched) {
                currentProcess.set_quantum((int) ceil(currentProcess.get_quantum() * 0.1 + currentProcess.get_quantum()));
                currentProcess.burstTime -= runTime;
                System.out.println("Time: " + time + "\tDone: " + currentProcess);
                if (currentProcess.burstTime > 0) {
                    readyQueue.add(currentProcess);
                    System.out.println("Added Process to ready queue");
                } else {
                    currentProcess.set_quantum(0);
                    dieList.add(currentProcess);
                    System.out.println("Added Process to die list");
                }
                System.out.println(miniSpacer);
                currentProcess = null;
            }

            time--;
        }
    }

    private void pushBackToReady(AGProcess currentProcess, Integer runTime) {
        currentProcess.burstTime -= runTime;
        if (currentProcess.burstTime <= 0) {
            currentProcess.set_quantum(0);
            dieList.add(currentProcess);
            System.out.println("Added Process to die list");
        } else {
            currentProcess.set_quantum(currentProcess.get_quantum() + currentProcess.get_quantum() - runTime);
            readyQueue.add(currentProcess);
            System.out.println("Added Process to ready queue");
        }
        System.out.println(miniSpacer);
    }

    private void __handleArrivedProcesses(int time) {
        List<Process> toBeRemoved = new LinkedList<>();
        for (Process process : processes) {
            if (process.arrivalTime <= time) {
                readyQueue.add(new AGProcess(process, ((AGProcess) process).get_quantum()));
                toBeRemoved.add(process);
            }
        }
        processes.removeAll(toBeRemoved);
    }

    private void convertToAGProcesses() {
        List<Process> temp = new LinkedList<>();
        for (Process process : processes) {
            System.out.print("Enter Quantum for Process " + process.PID + ": ");
            Integer quantum = inputReader.nextInt();
            temp.add(new AGProcess(process, quantum));
        }
        processes = new LinkedList<>(temp);
    }
}
