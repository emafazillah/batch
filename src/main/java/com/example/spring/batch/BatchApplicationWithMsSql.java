package com.example.spring.batch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.SqlServerPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.example.spring.batch.model.Inventory;

@Configuration
@EnableBatchProcessing
public class BatchApplicationWithMsSql {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	@Qualifier("MyItemReader")
	private JdbcPagingItemReader<Inventory> itemReader;
	
	@Autowired
	@Qualifier("MyItemWriter")
	private ItemWriter<Inventory> itemWriter;
	
	@Bean("MyItemReader")
	public JdbcPagingItemReader<Inventory> jobReader() {
		JdbcPagingItemReader<Inventory> reader = new JdbcPagingItemReader<>();
		reader.setDataSource(dataSource());
		reader.setPageSize(1);
		 
        PagingQueryProvider queryProvider = createQueryProvider();
        reader.setQueryProvider(queryProvider);
 
        reader.setRowMapper(new BeanPropertyRowMapper<>(Inventory.class));
 
        return reader;
	}
	
	@Bean("MyItemWriter")
	public ItemWriter<Inventory> jobWriter() {
//		JdbcBatchItemWriter<Inventory> itemWriter = new JdbcBatchItemWriter<Inventory>();
//        itemWriter.setDataSource(dataSource());
//        itemWriter.setSql("UPDATE Inventory SET quantity = 151 where id = 1");
//        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Inventory>());
//        return itemWriter;
		return new ItemWriter<Inventory>() {

			@Override
			public void write(List<? extends Inventory> arg0) throws Exception {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	private PagingQueryProvider createQueryProvider() {
		SqlServerPagingQueryProvider queryProvider = new SqlServerPagingQueryProvider();
 
        queryProvider.setSelectClause("SELECT id, name, quantity");
        queryProvider.setFromClause("FROM Inventory");
        queryProvider.setSortKeys(sortByNameAsc());
 
        return queryProvider;
    }
	
	private Map<String, Order> sortByNameAsc() {
        Map<String, Order> sortConfiguration = new HashMap<>();
        sortConfiguration.put("name", Order.ASCENDING);
        return sortConfiguration;
    }
	
	@Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl("jdbc:sqlserver://localhost;databaseName=TestDB");
        dataSource.setUsername("sa");
        dataSource.setPassword("Ema!1838");
        return dataSource;
    }
	
	@Bean(name = "step")
	public Step step() {
		return stepBuilderFactory.get("step")
				.<Inventory, Inventory>chunk(5)
				.reader(itemReader)
				.writer(itemWriter)
				.build();
	}
	
	@Bean(name = "job")
    public Job job() {
        return jobBuilderFactory.get("job")
                .start(step())
                .build();
    }
	
	@SuppressWarnings("resource")
	public static void main(String...strings) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		ApplicationContext context = new AnnotationConfigApplicationContext(BatchApplicationWithMsSql.class);
		
		JobLauncher jobLauncher = context.getBean(JobLauncher.class);
        Job job = context.getBean(Job.class);
        JobParameters jobParameter = new JobParametersBuilder().addString("jobid", "TestJob").toJobParameters();
        jobLauncher.run(job, jobParameter);
	}

}
