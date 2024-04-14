package org.rail.spring2024.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

public class OptimizedScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Scheduled(fixedDelayString = "${app.scheduling.fixedDelay}", initialDelay = 5000L)
    public void optimizeSchedule() throws Exception {
        System.out.println("Start optimized scheduler");
        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("date", new Date())
                .addLong("time",System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(job, jobParameters);
        System.out.println("Stop optimized scheduler");
    }
}
