public class Process {
    String PID;
    Integer arrivalTime;
    Integer burstTime;
    Integer priorityNumber;
    Integer waitingTime;
    Integer turnaroundTime;
    public String toString(){
        return "[" + "PID: " + PID + ", " + "arrivalTime: " + arrivalTime + ", " + "burstTime: " + burstTime + ", " +
                "priorityNumber: " + priorityNumber + "]";
    }
}