package org.rail.spring2024.config;

import org.rail.spring2024.batch.ProductPriceProcessor;
import org.rail.spring2024.batch.ProductWriter;
import org.rail.spring2024.mapper.ProductRowMapper;
import org.rail.spring2024.model.Product;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.PostgresPagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class BatchConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public Job productReaderJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) throws Exception {
        return new JobBuilder("productReaderJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(chunkStep(jobRepository, platformTransactionManager))
                .build();
    }

    @Bean
    public Step chunkStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) throws Exception {
        return new StepBuilder("productProcessStep", jobRepository).<Product, Product>
                chunk(1000, platformTransactionManager)
                .reader(reader())
                .processor(processor(null))
                .writer(compositeItemWriter())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public JdbcPagingItemReader<Product> reader() {
        final JdbcPagingItemReader<Product> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setPageSize(1000);
        reader.setRowMapper(new ProductRowMapper());
        reader.setQueryProvider(createQuery());
        return reader;
    }
    @Bean
    public ItemProcessor<Product, Product> processor(@Value("#{new java.math.BigDecimal(\"${app.scheduling.priceIncreasePercentage}\")}") BigDecimal number) {
        return new ProductPriceProcessor(number);
    }

    @Bean
    public ItemWriter<Product> writer() {
        return new ProductWriter();
    }

    @Bean
    public FlatFileItemWriter<Product> fileWriter() {
        return new FlatFileItemWriterBuilder<Product>()
                .name("file reader")
                .resource(new FileSystemResource("src/main/resources/file.txt"))
                .lineAggregator(new DelimitedLineAggregator<>() {
                    {
                        setDelimiter(",");
                        setFieldExtractor(new BeanWrapperFieldExtractor<>() {
                            {
                                String[] names = {"uuid", "name", "description", "type",
                                        "price", "quantity", "dateQuantityUpdated", "dateCreated"};
                                setNames(names);
                            }
                        });
                    }
                })
                .build();
    }

    @Bean
    public CompositeItemWriter<Product> compositeItemWriter() {
        CompositeItemWriter<Product> writer = new CompositeItemWriter<>();
        writer.setDelegates(List.of(fileWriter(), writer()));
        return writer;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(4);
        taskExecutor.setCorePoolSize(4);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }

    private PostgresPagingQueryProvider createQuery() {
        final PostgresPagingQueryProvider queryProvider = new PostgresPagingQueryProvider();
        queryProvider.setSelectClause("select *");
        queryProvider.setFromClause("from product");
        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("uuid", Order.ASCENDING);
        queryProvider.setSortKeys(sortKeys);
        return queryProvider;
    }

    //Removes WARN - bean isn't eligible to be processed by BeanPostProcessor
    @Bean
    public static BeanDefinitionRegistryPostProcessor jobRegistryBeanPostProcessorRemover() {
        return registry -> registry.removeBeanDefinition("jobRegistryBeanPostProcessor");
    }
}
