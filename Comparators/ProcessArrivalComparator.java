package Comparators;

import Processes.Process;

import java.util.Comparator;

public class ProcessArrivalComparator implements Comparator<Process> {
    public int compare(Process p1, Process p2){
        return p1.arrivalTime - p2.arrivalTime;
    }
}