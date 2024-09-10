package com.ispark.api_gateway_service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayServiceApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("tariff-service", r -> r.path("/api/v1/tariffs/**")
						.filters(f -> f.rewritePath("/api/v1/tariffs/(?<segment>.*)", "/api/v1/tariffs/${segment}")
								.requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())))
						.uri("http://tariff-service:8080"))
				.route("tariff-operation-service", r -> r.path("/api/v1/tariff-operations/**")
						.filters(f -> f.requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())))
						.uri("http://tariff-operation-service:8081"))
				.route("vehicle-type-service", r -> r.path("/api/vehicle-types/**")
						.uri("http://vehicle-type-service:8083"))
				.route("customer-type-service", r -> r.path("/api/customer-types/**")
						.uri("http://customer-type-service:8082"))
				.build();
	}

	@Bean
	public RedisRateLimiter redisRateLimiter() {
		return new RedisRateLimiter(10, 20); // Example values
	}
}
