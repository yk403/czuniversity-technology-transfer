package com.itts.common.utils;

import lombok.Getter;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
public class FastDFSClient {
    private static Logger logger = LoggerFactory.getLogger(FastDFSClient.class);

    /*    private static String confFilePath;
        @Value("${dictionary.confFilePath}")
        public void setConfFilePath(String confFilePath) {
            FastDFSClient.confFilePath = confFilePath;
        }
        public static String getConfFilePath(){
            return confFilePath;
        }*/
    //获取application-local.yml文件中的属性配置
    public static Object getCommonYml(Object key) {
        Resource resource = new ClassPathResource("/application.yml");
        Properties properties = null;
        try {
            YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
            yamlFactory.setResources(resource);
            properties = yamlFactory.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return properties.get(key);
    }

    /**
     * ClientGlobal init 方法会读取配置文件，并初始化对应属性
     */
    static {
        try {
            //获取配置文件中自定义的fastdfs_config.path值
            //String filePath=(String)g/AchievementsetCommonYml("dictionary.confFilePath");
            //System.out.println("测试打印"+filePath);
            String fdfsClientConfigFilePath = FastDFSClient.class.getClassLoader().getResource("classpath:fdfs_client.conf").getPath();
            logger.info("Fast DFS configuration file path:" + fdfsClientConfigFilePath);
            ClientGlobal.init(fdfsClientConfigFilePath);
        } catch (Exception e) {
            logger.error("fastdfs client init fail", e);
        }
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    public static String[] upload(FastDFSFile file) {
        logger.info("file name:" + file.getName() + "file length:" + file.getContent().length);
        NameValuePair[] meta_list = new NameValuePair[3];
        meta_list[0] = new NameValuePair("author", file.getAuthor());
        meta_list[1] = new NameValuePair("heigth", file.getHeight());
        meta_list[2] = new NameValuePair("width", file.getWidth());
        long startTime = System.currentTimeMillis();
        String[] uploadResults = null;
        StorageClient storageClient = null;
        try {
            //获取Storage客户端
            storageClient = getStorageClient();
            uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);
        } catch (Exception e) {
            logger.error("上传失败，file name=" + file.getName(), e);
        }
        //验证上传结果
        logger.info("上传时间=" + (System.currentTimeMillis() - startTime) + "ms");
        if (uploadResults == null && storageClient != null) {
            logger.error("上传失败" + storageClient.getErrorCode());
        }
        //上传成功会返回相应信息
        logger.info("上传成功，group_name" + uploadResults[0] + ",remoteFileName:" + uploadResults[1]);
        return uploadResults;
    }

    /**
     * 下载文件
     *
     * @param groupName
     * @param remoteFileName
     * @return
     */
    public static InputStream downFile(String groupName, String remoteFileName) {
        try {
            StorageClient storageClient = getStorageClient();
            byte[] fileBytes = storageClient.download_file(groupName, remoteFileName);
            InputStream ins = new ByteArrayInputStream(fileBytes);
            return ins;
        } catch (Exception e) {
            logger.error("下载失败", e);
        }
        return null;
    }

    /**
     * 删除文件
     *
     * @param groupName
     * @param remoteFileName
     */
    public static void deleteFile(String groupName, String remoteFileName) {
        try {
            StorageClient storageClient = getStorageClient();
            int i = storageClient.delete_file(groupName, remoteFileName);
            logger.info("删除成功" + i);
        } catch (Exception e) {
            logger.error("删除失败", e);
        }
    }

    /**
     * 查看文件信息
     *
     * @param groupName
     * @param remoteFileName
     * @return
     */
    public static FileInfo getFile(String groupName, String remoteFileName) {
        try {
            StorageClient storageClient = getStorageClient();
            storageClient.get_file_info(groupName, remoteFileName);
        } catch (Exception e) {
            logger.error("查看文件信息失败", e);
        }
        return null;
    }

    /**
     * 获取文件路径
     *
     * @return
     */
    public static String getTrackerUrl() throws IOException {
        return "http://" + getTrackerServer().getInetSocketAddress().getHostString() + ":11000";
    }

    /**
     * 生成StorageClient
     *
     * @return
     * @throws IOException
     */
    private static StorageClient getStorageClient() throws IOException {
        TrackerServer trackerServer = getTrackerServer();
        return new StorageClient(trackerServer, null);
    }

    /**
     * 生成tracker服务器端
     *
     * @return
     * @throws IOException
     */
    public static TrackerServer getTrackerServer() throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        return trackerClient.getConnection();
    }

}
