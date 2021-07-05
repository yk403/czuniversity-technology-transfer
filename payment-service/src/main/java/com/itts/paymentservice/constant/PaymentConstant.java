package com.itts.paymentservice.constant;

import java.io.FileWriter;
import java.io.IOException;

/*
 *@Auther: yukai
 *@Date: 2021/06/30/9:17
 *@Desription:支付常量
 */
public class PaymentConstant {
    /**
    * @Description: 微信支付常量
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/6/30
    */
    public static String wx_mchId = "1598396261"; // 商户号
    public static String wx_appid = "wx265a8aabddece7fe"; // appid
    public static String wx_mchSerialNo = "2E202F8A9DC59DE32FA3AF88B75F6764FDC8AA12"; // 商户证书序列号
    public static String wx_apiV3Key = "jiangsuzhongminghuiye12345678900"; // api密钥
    // 你的商户私钥 通过接口获取
    public static String wx_privateKey = "-----BEGIN PRIVATE KEY-----\n" +
            "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDGrY6RG17S3ky0\n" +
            "21BewjfIBRVLAqODkWZ9RaKnlWrHRSyuIqDtxRy0QhKxjJnMWc2JyNchstQ/zXnQ\n" +
            "eu2SjZ/gWMoTv6u5JbajhBHbNPo+a2iIXW7CFkJyUwDY0F/Yux4abzIxzJdJtcdq\n" +
            "8BNkOcXKIG7ZwYORq7Z093LQDMoxoyxfDr2p4FqqESqgyg/sVCYCBGdeg7dIzWxS\n" +
            "S+IPlWcdjX5ndGtammFT1hGmgqqWMXW81yispR9au+BUoeDpniq+osNGh7JTUzcl\n" +
            "pbyLOI9tadvgEZL4tuh276M5vfYa6hyiCPjttoxzpLITXvv3/iJEl5ksOhHc0U2h\n" +
            "coGkmE23AgMBAAECggEAOhAk1zGJj9TZ1A04OslF5hXeODLIOL44lqnQNGvNbflA\n" +
            "ao/COlrd3axlrC/sDrW8BOWGQdnyf8b8L7qNTcRIEhfAzu64L/7eloEVetvLuoxH\n" +
            "W3r60IwS1Th+yd5z9HITBSS0JaumFyuh4RXShPsBW+YIn2kXm230f9CFZFooiUKj\n" +
            "1ftOyPxoCd+jKCNVsvEj2vckYRfA0Yg7fRXvog8wjUHsm+jWLhPuj9Teru3uduEq\n" +
            "AWZ5iivPrP84RKByLjrNsjRLDip5m0ZqTI17XrMo3YVdWtB8N7xQkT8EieWtwU1q\n" +
            "WbmHnwWhq3WurRs+fcj2NwC0z4jDr/i+wLFRbRTmEQKBgQDrlWSJ6gBfRR0rnox9\n" +
            "DjFEZ0Y0deKvA26rOnsqFv5lYABj3m9aVglsoOhed8mIbAea7/JFoyJIfxpIDlbQ\n" +
            "G+W+rMFfjtTtbzkXXSrEdkT0COuEai8KZHeyOG/uUpvUbNWeSKzS+TFhG/rHbLYS\n" +
            "yeKn9DRzCAIZnhq7tgt/wveS7wKBgQDX5WJWBCH/IhLfDbO36ir4bqlo+KPJclwN\n" +
            "1i6sIPiY+KmyBz4qQQvO3OD39BrVg06BhV2tOuuysSRWh24Ip5ZW9vmGb7p4kv8d\n" +
            "9zzbqcaHdGVjjetQiEOJ1183dq6L7VAEaKAmbdBRP1HHby2q+V9IpW/Bc6ikfy9q\n" +
            "iYxuQubRuQKBgHKcv8kI+x1edo6eGCNPIFDzHYiwQZR7yv+q9/jGLwYK6qWrtejx\n" +
            "Kqyaq/IpvPaN+DU8v1V5xFgY3iRoNXwR1ngpe/qNdrH9Bn2DaicikvFW1Z4aOsCH\n" +
            "mGQwaOQ9dGqoeFUFbm9FpOxlvGABV6NUbNzhRhMgtkTYUpE4Q+foZqIpAoGAH82E\n" +
            "fNNDBshIK19jV/ZAujPi9LmbtvMHqGoimeyJck8/ACCdAbBvQtu6sAvfYBBloiK5\n" +
            "N+l0CvCZP73XcaLuPIVJIIqY12Yf+pQO/PS3cACvZsjjHwvTlxyIxCzRbX9ZyCx5\n" +
            "Wu/WPgGr1EsGWSc+5/6mxcJ51zF9FJ6KdzuXsNECgYABxN6UIOnSe+lXCcQmbxD2\n" +
            "cTahZbRjuuOA/jFGM5IIFBsJlJM2nlA9GBBVLOB6M3db7ZJCCiuBHE9RWCWroYhx\n" +
            "cxQyTQGta1OEo+Kwg11seBOLvLQWT4pAl3oo2+CdJ9CN/psWHcx0T3+rYKSI/R+r\n" +
            "8qdeyrheNscllAg8RURy8g==\n" +
            "-----END PRIVATE KEY-----\n";
    public static String wx_notify_url="http://yukai.vipgz4.idcfengye.com/payment/api/notice/get/";
    public static String wx_native_transactions_url="https://api.mch.weixin.qq.com/v3/pay/transactions/native";
    public static String wx_native_selectTrade_url="https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/";
    /**
    * @Description: 支付宝支付常量
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/6/30
    */
    //↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号(沙箱环境)
    public static String alipay_app_id = "2021000117683408";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String alipay_merchant_private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC5Cyk/d/z6CONIm+LRr+yZoAE34cjqoD8RxP1QSQ3qQBygT7SpAoYlvZ2KbN3P7DIedFm8PT1NnRB1M7ap2oY5S2gswgMzb1RcEPs2pkJoqEmMZRghpCZfclOcQOBDAU7QzTEaXEXBJtbWA89Xa0YFDa3NBnDJmdNsUrFkarEQ0mAmxHffw00t09E1ghw6v0tQC2TSYUc7S5+5QV25dmt96A2kk5tl5pUUxEosB1MZ9xA/vhQ41df6HFg4cSTj60Qe4gQ5x4f+RsARCNHKzpMKRUivRwYH27IKtirpRLBYMpdHfUQP9Tpv6Iy/gO0ytdbvyFSkafsa+dAoic9BXMmhAgMBAAECggEBAJVzWT4kp9I9yWvEU34tyzcMjs9WcVJmWGn+ouTsC+wNGdG0bmIuYrAqpAnAAapDxvrn1+37u/5qg3dVbAtlLhEqQRodHD63hPfS00KBJ5Olj7lvkPziugdOrA7AKyCS9tgXUbhLnB7zQoKZkyW0w1mjgaLct81riuGrLBDBRv8gumc03CTpEEnLfsqWfFCXt8Ozd3PtM6Bsx2JG6MHVrjuwBJnFG2o9Vez2+EJNNo5jh9LB8oqYhvRmKMrXSb3FQW8Px5irgmlyj88uJDDiDsmGZrp1U2s3w/g76IUQBcaPBdHafN+33wPTwU5s4JNjLe/2hWZHq/qU+QGT15bAHwECgYEA5bxBN1+A4HYyDgyMkFHEdEyPOqv8hmHnWNMkXWTeFAhuibuyLsrZ0+poMiw1MmtqqQ2BW4U6Np1iMf6IvFaxUxsTwnidyKOhvVwXT9G+7/DZVITu+dmlaRYAUFbVHhw/MSkTTZJRn2ZjQFsaYVWs6SkUN3FzJpDiHFrJ9Qh/4PECgYEAzjLljUROLmPk3t4pXXcFvVbrCRX67uTy7hTWT6F1j+iXavlKy7zWtftoW6VaIVxhiA8LDxuMarrJW/CxV5q14VfWrce00fmJqfGs5UGSw/cZ4Jy3hTh5Y+tXujEfhLzR5Q2OqdYZgYL9eYj6X54zUG9ZfvSAKTXmN6m5Ij1Gc7ECgYEAo2Q/8fUPwRTvRsSwGFi/Y4Or/quhn6X0F12cKHx74j2Y+IaxFMybHjhCQSTl9OUm5/M+BGx92agX1bzVXTY/Sf8hD5gfiDqw5u+feNqRdS+UMqOVH8gm99V5BaqsSo0GeJ1hKDOfr8HvZy8kVQPNRcs8oK4kmtJCrq33ozmXUdECgYEAile3j5oeYh1/ds/TLPQoE6p3Mrdejl1pZ7bvOqn3OEAzJwBu4FaoL5ynnaVI3U0f2qtNHiu7f/gmFNteAIq6MKBAVoPAm4gyhqSw/XeLQpNM91fuASGswA8r4q4GmYYdxPHdn7EXl78M1TfD09dOUk8C2pysR/nzpbDnl0NJO2ECgYATtjrvvsWE3pItemueSTQiR/+Dh7HfOq463RBA3t+f+fSSXCwn5iEZQElWwwigRQWOeKBGqUyL/wr7GGFytuaK2yPJPZij1uWx3xLCpHHFnOSxpwDKVmoZj0m6FJ3BV3qa2918xkh4wpFZEHGikbY5q61X9MsoC+FqwcCdzWLrhQ==";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuQspP3f8+gjjSJvi0a/smaABN+HI6qA/EcT9UEkN6kAcoE+0qQKGJb2dimzdz+wyHnRZvD09TZ0QdTO2qdqGOUtoLMIDM29UXBD7NqZCaKhJjGUYIaQmX3JTnEDgQwFO0M0xGlxFwSbW1gPPV2tGBQ2tzQZwyZnTbFKxZGqxENJgJsR338NNLdPRNYIcOr9LUAtk0mFHO0ufuUFduXZrfegNpJObZeaVFMRKLAdTGfcQP74UONXX+hxYOHEk4+tEHuIEOceH/kbAEQjRys6TCkVIr0cGB9uyCrYq6USwWDKXR31ED/U6b+iMv4DtMrXW78hUpGn7GvnQKInPQVzJoQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String alipay_notify_url = "http://yukai.vipgz4.idcfengye.com/payment/api/notice/alipaypost/";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String alipay_return_url = "http://yukai.vipgz4.idcfengye.com/payment/api/notice/alipayget/";

    // 签名方式
    public static String alipay_sign_type = "RSA2";

    // 字符编码格式
    public static String alipay_charset = "utf-8";

    // 支付宝网关(沙箱环境，正式环境在下面)
    public static String alipay_gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
    //public static String alipay_gatewayUrl = "https://openapi.alipay.com/gateway.do";

    // 支付宝网关
    public static String alipay_log_path = "F:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(alipay_log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
