package com.jiandong.legendarybatch.runner;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.stereotype.Service;

@Service
public class CsvFileImportJobRunner extends JobRunner {

	public CsvFileImportJobRunner(JobOperator jobOperator, Job csvFileImportJob) {
		super(jobOperator, csvFileImportJob);
	}

	@Override
	public void run() {
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("csvFilePath", "src/test/resources/batch/batch-data.csv")
				.toJobParameters();
		super.run(jobParameters);
	}

}
