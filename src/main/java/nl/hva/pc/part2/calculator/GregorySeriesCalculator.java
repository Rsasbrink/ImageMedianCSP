package nl.hva.pc.part2.calculator;

import nl.hva.pc.part2.task.GregorySeriesParams;
import nl.hva.pc.part2.task.GregorySeriesReturn;

/**
 * A simple Gregory Series PI calculator
 */
public class GregorySeriesCalculator  {

    public GregorySeriesReturn execute(GregorySeriesParams param)  {
        double result = 0;

        long currentTime = System.currentTimeMillis();
        for(int i = 1; i <= param.getLoopCount(); i++) {
            double temp = (Math.pow(-1, (i + 1))) / ((2 * i) -  1);
            result += temp;
        }

        GregorySeriesReturn gsReturn = new GregorySeriesReturn();
        gsReturn.setLoopCount(param.getLoopCount());
        gsReturn.setPi(result * 4);
        gsReturn.setTimeToCalculate(System.currentTimeMillis() - currentTime);

        return gsReturn;
    }


}
