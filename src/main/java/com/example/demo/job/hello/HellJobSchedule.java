package com.example.demo.job.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HellJobSchedule {

	private final HelloJob helloJob;
	
	@Autowired
	public HellJobSchedule(final HelloJob helloJob) {
		this.helloJob = helloJob;
	}
	
//	@Scheduled(cron="0 0 1 * * *")
	@Scheduled(fixedDelay = 3600000)
	public void schedule() {
		final String scheduleName = getClass().getSimpleName();
		if (log.isDebugEnabled()) {
			log.debug(String.format("%, start", scheduleName));
		}
		helloJob.process();
		if (log.isDebugEnabled()) {
			log.debug(String.format("%, end", scheduleName));
		}
	}
}
