package com.jiandong.legendarybatch.jobs;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

import com.jiandong.legendarybatch.config.BatchConfig;
import com.jiandong.legendarybatch.container.PostgresContainerTest;
import com.jiandong.legendarybatch.support.DataSourceConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.batch.autoconfigure.JobExecutionEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.simple.JdbcClient;

@SpringBootTest(classes = {CsvFileImportJobConfig.class, BatchConfig.class})
@Import(DataSourceConfig.class)
class CsvFileImportJobConfigTest implements PostgresContainerTest {

	@Autowired ConfigurableApplicationContext applicationContext;

	@Autowired Job csvFileImportJob;

	@Autowired JobOperator jobOperator;

	@Autowired JdbcClient jdbcClient;

	@Test
	void happyScenario() throws Exception {
		// listen
		CountDownLatch latch = new CountDownLatch(1);

		applicationContext.addApplicationListener((ApplicationListener<JobExecutionEvent>) event -> {
			if (event.getJobExecution().getJobInstance().getJobName().equals("csvFileImportJob")) {
				latch.countDown();
			}
		});

		// Given
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("csvFilePath", "src/test/resources/batch/batch-data.csv")
				.toJobParameters();
		JobExecution jobExecution = jobOperator.start(csvFileImportJob, jobParameters);

		// Wait
		latch.await();

		// Then
		Assertions.assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

		var personList = jdbcClient.sql("select * from employee")
				.query(CsvFileImportJobConfig.Employee.class)
				.list();
		Assertions.assertThat(personList)
				.hasSize(5)
				.last()
				.extracting(employee -> Objects.requireNonNull(employee).firstName())
				.isEqualTo("E");
	}

}
