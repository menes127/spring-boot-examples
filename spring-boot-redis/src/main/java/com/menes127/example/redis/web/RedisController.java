package com.menes127.example.redis.web;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		redisTemplate.expire("Tom", 30, TimeUnit.SECONDS);

		user = new User("Cat", 30);
		redisTemplate.opsForValue().set(user.getUsername(), user);
		redisTemplate.opsForValue().set(user.getUsername(), user);
		redisTemplate.expire("Cat", 30, TimeUnit.SECONDS);
		
		String result = String.format("Tom - {%s}  ", redisTemplate.opsForValue().get("Tom").getAge().longValue());
		result += String.format("Cat - {%s}  ", redisTemplate.opsForValue().get("Cat").getAge().longValue());
		
		logger.info("Tom - {}", redisTemplate.opsForValue().get("Tom").getAge().longValue());
		logger.info("Cat - {}", redisTemplate.opsForValue().get("Cat").getAge().longValue());
		
		return "hello world, " + result;
	}
	
	@Cacheable(cacheNames="users", keyGenerator = "wiselyKeyGenerator")
	@RequestMapping("/users")
	public List<User> users() {
		logger.info("the data does not come from cache");
		List<User> list = new ArrayList<User>();
		User user1 = new User("a", 1);
		User user2 = new User("b", 2);
		list.add(user1);
		list.add(user2);
		return list;
	}
	
	@Cacheable(cacheNames="users", keyGenerator = "wiselyKeyGenerator")
	@RequestMapping("/user/{id}")
	public User user(@PathVariable String id) {
		logger.info("the data does not come from cache");
		return new User("a"+id, 1);
	}
}