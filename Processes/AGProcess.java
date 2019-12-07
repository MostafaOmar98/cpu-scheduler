package Processes;

public class AGProcess extends Process {
    private Integer _AGFactor;
    private Integer _quantum;
    private Integer _timeSinceReady;

    public AGProcess(Process process, Integer _quantum) {
        super(process);
        this._quantum = _quantum;
        calcAGFactor();
    }

    public String toString() {
        return "[" + "PID: " + PID + ", " + "arrivalTime: " + arrivalTime + ", " + "burstTime: " + burstTime + ", " +
                "priorityNumber: " + priorityNumber + ", Quantum: " + _quantum + ", AG Factor:" + _AGFactor + "]";
    }

    private Integer calcAGFactor() {
        return _AGFactor = this.burstTime + this.arrivalTime + this.priorityNumber;
    }

    public Integer get_AGFactor() {
        return _AGFactor;
    }

    public Integer get_quantum() {
        return _quantum;
    }

    public void set_quantum(Integer _quantum) {
        this._quantum = _quantum;
    }

    public Integer get_timeSinceReady() {
        return _timeSinceReady;
    }

    public void set_timeSinceReady(Integer _timeSinceReady) {
        this._timeSinceReady = _timeSinceReady;
    }
}
