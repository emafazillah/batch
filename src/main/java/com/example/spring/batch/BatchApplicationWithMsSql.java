package com.example.spring.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchApplicationWithMsSql {
	
	@Autowired
	private JobBuilderFactory job;
	
	@Autowired
	private StepBuilderFactory step;
	
	@Bean
	public ItemReader<Integer> itemReader() {
		
		
		
		return null;
	}
	
	@Bean
	public Step step() {
		return step.get("step")
				.<Integer, Integer>chunk(5)
				.reader(itemReader())
				.build();
	}
	
	@Bean
    public Job job() {
        return job.get("job")
                .start(step())
                .build();
    }
	
	public static void main(String...strings) {
		
	}

}
