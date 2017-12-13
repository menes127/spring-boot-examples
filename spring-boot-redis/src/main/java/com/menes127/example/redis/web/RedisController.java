package com.menes127.example.redis.web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.menes127.example.redis.domain.User;

@RestController
public class RedisController {
	private static final Logger logger = LoggerFactory.getLogger(RedisController.class);
	
	@Autowired
	private RedisTemplate<String, User> redisTemplate;
	
	@RequestMapping("/")
	public String welcome() {
		User user = new User("Tom", 20);
		redisTemplate.opsForValue().set(user.getUsername(), user);

		user = new User("Cat", 30);
		redisTemplate.opsForValue().set(user.getUsername(), user);
		redisTemplate.opsForValue().set(user.getUsername(), user);
		
		String result = String.format("Tom - {%s}  ", redisTemplate.opsForValue().get("Tom").getAge().longValue());
		result += String.format("Cat - {%s}  ", redisTemplate.opsForValue().get("Cat").getAge().longValue());
		
		logger.info("Tom - {}", redisTemplate.opsForValue().get("Tom").getAge().longValue());
		logger.info("Cat - {}", redisTemplate.opsForValue().get("Cat").getAge().longValue());
		
		return "hello world, " + result;
	}
}