package com.menes127.example.helloworld.web;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	@RequestMapping("/")
	public String welcome() {
		return "hello world";
	}
}