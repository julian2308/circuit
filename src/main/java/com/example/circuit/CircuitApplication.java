package com.example.circuit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class CircuitApplication {

	public static void main(String[] args) {
		SpringApplication.run(CircuitApplication.class, args);
		System.out.println("hoa");
	}

	@Bean
	public RestClient getRestClient() {
		return RestClient.create();
	}

	@Bean
	public ExecutorService getExecutorService(){
		return new ThreadPoolExecutor(10, 10,
				0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
	}


}
