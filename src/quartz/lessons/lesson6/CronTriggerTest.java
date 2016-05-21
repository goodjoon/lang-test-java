package quartz.lessons.lesson6;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.DirectSchedulerFactory;
import org.quartz.simpl.RAMJobStore;
import org.quartz.simpl.SimpleThreadPool;
import org.quartz.spi.ThreadPool;

public class CronTriggerTest {
	private static ThreadPool threadPool;
	public CronTriggerTest() {
		if (threadPool == null)
			threadPool = new SimpleThreadPool(10, Thread.NORM_PRIORITY);
	}
	
	public ThreadPool getThreadPool() {
		return threadPool;
	}
	
	public void run() {
		/**
		 * white-space, and represent:
		 * ----------------------
		 * Seconds
		 * Minutes
 		 * Hours
		 * Day-of-Month
		 * Month
		 * Day-of-Week
		 * Year (optional field)
		 * 
		 */
		
		JobDetail job = JobBuilder.newJob()
				.ofType(LongJob.class)
				.withIdentity("job1", "group1")
				.build();
		
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("trigger1", "group1")
				.withSchedule(
						CronScheduleBuilder.cronSchedule("0,20,40 */1 9-18 * * ? *")
						)
				.forJob("job1", "group1")
				.usingJobData("sleepSeconds", 10)
				.usingJobData("alias", "쭌 잡")
				.build();
		
		try {
			DirectSchedulerFactory.getInstance().createScheduler("MXP Scheduler", "MXPScheduler_instance_1", getThreadPool(), new RAMJobStore());
					
			Scheduler scheduler = DirectSchedulerFactory.getInstance().getScheduler("MXP Scheduler");
			scheduler.scheduleJob(job, trigger);
			scheduler.start();
			
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) {
		new CronTriggerTest().run();
	}
}
