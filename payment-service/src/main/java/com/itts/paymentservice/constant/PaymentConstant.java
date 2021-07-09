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
    //public static String alipay_app_id = "2021000117683408";
    public static String alipay_app_id = "2021000117685792";

    // 商户私钥，您的PKCS8格式RSA2私钥
    //public static String alipay_merchant_private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDGlos+DI8bsLq3+b+neBrlmz9Z7dJcab6Id0ILMeoORzHf4/We3ltQCdDo6dB0jmD/bJujDG4+tWHoyImUTzpR5i3h9NliQfe+coaTgZb66I/OonAGM9UWILGQR1uzOreB3je6UXZKwAKLR1LrjBSq6bolTKFg0Rbra+fz1f1Gd8JF4MrC3KT8zWy90CksxmIUhEDqlSIH82I5CiHCJFAEJLxg+nLnCwdZReq5N3ly6VjIb0wpfFWjjnUL4qiLWHcoXxMcY8RTdLdLy4YoVXsoADTqRzl+jIIrE2biZr9KOo7aAU1HpMliamWd1SMn0lPrx403aYBzplLGzFugMm0TAgMBAAECggEBAIvKa0gJZS+zzyw0hUHo3N4l04mFTKA626GKeZYR09WrLiORf1QO6C5GSqW5+UAi/YqvtDspQ+2mlr5bc+VPhBkks8WsKt3s2+1XBv6MqXAEBz8Iv1zOXcBO0U9ju9xFh+nGOGpSb7ecw+2xDmC64c2R67PX5n2VkdG0yrmtN7XC2BB5bQvWQX4CbFW30B4Og9YlxFV7CK32HlOTEgAmw/RHNOOITfVKL5c23sOS2wrJlSQ+ou1OxFhI5S1MQ95/1+qu0drA85+QyR97Zw2AoEyIKHIWeZMfukuEndF15gHPecPoARmkJ2XPQwxBZn6+xFX06ZCMdCqMUnE4ruDo4XECgYEA5/5wV0j/JrZ6GKCHjzFRwmmV5IqfR8Q35mmW5VKSziyddkrpBt5J0a0F9jPdxT+R8vcVQVZ5qoVgXNj6CTuykSmttmSygunqtc8zWLpCmwZUoyJmc4oHNfEplaq8n3xvhnfm+Iak7SqdfNVfLRVcLE3/hLPy4Owla4zZNlN1bOsCgYEA2yMtoW7N1OgsU7sP+oh0377xkMp8XRjzCOkvXGdDOAg4tSjvJciu5HsNyQNOwez2nAR7qIEbOEtLIuby+0/qfrdgVfHCkRy2zOr7MaXbFdGjMiUcMKxslcBb9NIMtrtd/m0GYE8q+LaSu4mrXisSF3QAEktOPlA2427qe56RVnkCgYEA1FOF5A0WIQtPceyyRPbhmmpiWpoYr2lBtCsdhOLvI62nq9VhsvyNWU7fLvdHWLkWzeUf2f3YOp35lOCs7vhpNPyNVWqSCskmHKH3n5RRU2sW2XupgsNoWBFRev6ZxenwdISWqw0oMzwWiWMLycpL4uy8GGtNtlfK48U0I8l8pmMCgYATZMGdD9lXhlSl9GAto/Xz90oZrlHjS1n25+wxGVJxmwipv72qcmBICDvoFkraDsakeCEZgJOQH9m+dQH8C86M+F2mPYcioI/tAr7IXg1NmQgAYBhmllvlxOheelh1s49V/QAbdeYvrLi/sCdiZh1rxfBbrh52lVLGPgKyfg4OwQKBgQDI4ZKo2rjYT73tWGHJFL6RklzoZNdJjRMd7OC8fgM61lubk3evPZmoLBRE049XsrS+DS5TQP8/8vruBJNI2QPMte3RGk6rzQLvAakFyL9dtCJ5csXafPQ1s0PKaPkJyhxTZx1lNAs3DrUlemdGDS1OYTmBueMFPISfI9LxJTADTg==";
    public static String alipay_merchant_private_key ="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCmMUWgMzn2cy15U2cfQTMqxiQjdd4pxpwIKGWNSSN8x7rNoFRdHki8iBsvRZ4de9luh2C0YgZM7AdhOTdUTkBKj6inh43IxiEOHjlSVKRNPKTfNs9dI+0vbgEDG9SsS5wMRhSe+LzFg902StxZ9Rcdj0UaUfzeIjFotlJ8NzJVc4zdGtOgaMgtzD3U+ZgMKpk2k4N0JvdMQXaW43LTN3PwI9IF4whovj7bgmrk6pktG6kzjmtSUrU0bR/9somKQDCWFuXrqulOMKucgY5uWdfouOMRJHR5pjokj0RPcLJ/wPuiYfZT+lF9rK9bj3XDL0qDVIVYn9taHzLfVKN/czytAgMBAAECggEBAJ2YjcDrsuRstkud9mUiC+Zc/LoPtLwC1cW6oQpIt/lE4XTKvBWNeUYm/zhlmRm2byqr/90uM3FQRXNBsaEljzvlXPZ4ydABaikcKhlToHaJsoLlU+CsB+h5NUaAZr13+aN99WIG1+OpNuWcObaofuIOEWZzJwpyfE4izrdyFC4InPIhLVAkXx0DIHvPAkqIKQsQ23j9ymd3pOmaudVkF5VOxPQht9B4fTgAHYdGwZdWi3wj3DzWXYXOUqaWnMwGGQcYfs5JIY+h/CHUv2QdaTl6Dhhdk0n4u5T5tUAlAQJwD9vtQSYdaKjy8Z3gJXpeXC1efVwVKaOJLt3XlFKX/EECgYEAznlzJnxU9uiiVKJAjfi5s0U3KcQWgUPOgwrlRszxD253p7zjkJIFhsDetlq4SDB0quKI8VtqJkhdZ+z4oeLNtN9eR2qPqpNkHR8V30PAIEWaGhkFgTXV7nV585voMC4CMLM7NbVKHYRNxnYDYlgNnjac8qujhGv/aEqT6w5t3x0CgYEAzg5PwL5COmmwMV0jCTAldKMqRjqdXf3toCnk366CnLyqzPWgFjUPSnnc+BX/xxLSV1fmLkldi0BK88D9DSyCFjoDKnlHs2aOzTGzZ2OGgPEpp0apATbvzkUcHozC/EsGxpFb/l27wR6IQr8whhJumzfbxjBb+ksScGshQkeWjtECgYEAl1GOwJ/SLPolLd2TbfjhL68EVImu7KFjEcrEaXT9HhAZhUb7iBJcFnv3PLGT6OQKPMq1Dh2u18ulGOsk9I+hrnMdRz2jeKF4d5c2kl+2yG/6FjJMRHvOA2r5Kz4Rlm3BkStps1zfqm4yCcaniEV8BX2E3JxJxFzZ2/b8HQKAJ0kCgYAd3BMQkkGUp2Yh8SY3maOD4HUq0imlyjtaH1gMVgSVY/24T8vQDAZYO4qa5M048vBKOfGgOX91w25ZHllNRGrhf6yfxg1u4OjGsXIXBGQkYU4w8FtjUquzj25AuxMybkjgE4ZqOPkRRjRmv7/vXdJrac/OS4m4npJgehXKvgcCUQKBgCX4p+heMqQk+nx96s5rGtGK2894wbdVBri0cVrlRTCUZel72fkc+G8RFFQXQbEdtVI0R0iKL0CiuF9yU4umSjGU4GuaG8EfuG656QfI92f7Ok6nQRLmM+QbU5cKfOy2ldajrTVlBTs1O2ePMaxFAVQ7UPPqtJX7CRpQNqnqMffG";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    //public static String alipay_public_key = "MIIDszCCApugAwIBAgIQICEHAmApNynLUCBQXtjxIzANBgkqhkiG9w0BAQsFADCBkTELMAkGA1UEBhMCQ04xGzAZBgNVBAoMEkFudCBGaW5hbmNpYWwgdGVzdDElMCMGA1UECwwcQ2VydGlmaWNhdGlvbiBBdXRob3JpdHkgdGVzdDE+MDwGA1UEAww1QW50IEZpbmFuY2lhbCBDZXJ0aWZpY2F0aW9uIEF1dGhvcml0eSBDbGFzcyAyIFIxIHRlc3QwHhcNMjEwNzAyMDgyNDU2WhcNMjQwNjMwMDgyNDU2WjCBhDELMAkGA1UEBhMCQ04xHzAdBgNVBAoMFmtxaGlvcjg0MTVAc2FuZGJveC5jb20xDzANBgNVBAsMBkFsaXBheTFDMEEGA1UEAww65pSv5LuY5a6dKOS4reWbvSnnvZHnu5zmioDmnK/mnInpmZDlhazlj7gtMjA4ODYyMTk1NjA5NzcwNDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAIjLwyq1Y9Gfwu5iP8kq95NSVn2DtOAIZB2aSRFYM0DARSEz9/7SN1LQpa0T4QRcjAlSG68uFeD2XV9ynK1JkyKUK64uZgvVA1lxh+cTmHzWl0fjYg+FhzsdwRroDVLqIRZBwJTa5KzAt6MW10ZUBegBt9hGLUiXwziBniNuq/BZKHoDWgnHkDgZO9BVsGP77YJDd5Nm1KNNiKQtYsvSbr3SbO+Icr6Jex3+C3f1IeooAp0W9jtR6d+mTN/my143P3mbQ3KY/a9nKNkxF0jwcCb8PU3ozAOSi99s53XtKCiruVkq9oba+xsWqSmVTmxv3ekmbPbw1EGBXqNrRp5/tgsCAwEAAaMSMBAwDgYDVR0PAQH/BAQDAgTwMA0GCSqGSIb3DQEBCwUAA4IBAQBHvs7yhvqfpYItQDO/I4Jj7Ve578NRVXW9B3oa4xzhCYUHGnNBwK9EUXaB+iL5lNbTZE5iPVUahtN8FM0ajmdwU2NuoJUIydj0aGWhWYY8ELTtF8P8Q/3o1uGAeps+NPQz1wPDYEp1168gOJru/qKb7f3Jwp+MHo8xrbj/trYASEP9GQQljvkBLUj2NXOhVpTBTQ4aMIUEW4XeOnWE4PFN8c7AEGd/tu6Fn490Ek4vC8ezQ9AEWdwB7y3WldH1dKJw2AgvdzdLCOFKeeQxhxlG/CoXJ4hG2pcv0vFCEQhSH/WAkLJzi0qrhOKW1BtDtdD7Ij72M+7dolH6Ndu545B1";
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgeM0aIbY7rEaQc8UeJsCrK2lH4iYnAZ4WaM0yf3tBdlzzbpRJHXwvPErxMUIQYjrud6jpBlsfcyYRKYnMeG9bBLwSxyWCgKotVXc3680XRYjSJhoVW6qS0YOiqveqEfuHgjt1RexFtCQ0gbC9JOURjAPnYko7gnr7WsqTW5LtFaT0lJsSE5ENGbbwVji8l2TYEv8h6hEVxd67Cz33ctJ708Sse9QpDPGtC3FryMwPVG8YVesyP96DyLhx4dJanvXD1NSOUpLxpz82E6164fOxSHkdoTBl3BKJCoWTTsCeLLJYyfoGWuJglRrer7qUKzFdDpoHOefKhZL61SgdtCMKQIDAQAB";
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
