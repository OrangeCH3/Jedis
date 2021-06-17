package pers.ditto;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * @author OrangeCH3
 * @create 2021-06-17 16:25
 */
public class TransactionTest {

    public static void main(String[] args) {
        // 连接jedis
        Jedis jedis = new Jedis("192.168.242.129", 6379);

        // 清空当前数据库
        jedis.flushDB();
        // 通过fastjson获取json字符串
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Hello","Sun");
        jsonObject.put("Name","OrangeCH3");

        // 1.开启事务
        Transaction multi = jedis.multi();
        String result = jsonObject.toJSONString();

        // 乐观锁（监控result）
        // jedis.watch(result);
        try {
            // 2.命令
            multi.set("user1",result);
            multi.set("user2",result);
            // 3.执行事务
            multi.exec();
        } catch (Exception e) {
            multi.discard(); // 失败就放弃事务
            e.printStackTrace();
        } finally {
            System.out.println(jedis.get("user1"));
            System.out.println(jedis.get("user2"));
            jedis.flushDB(); // 再次清空当前数据库
            jedis.close(); // 关闭连接
        }
    }
}
