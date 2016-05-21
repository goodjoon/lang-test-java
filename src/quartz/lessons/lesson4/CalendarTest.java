package quartz.lessons.lesson4;

import java.util.Calendar;

import org.quartz.CalendarIntervalScheduleBuilder;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.HolidayCalendar;

/**
 * [Lesson3]
 * JobDetail 과 JobDataMap 사용하는 예제임.
 * @author sjoon
 *
 */
public class CalendarTest {
	public static void main(String[] args) {
		
		try {
			
			HolidayCalendar cal = new HolidayCalendar();
			Calendar excludeDateFrom = Calendar.getInstance();
			excludeDateFrom.set(2013, 7, 12);
			Calendar excludeDateTo = Calendar.getInstance();
			excludeDateTo.set(2013, 7, 14);
						
			cal.addExcludedDate(excludeDateFrom.getTime());  // from ~ to 는 안되네..
			cal.addExcludedDate(excludeDateTo.getTime());
			
			
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.addCalendar("myHolidays", cal, false, true);
			
			
			
			JobDetail job = JobBuilder.newJob(DumbJob.class)
					.withIdentity("job1", "group1")
					.usingJobData("jobSays", "Hello World")
					.usingJobData("myFloatValue", 3.14f)
					.build();
			
			Trigger trigger = TriggerBuilder.newTrigger()
					.withIdentity("trigger1", "group1")
//					.withSchedule(
//							CronScheduleBuilder.cronSchedule("*/1 * * ? * * *")
//							)
					.withSchedule(
							SimpleScheduleBuilder.simpleSchedule()
							.withIntervalInSeconds(1)
							.repeatForever()
							)
					.modifiedByCalendar("myHolidays")  //<---- 요걸로 달력 지정해주는거다..
					.build();
			
			scheduler.scheduleJob(job, trigger);
			
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
