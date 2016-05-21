package quartz.lessons.lesson3;

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
		
		String jobSays = dataMap.getString("jobSays");
		float myFloatValue = dataMap.getFloat("myFloatValue");
				
		logger.info("###### 더미 잡 [" + key.getGroup() + ">" + key.getName() + "] 이무니다~ ######");
		logger.info("JobSays : " + jobSays);
		logger.info("Val : " + myFloatValue);
		
		if (context.getTrigger().getJobDataMap() != null) { // 요걸로 분기하면 안되겠다. trigger 에는 항상 JobDataMap 이 있네..
			dataMap = context.getMergedJobDataMap();
			logger.info("\t>>>> 트리거 JobDataMap 있쌈.. Merged 로 가져올꺼임");
			logger.info("\tjobSays : " + dataMap.getString("jobSays"));
			logger.info("\tVal : " + dataMap.getFloat("myFloatValue"));
		}
	}

}
