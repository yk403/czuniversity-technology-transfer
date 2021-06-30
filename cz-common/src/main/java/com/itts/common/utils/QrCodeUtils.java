package com.itts.common.utils;

import cn.hutool.core.codec.Base64Encoder;
import com.alibaba.fastjson.JSON;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者: yushuangyu
 * 时间: 2020年07月26日 9:16
 * 描述: 二维码生成工具
 */
@Slf4j
public class QrCodeUtils {

    /**
     * 生成不带白边的二维码
     *
     * @param content 二维码内容(目标url)
     * @param qrCodePath 生成的二维码地址(最终保存地址)
     * @throws Exception 异常
     */
    public static String generatorQrCode(String content) throws Exception {
        Map<EncodeHintType, Object> hints = new HashMap<>();

        hints.put(EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, 256, 256, hints);

        // 去白边
        int[] rec = bitMatrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;
        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (bitMatrix.get(i + rec[0], j + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }

        int width = resMatrix.getWidth();
        int height = resMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (resMatrix.get(x, y)) {
                    //                    image.setRGB(x, y, Color.BLACK.getRGB());
                    image.setRGB(x, y, -16777216);
                } else {
                    //                    image.setRGB(x, y, Color.WHITE.getRGB());
                    image.setRGB(x, y, -1);
                }
            }
        }
        //ImageIO.write(image, "png", new File(qrCodePath));
/*        InputStream inputStream = bufferedImageToInputStream(image);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String format = df.format(now);
        String fileNameString = format+"qrcode.png";*/
        //return uploadFile(inputStream,fileNameString);
        return bufferedImageToString(image);
    }
    /**
     * 将BufferedImage转换为base64字符串
     * @param image
     * @return
     */
    public static String bufferedImageToString(BufferedImage image){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", os);
            byte b[]=os.toByteArray();
            String str = new Base64Encoder().encode(b);
            //InputStream input = new ByteArrayInputStream(os.toByteArray());
            return str;
        } catch (IOException e) {
            throw new ServiceException("二维码转码失败");
        }
    }
    /**
     * 文件上传
     * @param
     * @return
     * @throws IOException
     */
    public static String uploadFile(InputStream inputStream,String fileNameString) throws IOException {
        String[] fileAbsolutPath = {};
        String fileName = fileNameString;
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        byte[] file_buff = null;
        if (inputStream != null) {
            int len1 = inputStream.available();
            file_buff = new byte[len1];
            inputStream.read(file_buff);
        }
        inputStream.close();
        FastDFSFile file = new FastDFSFile(fileName, file_buff, ext);
        if (file == null) {
            log.error("文件上传失败, 文件为空");
        } else {
            log.info("file:{}", file);
            fileAbsolutPath = FastDFSClient.upload(file);
            if (fileAbsolutPath == null) {
                log.error("【文件上传失败】");
                throw new ServiceException(ErrorCodeEnum.UPLOAD_FAIL_ERROR);
            }
            String path = FastDFSClient.getTrackerUrl() + "/" + fileAbsolutPath[0] + "/" + fileAbsolutPath[1];
            log.info("【文件上传成功】,path: {}", path);
            return path;
        }
        return null;
    }

    @Test
    public void genInviteQrCode3() {
        String qrCodePath = "https://www.baidu.com";
        String qrCodeLocalPath = "D:\\technologyTransaction\\测试\\百度首页22.png";
        try {
            // 生成不带白边的二维码
            QrCodeUtils.generatorQrCode(qrCodePath);
        } catch (Exception e) {
            log.info("error :{}", JSON.toJSON(e));
        }
    }


}
