package quartz.lessons.lesson6;

import java.util.Timer;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LongJob implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		
		try {
			Logger logger = LoggerFactory.getLogger(LongJob.class);
			int sleepSeconds = context.getMergedJobDataMap().getInt("sleepSeconds");
			
			for (int i = 0 ; i < sleepSeconds ; i++) {
				logger.info(context.getMergedJobDataMap().getString("alias") + " / Looping ... " + i);
				Thread.currentThread().sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
