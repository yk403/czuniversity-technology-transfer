package com.itts.paymentservice.constant;

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
}
