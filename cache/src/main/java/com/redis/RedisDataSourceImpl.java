package com.redis;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * Created by yaochenglong on 17/1/4.
 */
public class RedisDataSourceImpl implements RedisDataSource{

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(RedisDataSourceImpl.class);

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    public ShardedJedisPool getShardedJedisPool() {
        return shardedJedisPool;
    }

    public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }


    public ShardedJedis getRedisClient() {
        ShardedJedis shardedJedis =  shardedJedisPool.getResource();
        return shardedJedis;
    }
    /**
     * 将资源返还给pool
     */
    @SuppressWarnings("deprecation")
    public void returnResource(ShardedJedis shardedJedis) {
        shardedJedisPool.returnResource(shardedJedis);
    }

    /**
     * 出现异常后返回资源给pool
     */
    @SuppressWarnings("deprecation")
    public void returnResource(ShardedJedis shardedJedis, boolean broken) {
        if(broken){
            shardedJedisPool.returnBrokenResource(shardedJedis);
        }else{
            shardedJedisPool.returnResource(shardedJedis);
        }
    }
}
