package com.jiandong.legendarybatch.runner;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.InvalidJobParametersException;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.JobRestartException;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.IdGenerator;
import org.springframework.util.ObjectUtils;

public class JobRunner {

	private static final IdGenerator idGenerator = new AlternativeJdkIdGenerator();

	private final JobOperator jobOperator;

	private final Job job;

	protected JobRunner(JobOperator jobOperator, Job job) {
		this.jobOperator = jobOperator;
		this.job = job;
	}

	public void run() {
		this.run(new JobParameters());
	}

	public void run(JobParameters jobParameters) {
		try {
			this.jobOperator.start(job, withJobParamId(jobParameters));
		}
		catch (JobInstanceAlreadyCompleteException | JobExecutionAlreadyRunningException |
			   InvalidJobParametersException | JobRestartException e) {
			throw new RuntimeException(e);
		}
	}

	private JobParameters withJobParamId(JobParameters jobParameters) {
		String jobParamId = jobParameters.getString("job-param-id");
		if (ObjectUtils.isEmpty(jobParamId)) {
			jobParameters = new JobParametersBuilder()
					.addString("job-param-id", idGenerator.generateId().toString())
					.addJobParameters(jobParameters)
					.toJobParameters();
		}
		return jobParameters;
	}

}