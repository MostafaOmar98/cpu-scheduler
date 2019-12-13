package Schedulers;

import Comparators.AGComparator;
import MainPack.Printer;
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
                assert currentProcess != null;
                currentProcess.waitingTime += time - currentProcess.get_timeSinceReady();
                System.out.println("Time: " + time + "\tStarting: " + currentProcess);
                readyQueue.remove(currentProcess);
                runTime = 0;
            }

            if (runTime == 0) {
                time += min(currentProcess.burstTime, (currentProcess.get_quantum() + 1) / 2);
                runTime += min(currentProcess.burstTime, (currentProcess.get_quantum() + 1) / 2);
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
                    pushBackToReady(currentProcess, runTime, time);
                    currentProcess = agProcess;
                    System.out.println(currentProcess.PID + " " + currentProcess.get_timeSinceReady() + " " + time);
                    currentProcess.waitingTime += time - currentProcess.get_timeSinceReady();
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
                    currentProcess.set_timeSinceReady(time);
                    readyQueue.add(currentProcess);
                    System.out.println("Added Process: " + currentProcess + " to ready queue");
                } else {
                    currentProcess.set_quantum(0);
                    currentProcess.turnaroundTime = time - currentProcess.turnaroundTime;
                    dieList.add(currentProcess);
                    System.out.println("Added Process: " + currentProcess + " to die list");
                }
                System.out.println(miniSpacer);
                currentProcess = null;
            }

            time--;
        }

        Printer printer = new Printer(System.out);
        Double averageWait = 0.0, averageTAT = 0.0;
        for (Process process : dieList) {
            printer.print(process);
            averageWait += process.waitingTime;
            averageTAT += process.turnaroundTime;
        }

        averageWait /= dieList.size();
        averageTAT /= dieList.size();
        printer.print(Printer.PrintType.AVERAGE_WAIT, averageWait);
        printer.print(Printer.PrintType.AVERAGE_TURNAROUND, averageTAT);
    }

    private void pushBackToReady(AGProcess currentProcess, Integer runTime, Integer time) {
        currentProcess.burstTime -= runTime;
        if (currentProcess.burstTime <= 0) {
            currentProcess.set_quantum(0);
            currentProcess.turnaroundTime = time - currentProcess.turnaroundTime + 1;
            dieList.add(currentProcess);
            System.out.println("Added Process: " + currentProcess + " to die list");
        } else {
            currentProcess.set_quantum(currentProcess.get_quantum() + currentProcess.get_quantum() - runTime);
            currentProcess.set_timeSinceReady(time);
            readyQueue.add(currentProcess);
            System.out.println("Added Process: " + currentProcess + " to ready queue");
        }
        System.out.println(miniSpacer);
    }

    private void __handleArrivedProcesses(int time) {
        List<Process> toBeRemoved = new LinkedList<>();
        for (Process process : processes) {
            if (process.arrivalTime <= time) {
                AGProcess toBeAdded = new AGProcess(process, ((AGProcess) process).get_quantum());
                toBeAdded.set_timeSinceReady(process.arrivalTime);
                toBeAdded.waitingTime = 0;
                toBeAdded.turnaroundTime = process.arrivalTime;
                readyQueue.add(toBeAdded);
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
