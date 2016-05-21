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
public class NoShutdownTest {
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
					.endAt(
							DateBuilder.futureDate(10, IntervalUnit.SECOND)
							)
					.forJob("job1", "group1")
					.build();
			
			scheduler.scheduleJob(job, (Trigger)trigger);
			
			scheduler.start();

		} catch (SchedulerException e) {
			e.printStackTrace();
		} 
		
	}
}
