package com.example.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class NewsJob {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate redisTemplate;

    @Scheduled(cron = "0 0/1 * * * ? ")//每1分钟
    public void SaveNewsData() {
        Set<String> keys=redisTemplate.keys("news_*");//获取所有以news_开头的键
        for(String key:keys){
            Map<String,String> map=redisTemplate.opsForHash().entries(key);
            String id=key.substring(5);//获取key中包含的id号，key是news_+id的格式
            String read=map.get("read");//获取阅读次数
            String zan=map.get("zan");//获取点赞次数
            //然后就可调用业务层将这些数据update到数据库，读者自行实现。
            logger.info("编号："+id+",阅读次数："+read+",点赞次数："+zan);
        }
        logger.info("以上数据已保存到数据库");
    }
}
