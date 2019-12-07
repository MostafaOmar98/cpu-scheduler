package Comparators;

import Processes.Process;

import java.util.Comparator;

public class priorityComparator implements Comparator<Process> {
    @Override
    public int compare(Process process, Process t1) {
        if(process.arrivalTime.equals(t1.arrivalTime)) {

            if(process.priorityNumber.equals(t1.priorityNumber))
                return Integer.compare(process.burstTime , t1.burstTime);
            return Integer.compare(process.priorityNumber, t1.priorityNumber);


        }
        return Integer.compare(process.arrivalTime , t1.arrivalTime);
    }
}
