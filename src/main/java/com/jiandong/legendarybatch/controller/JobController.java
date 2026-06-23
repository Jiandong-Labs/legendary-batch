package com.jiandong.legendarybatch.controller;

import com.jiandong.legendarybatch.runner.JobRunner;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("job")
class JobController {

	private final ApplicationContext applicationContext;

	JobController(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@RequestMapping(value = "run/{runner}", method = RequestMethod.GET)
	void run(@PathVariable String runner) {
		JobRunner jobRunner = applicationContext.getBean(runner, JobRunner.class);
		jobRunner.run();
	}

}
