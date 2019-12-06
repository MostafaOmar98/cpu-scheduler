public class Process {
    String PID;
    Integer arrivalTime;
    Integer burstTime;
    Integer priorityNumber;
    Integer waitingTime;
    Integer turnaroundTime;
    Integer originalPriority;
    Integer age = 0 ;
    public String toString(){
        return "[" + "PID: " + PID + ", " + "arrivalTime: " + arrivalTime + ", " + "burstTime: " + burstTime + ", " +
                "priorityNumber: " + priorityNumber + "]";
    }
}