package event;

import event.basic.EventRouter;
import utility.MyIn;

import java.io.FileNotFoundException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by zzt on 1/25/16.
 * <p>
 * Usage:
 */
public class EventTester {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();

        DoInput doInput = new DoInput(new MyIn("test"));
        new DoShift();
        new DoSort();
        new DoOutput();

        executor.execute(doInput);
        // without it, this program will not finish
        executor.awaitTermination(60, TimeUnit.SECONDS);
    }
}
