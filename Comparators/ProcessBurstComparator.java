package Comparators;

import Processes.Process;

import java.util.Comparator;

public class ProcessBurstComparator implements Comparator<Process> {
    public int compare(Process p1, Process p2) {
        if(p1.burstTime.equals(p2.burstTime)) return p1.arrivalTime - p2.arrivalTime;
        else return p1.burstTime - p2.burstTime;
    }
}