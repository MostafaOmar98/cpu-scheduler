import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class ShortestJobFirst extends Scheduler {

    ShortestJobFirst(Integer nProcesses, Integer contextSwitchingDuration, List<Process> processes)
    {
        super(nProcesses, contextSwitchingDuration, processes);
    }

    public void simulate()
    {
        /*
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

        Printer printer = new Printer("ShortestJobFirst.txt");
        for(int time = 0; time <= 2000000000; time++){

            if(ContextSwitching){

                if(contextSwitchingCounter >= contextSwitchingDuration){
                    printer.print(Printer.PrintType.CONTEXT_END, time - 1);
                    ContextSwitching = false;
                    contextSwitchingCounter = 0;
                }
                else{
                    contextSwitchingCounter++;
                    continue;
                }
            }

            while(!arrivalMinHeap.isEmpty() && arrivalMinHeap.peek().arrivalTime <= time) {
                Process arrivingProcess = arrivalMinHeap.poll();
                burstMinHeap.add(arrivingProcess);
                printer.print(Printer.PrintType.PROCESS_ARRIVE, arrivingProcess.arrivalTime, arrivingProcess); // message for process arrival
            }

            if(currentRunningProcess == null && !burstMinHeap.isEmpty()){ // If there are no processes running and there are processes waiting
                currentRunningProcess = burstMinHeap.poll(); // Allow process with minimum burst time to enter the CPU
                currentProcessStartTime = time;
                printer.print(Printer.PrintType.PROCESS_START, time, currentRunningProcess); // message for process start
            }

            if(currentRunningProcess != null){
                if(currentRunningProcess.burstTime.equals(time - currentProcessStartTime)){ // Current process has finished executing

                    printer.print(Printer.PrintType.PROCESS_END, time - 1, currentRunningProcess); // message for process end

                    currentRunningProcess.waitingTime = time - currentRunningProcess.arrivalTime - currentRunningProcess.burstTime;
                    currentRunningProcess.turnaroundTime = time - currentRunningProcess.arrivalTime;
                    order.add(currentRunningProcess);

                    currentRunningProcess = null;

                    if(contextSwitchingDuration.equals(0))
                        time--;
                    else {
                        printer.print(Printer.PrintType.CONTEXT_START, time);
                        ContextSwitching = true;
                        contextSwitchingCounter = 1;
                    }
                }
            }
        }

        Double averageWaitingTime = 0.0;
        Double averageTurnAroundTime = 0.0;
        for(Process p : order){
            printer.print(p);
            averageWaitingTime += p.waitingTime*1.0/nProcesses;
            averageTurnAroundTime += p.turnaroundTime*1.0/nProcesses;
        }
        printer.print(Printer.PrintType.AVERAGE_WAIT, averageWaitingTime);
        printer.print(Printer.PrintType.AVERAGE_TURNAROUND, averageTurnAroundTime);
    }
}
