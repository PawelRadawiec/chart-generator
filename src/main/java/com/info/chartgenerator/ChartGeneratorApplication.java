package com.info.chartgenerator;

import com.info.chartgenerator.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class ChartGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChartGeneratorApplication.class, args);
	}

}
