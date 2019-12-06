import java.util.List;

public abstract class Scheduler {
    protected Integer nProcesses;
    protected Integer contextSwitchingDuration;
    protected List<Process> processes;

    Scheduler(Integer nProcesses, Integer contextSwitchingDuration, List<Process> processes)
    {
        this.nProcesses = nProcesses;
        this.contextSwitchingDuration = contextSwitchingDuration;
        this.processes = processes;
    }

    public abstract void simulate();

}
