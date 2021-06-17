package pers.ditto;

import redis.clients.jedis.Jedis;

/**
 * @author OrangeCH3
 * @create 2021-06-17 16:01
 */
public class PingTest {

    public static void main(String[] args) {
        // 创建一个Jedis对象
        Jedis jedis = new Jedis("192.168.242.129",6379);

        // 测试连接虚拟机redis-server服务器
        System.out.println(jedis.ping());
    }
}
