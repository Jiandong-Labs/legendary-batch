package com.jiandong.legendarybatch;

import com.jiandong.legendarybatch.container.PostgresContainerTest;
import com.jiandong.legendarybatch.support.DataSourceConfig;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(DataSourceConfig.class)
class LegendaryBatchApplicationTests implements PostgresContainerTest {

	@Test
	void contextLoads() {
	}

}
