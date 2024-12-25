package BsK.common.util.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskScheduler {
  private static final ScheduledExecutorService EXECUTOR =
      Executors.newSingleThreadScheduledExecutor();

  public static void run1PerDay(Runnable task) {
    EXECUTOR.scheduleAtFixedRate(task, 0, 24 * 60 * 60, TimeUnit.SECONDS);
  }
}
