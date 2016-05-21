package quartz.lessons.lesson5;

import java.util.Calendar;
import java.util.Date;

import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.impl.StdSchedulerFactory;

/**
 * [Lesson3]
 * JobDetail 과 JobDataMap 사용하는 예제임.
 * @author sjoon
 *
 */
public class TriggerAndDateBuilderTest {
	public static void main(String[] args) {
		
		try {
			
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.SECOND, 3);
			
			
			JobDetail job = JobBuilder.newJob(DumbJob.class)
					.withIdentity("job1", "group1")
					.usingJobData("jobSays", "Hello World")
					.usingJobData("myFloatValue", 3.14f)
					.build();
			
			SimpleTrigger trigger = (SimpleTrigger)TriggerBuilder.newTrigger()
					.withIdentity("trigger1", "group1")
					.startAt(
							DateBuilder.futureDate(10, IntervalUnit.SECOND) // Date Builder 를 사용한 예제~~~
							)
					.forJob("job1", "group1")
					.build();
			
			scheduler.scheduleJob(job, (Trigger)trigger);
			
			scheduler.start();
			
			
			Thread.sleep(10000);
			
			scheduler.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
