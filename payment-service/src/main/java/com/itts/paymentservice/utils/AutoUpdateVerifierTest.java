package com.itts.paymentservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.WechatPayUploadHttpPost;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.security.PrivateKey;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AutoUpdateVerifierTest {

    private static String mchId = "1598396261"; // 商户号
    private static String appid = "wx265a8aabddece7fe"; // appid
    private static String mchSerialNo = "2E202F8A9DC59DE32FA3AF88B75F6764FDC8AA12"; // 商户证书序列号
    private static String apiV3Key = "jiangsuzhongminghuiye12345678900"; // api密钥
    // 你的商户私钥 通过接口获取
    private static String privateKey = "-----BEGIN PRIVATE KEY-----\n" +
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

    //测试AutoUpdateCertificatesVerifier的verify方法参数
    private static String serialNumber = "";
    private static String message = "";
    private static String description = "技术商品拍卖保证金";
    //商户订单号，测试暂用定值，实际开发中传技术商品id
    private static String out_trade_no = "test000001";
    private static String signature = "";
    private CloseableHttpClient httpClient;
    private AutoUpdateCertificatesVerifier verifier;

    @Before
    public void setup() throws IOException {
        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(
                new ByteArrayInputStream(privateKey.getBytes("utf-8")));

        //使用自动更新的签名验证器，不需要传入证书
        verifier = new AutoUpdateCertificatesVerifier(
                new WechatPay2Credentials(mchId, new PrivateKeySigner(mchSerialNo, merchantPrivateKey)),
                apiV3Key.getBytes("utf-8"));

        httpClient = WechatPayHttpClientBuilder.create()
                .withMerchant(mchId, mchSerialNo, merchantPrivateKey)
                .withValidator(new WechatPay2Validator(verifier))
                .build();
    }

    @After
    public void after() throws IOException {
        httpClient.close();
    }

    @Test
    public void autoUpdateVerifierTest() throws Exception {
        assertTrue(verifier.verify(serialNumber, message.getBytes("utf-8"), signature));
    }
    //统一下单api
    //orderNo 订单号
    @Test
    public void orderInteface(String orderNo) throws Exception{
        URIBuilder uriBuilder = new URIBuilder("https://api.mch.weixin.qq.com/v3/certificates");
        HttpPost httpPost = new HttpPost(uriBuilder.build());
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type","application/json; charset=utf-8");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode rootNode = objectMapper.createObjectNode();
        //获取当前时间两小时后的时间
        String format = TimeUtils.DTF.format(LocalDateTime.now().plusHours(2));
        rootNode.put("mchid",mchId)
                .put("appid",appid)
                .put("description", description)
                //测试暂定固定值，实际开发需接入参数(商户订单号)
                .put("out_trade_no", out_trade_no)
                .put("notify_url", "https://technologytransfer.zmhycs.cn:11005/api/payment-service/api/test/get/");
                //交易结束时间(当前时间向后两小时)
                //.put("time_expire",format)
        rootNode.putObject("amount")
                .put("total", 1);
        rootNode.putObject("payer")
                .put("openid", "oUpF8uMuAJO_M2pxb1Q9zNjWeS6o");
    }
    //获取平台证书方法
    @Test
    public void getCertificateTest() throws Exception {
        URIBuilder uriBuilder = new URIBuilder("https://api.mch.weixin.qq.com/v3/certificates");
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.addHeader("Accept", "application/json");
        CloseableHttpResponse response1 = httpClient.execute(httpGet);
        assertEquals(200, response1.getStatusLine().getStatusCode());
        try {
            HttpEntity entity1 = response1.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity1);
        } finally {
            response1.close();
        }
    }

    @Test
    public void uploadImageTest() throws Exception {
        String filePath = "/your/home/hellokitty.png";

        URI uri = new URI("https://api.mch.weixin.qq.com/v3/merchant/media/upload");

        File file = new File(filePath);
        try (FileInputStream s1 = new FileInputStream(file)) {
            String sha256 = DigestUtils.sha256Hex(s1);
            try (InputStream s2 = new FileInputStream(file)) {
                WechatPayUploadHttpPost request = new WechatPayUploadHttpPost.Builder(uri)
                        .withImage(file.getName(), sha256, s2)
                        .build();

                CloseableHttpResponse response1 = httpClient.execute(request);
                assertEquals(200, response1.getStatusLine().getStatusCode());
                try {
                    HttpEntity entity1 = response1.getEntity();
                    // do something useful with the response body
                    // and ensure it is fully consumed
                    String s = EntityUtils.toString(entity1);
                    System.out.println(s);
                } finally {
                    response1.close();
                }
            }
        }
    }
}
