package Schedulers;

import Processes.Process;

import java.util.List;

public abstract class Scheduler {
    Integer nProcesses;
    Integer contextSwitchingDuration;
    protected List<Process> processes;
    static String spacer = "================================================";
    static String miniSpacer = "===============";

    Scheduler(Integer nProcesses, Integer contextSwitchingDuration, List<Process> processes) {
        this.nProcesses = nProcesses;
        this.contextSwitchingDuration = contextSwitchingDuration;
        this.processes = processes;
    }

    public abstract void simulate();

}
