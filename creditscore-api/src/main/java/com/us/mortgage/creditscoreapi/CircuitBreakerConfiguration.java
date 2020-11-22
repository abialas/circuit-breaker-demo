package com.us.mortgage.creditscoreapi;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Created by adam.
 */
@Configuration
class CircuitBreakerConfiguration {

	@Bean
	public Customizer<ReactiveResilience4JCircuitBreakerFactory> loanApiCircuitBreakerCustomizer() {
		return factory ->
				factory.configure(builder -> builder
						.circuitBreakerConfig(CircuitBreakerConfig
								.custom()
								.minimumNumberOfCalls(5)
								.slidingWindowSize(5)
								.permittedNumberOfCallsInHalfOpenState(5)
								.waitDurationInOpenState(Duration.ofSeconds(60))
								.build())
						.timeLimiterConfig(TimeLimiterConfig
								.custom()
								.timeoutDuration(Duration.ofSeconds(3)).build())
						.build(), "loanApi");
	}

	@Bean
	public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCircuitBreakerCustomizer() {
		return factory ->
				factory.configureDefault(id ->
						new Resilience4JConfigBuilder(id)
								.circuitBreakerConfig(CircuitBreakerConfig
										.custom()
										.minimumNumberOfCalls(10)
										.slidingWindowSize(10)
										.waitDurationInOpenState(Duration.ofSeconds(60))
										.build())
								.timeLimiterConfig(TimeLimiterConfig
										.custom()
										.timeoutDuration(Duration.ofSeconds(5)).build())
								.build());
	}

}
