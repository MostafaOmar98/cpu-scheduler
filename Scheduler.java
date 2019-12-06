import java.util.List;

public abstract class Scheduler {
    final static protected String spacer = "================================================";
    final static protected String miniSpacer = "===============";
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
