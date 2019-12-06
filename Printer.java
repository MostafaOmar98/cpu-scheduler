import java.io.*;

public class Printer {

    public enum PrintType{
        CONTEXT_START, CONTEXT_END, PROCESS_START, PROCESS_END, PROCESS_ARRIVE, AVERAGE_WAIT, AVERAGE_TURNAROUND
    }


    private PrintWriter writer;
    final static protected String spacer = "================================================";
    final static protected String miniSpacer = "===============";
    Printer(String path)
    {
        try {
            writer = new PrintWriter(new FileWriter(path), true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.println(spacer);
    }

    Printer (OutputStream outputStream)
    {
        writer = new PrintWriter(outputStream);
    }

    public void print(PrintType type, Integer time)
    {
        switch(type)
        {
            case CONTEXT_START:
                writer.println("Start Context Switch at time: " + time);
                break;
            case CONTEXT_END:
                writer.println("Finish Context Switch at time: " + time);
                break;
            default:
                System.out.println("Invalid Case print context");
                assert(false);
        }
    }

    public void print(Process p)
    {
        writer.println(miniSpacer);
        writer.println(p.PID+":");
        writer.println("  -Waiting Time: " + p.waitingTime);
        writer.println("  -Turnaround Time: " + p.turnaroundTime);
    }

    public void print(PrintType type, Double average)
    {
        writer.println(miniSpacer);
        switch(type) {
            case AVERAGE_WAIT:
                writer.println("Average waiting time: " + average);
                break;
            case AVERAGE_TURNAROUND:
                writer.println("Average turnaround time: " + average);
                break;
            default:
                System.out.println("Average error");
                assert(false);
        }
    }
    public void print(PrintType type, Integer time, Process p)
    {
        switch(type)
        {
            case PROCESS_START:
                writer.println("Start Process: " + p.PID +" at time: " + time);
                break;
            case PROCESS_END:
                writer.println("Finish Process: " + p.PID +" at time: " + time);
                break;
            case PROCESS_ARRIVE:
                writer.println("Arrival Process: " + p.PID +" at time: " + time);
                break;

            default:
                System.out.println("Invalid case print process");
                assert(false);
        }

    }


}
