package quartz.lessons.lesson5;

import java.util.Date;

import org.quartz.DateBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
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
public class MisfireInstructionTest {
	public static void main(String[] args) {
		
		try {
			
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			System.out.println("A 쓰레드 풀 개수 : " + scheduler.getMetaData().getThreadPoolSize());
			Date startDate = DateBuilder.futureDate(3, IntervalUnit.SECOND); // 현재 부터 3초 뒤
			
			JobDetail job = JobBuilder.newJob(LongJob.class)
					.withIdentity("job1", "group1")
					.usingJobData("sleepSeconds", 10)
					.build();
			
			JobDetail job2 = JobBuilder.newJob(DumbJob.class)
					.withIdentity("job2", "group1")
					.usingJobData("jobSays", "멍멍미미미")
					.usingJobData("myFloatValue", 3.14f)
					.storeDurably()
					.build();
			
			SimpleTrigger trigger = (SimpleTrigger)TriggerBuilder.newTrigger()
					.withIdentity("trigger1", "group1")
					.startAt(startDate)
					.usingJobData("alias", "ByTrigger 1")
					.forJob("job1", "group1")
					.build();
			
			SimpleTrigger trigger2 = (SimpleTrigger)TriggerBuilder.newTrigger()
					.withIdentity("trigger2", "group1")
					.startAt(startDate)
					.forJob("job1", "group1")
					.usingJobData("alias", "ByTrigger 2")
					.withSchedule(
							SimpleScheduleBuilder.simpleSchedule()
							.withMisfireHandlingInstructionIgnoreMisfires() // <--- 요놈으로~~
							)
					.build();
			
			SimpleTrigger trigger3 = (SimpleTrigger)TriggerBuilder.newTrigger()
					.withIdentity("trigger3", "group1")
					.startAt(startDate)
					.forJob("job2", "group1")
//					.usingJobData("alias", "ByTrigger 3")
					.withSchedule(
							SimpleScheduleBuilder.simpleSchedule()
							.withMisfireHandlingInstructionIgnoreMisfires() // <--- 요놈으로~~
							)
					.build();
			
			scheduler.scheduleJob(job, (Trigger)trigger);		
			scheduler.addJob(job2, false);
			scheduler.scheduleJob(trigger2);
			scheduler.scheduleJob(trigger3);
			
			scheduler.start();
			
			System.out.println("B 쓰레드 풀 개수 : " + scheduler.getMetaData().getThreadPoolSize());
			System.out.println("스케쥴러 이름 : " + scheduler.getMetaData().getSchedulerName());
			
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
