package Processes;

public class Process {
    public String PID;
    public Integer arrivalTime;
    public Integer burstTime;
    public Integer priorityNumber;
    public Integer waitingTime;
    public Integer turnaroundTime;
    public Integer originalPriority;
    public Integer age = 0;

    public Process() {
    }

    public Process(Process process) {
        this.PID = process.PID;
        this.arrivalTime = process.arrivalTime;
        this.burstTime = process.burstTime;
        this.priorityNumber = process.priorityNumber;
        this.waitingTime = process.waitingTime;
        this.turnaroundTime = process.turnaroundTime;
        this.originalPriority = process.originalPriority;
        this.age = process.age;
    }

    public String toString() {
        return "[" + "PID: " + PID + ", " + "arrivalTime: " + arrivalTime + ", " + "burstTime: " + burstTime + ", " +
                "priorityNumber: " + priorityNumber + "]";
    }
}