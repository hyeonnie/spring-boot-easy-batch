package com.example.demo.job.hello;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

	public List<String> list() {
		return List.of("hello", "world!");
	}
	
}
