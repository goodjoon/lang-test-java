package quartz.lessons.lesson3;

import java.util.ArrayList;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DumbJob3 implements Job {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public DumbJob3() {
		logger.info("### DumbJob3 Instance 생성되었쌈 ###");
	}

	String jobSays;
	float myFloatValue;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobKey key = context.getJobDetail().getKey();
		
		JobDataMap dataMap = context.getMergedJobDataMap(); // 이전 DumbJob 과 다르게 jobDetail 의 DataMap 이 아니고 Context 에서 가져옴~ merged 를~
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		logger.info("Instance " + key + " of DumbJob3 says : " + jobSays + ", and Val is : " + myFloatValue);
	}
	
	// 아래처럼 JobDataMap 의 Key 에 해당하는 Setter 를 만들어주면, Job 돌리기 전에 Scheduler 가 Set 해준다.
	public void setJobSays(String jobSays) {
		this.jobSays = jobSays;
	}
	
	public void setMyFloatValue(float myFloatValue) {
		this.myFloatValue = myFloatValue;
	}
	
}
