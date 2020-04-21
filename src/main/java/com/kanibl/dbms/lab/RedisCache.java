package com.kanibl.dbms.lab;

import java.sql.SQLException;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisCache {
	private JedisPool pool = null;
	
    public RedisCache() {
    	pool = new JedisPool(new JedisPoolConfig(), "redis-12402.c9.us-east-1-2.ec2.cloud.redislabs.com", 12402, 0, "nantes");
        Jedis j = pool.getResource();
        j.close();
	}
    
    public void createSchema() throws SQLException {
    	try {
    		Jedis j = pool.getResource();
    		/*try {
            	jedis.hdel("users");
        	} catch (Exception e) {
            		System.out.println("Drop table failed - probably because it does not exist");
        	}*/
    	
    		j.hset("users", "John", "Doe");
    		j.hset("users", "David", "Getta");
    		j.hset("users", "Billie", "Eilish");
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public Map<String, String> getUserById(int id) throws SQLException {
    	Map<String, String> users = null;
    	try {
    		Jedis j = pool.getResource();
    		users = j.hgetAll("users");
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
        return users;
    }
    
    public static void main(String[] args) throws Exception {

        RedisCache rc = new RedisCache();

        rc.createSchema();

        System.out.println("Print all users");
        System.out.println(
                //rc.getUserAllAsMap()
        );
        System.out.println("========\n");

        System.out.println("Print one user");
        System.out.println(
                rc.getUserById(1)
        );
        System.out.println("========\n");

    }
	
}
