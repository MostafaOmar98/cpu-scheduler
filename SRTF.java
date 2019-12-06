import javafx.util.Pair;

import java.util.*;

public class SRTF {

    PriorityQueue<Process> burstMinHeap;
    PriorityQueue<Process> arrivalMinHeap;
    Integer contextSwitchingDuration;
    List<Pair<Integer,String>> executionOrder;
    List<Process> info;
    int stop;
    Map<String,Integer> actual;                // to store initial burst time (used to calculate waiting time
    // as i am decrementing el burst gowa el loop
    SRTF(List<Process> list, int n){
        stop = list.size();
        executionOrder = new ArrayList<>();
        info = new ArrayList<>();
        contextSwitchingDuration = n;
        burstMinHeap = new PriorityQueue<>(new ProcessBurstComparator());
        arrivalMinHeap = new PriorityQueue<>(new ProcessArrivalComparator());
        arrivalMinHeap.addAll(list);
        actual = new HashMap<>();
        for(Process p : list) actual.put(p.PID,p.burstTime);
    }

    void Simulate(){
        Process q = null;
        for(int i=0;i<=20000;++i){

            while(!arrivalMinHeap.isEmpty() && arrivalMinHeap.peek().arrivalTime<=i){
                burstMinHeap.add(arrivalMinHeap.poll());
            }
            if(burstMinHeap.isEmpty()) continue;
            if(q!=null){                                                     // Not first process, current process needs to be switched
                if(q.PID!=burstMinHeap.peek().PID && q.burstTime > 0){       // if last one finished (burst = 0,do nth)
                    i+=contextSwitchingDuration;                             // else skip context units
                }
            }
            q = burstMinHeap.poll();
            q.burstTime--;
            executionOrder.add(new Pair<>(i,q.PID));
            if(q.burstTime > 0)  burstMinHeap.add(q);      // add to queue if not finished
            else{
                stop--;
                q.waitingTime = i - q.arrivalTime - actual.get(q.PID) + 1;
                q.turnaroundTime = i - q.arrivalTime + 1;
                info.add(q);
                i+=contextSwitchingDuration;             // context after process finished
                if(stop==0) break;
            }
        }
    }

    void print(){
        System.out.print("Time    Process\n");
        for(Pair<Integer,String> e : executionOrder){
            System.out.println(e.getKey()+"       P"+ e.getValue());
        }

        Double AverageWaitingTime = 0.0;
        Double AverageTurnAroundTime = 0.0;
        for(Process p : info){
            System.out.println("Process "+p.PID );
            System.out.println("  -Waiting Time : " + p.waitingTime);
            System.out.println("  -TurnAround Time : " + p.turnaroundTime);
            AverageWaitingTime+= p.waitingTime*1.0/info.size();
            AverageTurnAroundTime+= p.turnaroundTime*1.0/info.size();
        }
        System.out.println("Average Waiting Time Using SRTF scheduling : " + AverageWaitingTime);
        System.out.println("Average TurnAround Time Using SRTF scheduling : " + AverageTurnAroundTime);

    }
}