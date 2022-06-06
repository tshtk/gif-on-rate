package com.github.tshtk.gifonrate;

import com.github.tshtk.gifonrate.model.RateChange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.EnumMap;
import java.util.Map;

@SpringBootApplication
@EnableFeignClients
public class GifOnRateApplication {

	public static void main(String[] args) {
		SpringApplication.run(GifOnRateApplication.class, args);
	}

	@Bean
	@ConfigurationProperties(prefix = "gifonrate.tags")
	public Map<RateChange, String> tags(){
		return new EnumMap<>(RateChange.class);
	}
}
