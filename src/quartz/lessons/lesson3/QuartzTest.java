package quartz.lessons.lesson3;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * [Lesson3]
 * JobDetail 과 JobDataMap 사용하는 예제임.
 * @author sjoon
 *
 */
public class QuartzTest {
	public static void main(String[] args) {
		
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			
			scheduler.start();
			
			JobDetail job = JobBuilder.newJob(DumbJob.class)
					.withIdentity("job1", "group1")
					.usingJobData("jobSays", "Hello World")
					.usingJobData("myFloatValue", 3.14f)
					.build();
			
			Trigger trigger = TriggerBuilder.newTrigger()
					.withIdentity("trigger1", "group1")
//					.startNow()
					.withSchedule(
							SimpleScheduleBuilder.simpleSchedule()
							.withIntervalInSeconds(2)
							.repeatForever())
					.build();
			
			scheduler.scheduleJob(job, trigger); // 트리거 1 할당
			
			trigger = TriggerBuilder.newTrigger()
					.withIdentity("trigger2", "group1")
					.forJob(job) // 해당 job 을 지정해줌
					.withSchedule(
						SimpleScheduleBuilder.simpleSchedule()
						.withIntervalInSeconds(3)
						.repeatForever())
					.usingJobData("jobSays", "I'm a King!")
					.build();
			
			
			scheduler.scheduleJob(trigger); 
			
			
			
			Thread.sleep(15000);
			
			scheduler.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
