package com.menes127.example.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {

	@Autowired
	private JedisConnectionFactory jedisConnFactory;

//	@Bean
//	JedisConnectionFactory jedisConnectionFactory() {
//		JedisConnectionFactory factory = new JedisConnectionFactory();
//		factory.setHostName("xxxx");
//		return factory;
//	}

//	@Bean
//	public RedisTemplate<?, ?> redisTemplate() {
//		RedisTemplate<?, ?> template = new RedisTemplate<>();
//		template.setConnectionFactory(jedisConnFactory);
//		return template;
//	}
	
//	@Bean
//	public RedisTemplate<?, ?> redisTemplate() {
//		RedisTemplate<?, ?> template = new RedisTemplate<>();
//		template.setConnectionFactory(jedisConnFactory);
//		template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
//		template.setKeySerializer(new StringRedisSerializer());
//		template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
//		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//		return template;
//	}
	
	@Bean
	RedisTemplate<String, ?> redisTemplate() {
		RedisTemplate<String, ?> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisConnFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		return template;
	}

	@Bean
	public CacheManager cacheManager() {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate());
		cacheManager.setDefaultExpiration(60);
		return cacheManager;
	}
}