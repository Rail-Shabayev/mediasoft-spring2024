package org.rail.spring2024.config;

import org.rail.spring2024.scheduler.OptimizedScheduler;
import org.rail.spring2024.scheduler.SimpleScheduler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!local")
public class SchedulerConfig {

    @Bean
    @ConditionalOnExpression("${app.scheduling.enabled} and " +
            "!${app.scheduling.optimization}")
    public SimpleScheduler simpleScheduler() {
        return new SimpleScheduler();
    }

    @Bean
    @ConditionalOnExpression("${app.scheduling.enabled} and " +
            "${app.scheduling.optimization}")
    public OptimizedScheduler optimizedScheduler() {
        return new OptimizedScheduler();
    }
}
