package quartz.lessons.lesson5;

import java.util.Calendar;
import java.util.Date;

import org.quartz.DateBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * [Lesson3]
 * JobDetail 과 JobDataMap 사용하는 예제임.
 * @author sjoon
 *
 */
public class ShutdownTest {
	public static void main(String[] args) {
		
		try {
			
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			
			JobDetail job = JobBuilder.newJob(LongJob.class)
					.withIdentity("job1", "group1")
					.usingJobData("sleepSeconds", 10) // 5초 걸리는 Job 으로 만듦.
					.build();
			
			SimpleTrigger trigger = (SimpleTrigger)TriggerBuilder.newTrigger()
					.withIdentity("trigger1", "group1")
					.startAt(
							DateBuilder.futureDate(1, IntervalUnit.SECOND) // Date Builder 를 사용한 예제~~~ 1초 후에 시작함.
							)
					.forJob("job1", "group1")
					.build();
			
			scheduler.scheduleJob(job, (Trigger)trigger);
			
			scheduler.start();
			
			
			Thread.sleep(4000); // Trigger 는 1초 후 시작하고 Main Thread 는 시작한지 4초 후 죽게 되어있음.. 그럼 5초 이상 걸리는 Job 은...? 
			
			scheduler.shutdown(); 
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
