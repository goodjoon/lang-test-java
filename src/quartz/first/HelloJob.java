package quartz.first;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		
		logger.info("###### 잡 돌구 있네 ######");
	}

}
