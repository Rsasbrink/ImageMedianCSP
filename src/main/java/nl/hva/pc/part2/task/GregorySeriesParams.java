package nl.hva.pc.part2.task;

import java.io.Serializable;

public class GregorySeriesParams implements Serializable {

    private int loopCount;

    public GregorySeriesParams(int loopCount) {
        setLoopCount(loopCount);
    }

    public GregorySeriesParams() {}

    public int getLoopCount() {
        return loopCount;
    }

    public void setLoopCount(int loopCount) {
        this.loopCount = loopCount;
    }

}
