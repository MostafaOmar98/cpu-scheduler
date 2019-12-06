import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class PriorityScheduler extends Scheduler {
    PriorityScheduler(Integer nProcesses, Integer contextSwitchingDuration, List<Process> processes) {
        super(nProcesses, contextSwitchingDuration, processes);
    }

    @Override
    public void simulate() {
        Process currentRunningProcess = null;
        List<Process> order = new ArrayList<>();
        PriorityQueue<Process> priorityBasedqueue = new PriorityQueue<Process>(nProcesses , new priorityComparator());
        for(Process p : processes){
            p.originalPriority = p.priorityNumber;
            priorityBasedqueue.add(p);
        }
        for(int time = 0 ; time <= 200000 ; ++time){

            for ( Process p :priorityBasedqueue) {
                if(p.arrivalTime <= time){
                    p.age = (time - p.arrivalTime)/10;
                    p.priorityNumber=Math.max(0 , p.originalPriority - p.age);
                }
            }
            for (Process p : priorityBasedqueue){
                if(p.arrivalTime <= time){
                    currentRunningProcess = priorityBasedqueue.poll(); ;

                    break;
                }
            }
            if(currentRunningProcess != null){ /// since nonpreemptive then we take all time once
//                Process Current = priorityBasedqueue.poll();
                order.add(currentRunningProcess);
                currentRunningProcess.turnaroundTime = time + currentRunningProcess.burstTime + contextSwitchingDuration ;
                currentRunningProcess.waitingTime = currentRunningProcess.turnaroundTime - currentRunningProcess.arrivalTime;
                time = time + currentRunningProcess.burstTime + contextSwitchingDuration - 1;
                currentRunningProcess = null;
            }
        }
        System.out.println(spacer);
        System.out.println("Priority scheduling  Simulation: ");
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
