import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class ShortestJobFirst extends Scheduler {

    ShortestJobFirst(Integer nProcesses, Integer contextSwitchingDuration, List<Process> processes)
    {
        super(nProcesses, contextSwitchingDuration, processes);
    }

    public void simulate()
    {/*
     * Shortest Time First
     */
        Process currentRunningProcess = null;
        Integer currentProcessStartTime = 0;
        Integer contextSwitchingCounter = 0;
        Boolean ContextSwitching = false;

        PriorityQueue<Process> arrivalMinHeap = new PriorityQueue<Process>(nProcesses, new ProcessArrivalComparator());
        for(Process p : processes) arrivalMinHeap.add(p);

        PriorityQueue<Process> burstMinHeap = new PriorityQueue<Process>(1, new ProcessBurstComparator());
        List<Process> order = new ArrayList<>();

        for(int time = 0; time <= 2000000000; time++){

            if(ContextSwitching){
                if(contextSwitchingCounter >= contextSwitchingDuration){
                    ContextSwitching = false;
                    contextSwitchingCounter = 0;
                }
                else{
                    contextSwitchingCounter++;
                    continue;
                }
            }

            while(!arrivalMinHeap.isEmpty() && arrivalMinHeap.peek().arrivalTime <= time)
                burstMinHeap.add(arrivalMinHeap.poll());

            if(currentRunningProcess == null && !burstMinHeap.isEmpty()){ // If there are no processes running and there are processes waiting
                currentRunningProcess = burstMinHeap.poll(); // Allow process with minimum burst time to enter the CPU
                currentProcessStartTime = time;
            }

            if(currentRunningProcess != null){
                if(currentRunningProcess.burstTime.equals(time - currentProcessStartTime)){ // Current process has finished executing
                    currentRunningProcess.waitingTime = time - currentRunningProcess.arrivalTime - currentRunningProcess.burstTime;
                    currentRunningProcess.turnaroundTime = time - currentRunningProcess.arrivalTime;
                    order.add(currentRunningProcess);

                    currentRunningProcess = null;

                    ContextSwitching = true;
                    contextSwitchingCounter = 1;
                    if(contextSwitchingDuration.equals(0)) time--;
                }
            }
        }

        System.out.println(spacer);
        System.out.println("Shortest Time First Simulation: ");
        Double averageWaitingTime = 0.0;
        Double averageTurnAroundTime = 0.0;
        for(Process p : order){
            System.out.println(p.PID+":");
            System.out.println("  -Waiting Time: " + p.waitingTime);
            System.out.println("  -Turnaround Time: " + p.turnaroundTime);
            System.out.println(miniSpacer);

            averageWaitingTime += p.waitingTime*1.0/nProcesses;
            averageTurnAroundTime += p.turnaroundTime*1.0/nProcesses;
        }
        System.out.println("Average waiting time: " + averageWaitingTime);
        System.out.println("Average turnaround time: " + averageTurnAroundTime);
        System.out.println(spacer);
    }
}
