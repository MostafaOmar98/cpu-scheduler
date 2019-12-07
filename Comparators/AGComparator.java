package Comparators;

import Processes.AGProcess;

import java.util.Comparator;

public class AGComparator implements Comparator<AGProcess> {

    @Override
    public int compare(AGProcess o1, AGProcess o2) {
        return !o1.get_AGFactor().equals(o2.get_AGFactor()) ? o1.get_AGFactor() - o2.get_AGFactor() : o1.arrivalTime - o2.arrivalTime;
    }
}
