package quartz.lessons.lesson3;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import quartz.lessons.lesson3.DumbJob3;

public class QuartzTest3 {
	public static void main(String[] args) {
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			
			JobDetail job3 = JobBuilder.newJob(DumbJob3.class)
					.withIdentity("job3", "group1")
					.storeDurably(true)
					.build();
						
			Trigger trigger = TriggerBuilder.newTrigger()
					.forJob(job3)
					.withIdentity("trigger1", "group1")
					.usingJobData("jobSays", "에라이...")
					.usingJobData("myFloatValue", 9.99f)
					.withSchedule(
							CronScheduleBuilder.cronSchedule("*/2 * * ? * * *")
							)
					.withPriority(100)
					.build();
			
			Trigger trigger2 = TriggerBuilder.newTrigger()
					.forJob("job3", "group1")
					.withIdentity("trigger2", "group1")
					.usingJobData("jobSays", "엉헝??")
					.usingJobData("myFloatValue", 1.11f)
					.withSchedule(
							CronScheduleBuilder.cronSchedule("*/2 * * ? * * *")
							)
					.withPriority(1)
					.build();
			
			scheduler.addJob(job3, false);
			scheduler.scheduleJob(trigger);
			scheduler.scheduleJob(trigger2);
			
			
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
