package com.sanjaycodes.springbatch.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.logging.Logger;

@Configuration
public class FirstJobConfiguration {

    public static final Logger LOGGER = Logger.getLogger(FirstJobConfiguration.class.getName());

    private JobRepository jobRepository;

    private PlatformTransactionManager transactionManager;

    @Autowired
    public FirstJobConfiguration(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Job firstJob() {
        return new JobBuilder("FIRST JOB", jobRepository)
                .start(firstStep())
                .build();
    }

    private Step firstStep() {
        return new StepBuilder("FIRST STEP", jobRepository)
                .tasklet(firstTasklet(), transactionManager)
                .build();
    }

    private Tasklet firstTasklet() {
        return (contribution, chunkContext) -> {
            LOGGER.warning("FIRST TASKLET EXECUTION STARTED");
            LOGGER.warning("FIRST TASKLET EXECUTION PROGRESS");
            LOGGER.warning("FIRST TASKLET EXECUTION COMPLETED");
            return RepeatStatus.FINISHED;
        };
    }
}
