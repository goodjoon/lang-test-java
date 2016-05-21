package quartz.lessons.lesson1;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTest {
	public static void main(String[] args) {
		
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			
			JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("job1", "group1").build();
			
			Trigger trigger = TriggerBuilder.newTrigger()
					.withIdentity("trigger1", "group1")
					.startNow()
					.withSchedule(
							SimpleScheduleBuilder.simpleSchedule()
							.withIntervalInSeconds(2)
							.repeatForever())
					.build();
			
			scheduler.scheduleJob(job, trigger);			
			
			Thread.sleep(10000);
			
			scheduler.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
