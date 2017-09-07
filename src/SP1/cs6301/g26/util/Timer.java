package cs6301.g26.util;

/**
 * This class is used to calculate the time interval between start and stop call
 * @author Sharath
 */

public class Timer {
    private long startTime;
    private long endTime;
    private long memAvailable;
    private long memUsed;

    public void start() {
        clear();
        startTime = System.nanoTime();
    }

    public void stop() {
        endTime = System.nanoTime();
        memAvailable = Runtime.getRuntime().totalMemory();
        memUsed = memAvailable - Runtime.getRuntime().freeMemory();
    }

    public long getTime() {
        if (startTime == 0) {
            return 0;
        }
        if (endTime == 0) {
            stop();
        }
        return (endTime - startTime) / 1000000;
    }


    public void clear() {
        startTime = endTime = 0;
    }

    public long getMemAvailable() {
        return memAvailable;
    }

    public long getMemUsed() {
        return memUsed;
    }

    public String toString(){
        return "Time: " + getTime() + " msec.\n" +
                "Memory: " + (memUsed/1048576) +
                " MB / " + (memAvailable/1048576) + " MB.";
    }
}
