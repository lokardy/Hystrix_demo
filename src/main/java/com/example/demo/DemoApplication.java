package com.example.demo;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import reactor.core.publisher.Mono;

@SpringBootApplication

@Configuration
@RestController
@EnableEurekaClient
@EnableHystrix

public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	 @GetMapping(path = "/test/{id}")
	 @HystrixCommand(fallbackMethod = "reliable")
	    public Mono<String> get(@PathVariable("id") String uuid) {
		 
	        return Mono.just("Hi " + uuid + " , Integrating microservices was never this easy");
	    }
	 
	 @GetMapping(path = "/test/fallback")
	 @HystrixCommand(fallbackMethod = "reliable")
	    public Mono<String> fallback() {
		 
		 try {
			Thread.sleep( 2 * 1000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        return Mono.just("Hi Integrating microservices was never this easy");
	    }
	 
	 
	 @GetMapping(path = "/test/failure")
	 @HystrixCommand(fallbackMethod = "reliable")
	    public Mono<String> failure() {
		
	        return Mono.error(new Exception("error"));
	    }
	 
	 
	 public Mono<String> reliable(String str) {
		     return Mono.just("Hi " + str + " ,Hystrix circuit opned");
		  }
	 
	 public Mono<String> reliable() {
	     return Mono.just("Hi  Hystrix circuit opned");
	  }
}
