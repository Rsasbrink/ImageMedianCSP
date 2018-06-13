package nl.hva.pc.part2.task;

import java.io.Serializable;

public class GregorySeriesReturn implements Serializable{

    private int loopCount;
    private double pi;
    private double timeToCalculate;

    public int getLoopCount() {
        return loopCount;
    }

    public void setLoopCount(int loopCount) {
        this.loopCount = loopCount;
    }

    public double getPi() {
        return pi;
    }

    public void setPi(double pi) {
        this.pi = pi;
    }

    public double getTimeToCalculate() {
        return timeToCalculate;
    }

    public void setTimeToCalculate(double timeToCalculate) {
        this.timeToCalculate = timeToCalculate;
    }

    @Override
    public String toString() {
        return "loop count: " + getLoopCount() + " pi: " + getPi() + " time to calculate: " + getTimeToCalculate() + " ms";
    }
}
