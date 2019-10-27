package com.hr.service.redis;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import com.hr.paramenum.CacheEnum;
import com.hr.paramenum.SmsTypeEnum;

@Service
public class RedisService {
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	private final String GROUP_MARK=":";
	
	/**
	 * 写入缓存
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value,CacheEnum cache) {
		boolean result = false;
		try {
			redisTemplate.boundValueOps(cache.getGroupCode()+GROUP_MARK+key).set(value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	/**
	 * 写入缓存设置时效时间
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean setByTime(final String key, Object value, long expireTime,CacheEnum cache) {
		boolean result = false;
		try {
			redisTemplate.boundValueOps(cache.getGroupCode()+GROUP_MARK+key).set(value,expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 批量删除对应的value
	 * 
	 * @param keys
	 */
	public void remove(CacheEnum cache,final String... keys) {
		try {
			for (String key : keys) {
				remove(key,cache);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 批量删除key
	 * 
	 * @param pattern
	 */
	public void removePattern(final String pattern) {
		try {
			Set<Serializable> keys = redisTemplate.keys(pattern);
			if (keys.size() > 0) {
				redisTemplate.delete(keys);
			}			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除对应的value
	 * 
	 * @param key
	 */
	public void remove(final String key,CacheEnum cache) {
		try{
			redisTemplate.delete(cache.getGroupCode()+GROUP_MARK+key);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断缓存中是否有对应的value
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(final String key,CacheEnum cache) {
		try {
			return redisTemplate.hasKey(cache.getGroupCode()+GROUP_MARK+key);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 读取缓存
	 * 
	 * @param key
	 * @return
	 */
	public Object get(final String key,CacheEnum cache) {
		try {
	        BoundValueOperations<Serializable, Object> valueOper = redisTemplate.boundValueOps(cache.getGroupCode()+GROUP_MARK+key);
	        return valueOper.get();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	
	
	
	
	/**
	 * 设置短信验证码
	 * @param mobile
	 * @param type
	 * @param smscode
	 */
	public void setSMSCode(String mobile,int smsType,String smscode) {
		try {
			this.setByTime(mobile+""+smsType, smscode, 300L,CacheEnum.CACHE_SMS_CODE);//5分钟过期
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得短信验证码
	 * @param mobile
	 * @param type
	 * @return
	 */
	public String getSMSCode(String mobile,SmsTypeEnum smsTypeEnum) {
		try {
			Object smsObj=this.get(mobile+""+smsTypeEnum.getType(),CacheEnum.CACHE_SMS_CODE);
			if(smsObj!=null) {
				return (String)smsObj;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public void clearSMSCode(String mobile,SmsTypeEnum smsTypeEnum) {
		try {
			this.remove(mobile+""+smsTypeEnum.getType(),CacheEnum.CACHE_SMS_CODE);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	


	
	public void clearAppUser(final String token) {
		try {
			this.remove(token, CacheEnum.CACHE_CUSTOMER);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	

	
	/**
	 * 哈希 添加
	 * 
	 * @param key
	 * @param hashKey
	 * @param value
	 */
	public void hmSet(String key, Object hashKey, Object value) {
		HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
		hash.put(key, hashKey, value);
	}

	/**
	 * 哈希获取数据
	 * 
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public Object hmGet(String key, Object hashKey) {
		HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
		return hash.get(key, hashKey);
	}

	/**
	 * 列表添加
	 * 
	 * @param k
	 * @param v
	 */
	public void lPush(String k, Object v) {
		ListOperations<String, Object> list = redisTemplate.opsForList();
		list.rightPush(k, v);
	}

	/**
	 * 列表获取
	 * 
	 * @param k
	 * @param l
	 * @param l1
	 * @return
	 */
	public List<Object> lRange(String k, long l, long l1) {
		ListOperations<String, Object> list = redisTemplate.opsForList();
		return list.range(k, l, l1);
	}

	/**
	 * 集合添加
	 * 
	 * @param key
	 * @param value
	 */
	public void add(String key, Object value) {
		SetOperations<String, Object> set = redisTemplate.opsForSet();
		set.add(key, value);
	}

	/**
	 * 集合获取
	 * 
	 * @param key
	 * @return
	 */
	public Set<Object> setMembers(String key) {
		SetOperations<String, Object> set = redisTemplate.opsForSet();
		return set.members(key);
	}

	/**
	 * 有序集合添加
	 * 
	 * @param key
	 * @param value
	 * @param scoure
	 */
	public void zAdd(String key, Object value, double scoure) {
		ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
		zset.add(key, value, scoure);
	}

	/**
	 * 有序集合获取
	 * 
	 * @param key
	 * @param scoure
	 * @param scoure1
	 * @return
	 */
	public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
		ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
		return zset.rangeByScore(key, scoure, scoure1);
	}	

}
