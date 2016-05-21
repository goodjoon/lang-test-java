package quartz.lessons.lesson4;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DumbJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		
		JobKey key = context.getJobDetail().getKey();
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
				
		logger.info("###### 더미 잡 [" + key.getGroup() + ">" + key.getName() + "] 이무니다~ ######");
		SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd / HH:mm:ss");
		logger.info("TIME : " + format.format(new Date()));
		dataMap = context.getMergedJobDataMap();
		logger.info("\tjobSays : " + dataMap.getString("jobSays"));
		logger.info("\tVal : " + dataMap.getFloat("myFloatValue"));
	}

}
