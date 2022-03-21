package com.example.demo.job.hello;

import org.jeasy.batch.core.job.Job;
import org.jeasy.batch.core.job.JobBuilder;
import org.jeasy.batch.core.job.JobReport;
import org.jeasy.batch.core.job.JobStatus;
import org.jeasy.batch.core.reader.IterableRecordReader;
import org.jeasy.batch.core.writer.StandardOutputRecordWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HelloJob {

	private final HelloService helloService;
	
	@Autowired
	public HelloJob(final HelloService helloService) {
		this.helloService = helloService;
	}
	
	public void process() {
		if (log.isDebugEnabled()) {
			log.debug("process, start");
		}
		try {
			
			final Job job = new JobBuilder<String, String>()
					.batchSize(1)
					.reader(new IterableRecordReader<String>(helloService.list()))
					.writer(new StandardOutputRecordWriter<String>())
					.build();
			
			final JobReport jobReport = job.call();
			if (JobStatus.FAILED.equals(jobReport.getStatus())) {
				if (log.isErrorEnabled()) {
					log.error("process, {}", jobReport.getLastError().toString());
				}
			}
			if (log.isInfoEnabled()) {
				log.info("process, end, name: {}, status: {}",
						jobReport.getJobName(),
						jobReport.getStatus());
			}
		} catch (final Exception e) {
			e.printStackTrace();
			if (log.isErrorEnabled()) {
				log.error("process, {}", e.toString());
			}
			if (log.isInfoEnabled()) {
				log.info("process, end");
			}
		}
	}

}
