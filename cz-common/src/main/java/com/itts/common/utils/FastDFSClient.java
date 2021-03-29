package com.itts.common.utils;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FastDFSClient {
    private static Logger logger = LoggerFactory.getLogger(FastDFSClient.class);
    /**
     * ClientGlobal init 方法会读取配置文件，并初始化对应属性
     */
    static{
        try{
            String fdfsClientConfigFilePath = new ClassPathResource("fdfs_client.conf").getFile().getAbsolutePath();
            logger.info("Fast DFS configuration file path:" + fdfsClientConfigFilePath);
            ClientGlobal.init(fdfsClientConfigFilePath);
        }catch (Exception e) {
            logger.error("fastdfs client init fail",e);
        }
    }

    /**
     * 上传文件
     * @param file
     * @return
     */
    public static String[] upload(FastDFSFile file){
        logger.info("file name:"+file.getName()+"file length:"+file.getContent().length);
        NameValuePair[] meta_list = new NameValuePair[3];
        meta_list[0] = new NameValuePair("author",file.getAuthor());
        meta_list[1] = new NameValuePair("heigth", file.getHeight());
        meta_list[2] = new NameValuePair("width", file.getWidth());
        long startTime  = System.currentTimeMillis();
        String[] uploadResults = null;
        StorageClient storageClient = null;
        try {
            //获取Storage客户端
            storageClient = getStorageClient();
            uploadResults = storageClient.upload_file(file.getContent(),file.getExt(),meta_list);
        } catch (Exception e) {
           logger.error("上传失败，file name="+file.getName(),e);
        }
        //验证上传结果
        logger.info("上传时间="+(System.currentTimeMillis()-startTime)+"ms");
        if(uploadResults==null && storageClient!=null){
            logger.error("上传失败"+storageClient.getErrorCode());
        }
        //上传成功会返回相应信息
        logger.info("上传成功，group_name"+uploadResults[0]+",remoteFileName:"+uploadResults[1]);
        return uploadResults;
    }
    /**
     * 下载文件
     * @param groupName
     * @param remoteFileName
     * @return
     */
    public static InputStream downFile(String groupName,String remoteFileName){
        try {
            StorageClient storageClient = getStorageClient();
            byte[] fileBytes = storageClient.download_file(groupName, remoteFileName);
            InputStream ins = new ByteArrayInputStream(fileBytes);
            return ins;
        } catch (Exception e) {
            logger.error("下载失败",e);
        }
        return null;
    }

    /**
     * 删除文件
     * @param groupName
     * @param remoteFileName
     */
    public static  void deleteFile(String groupName,String remoteFileName){
        try {
            StorageClient storageClient = getStorageClient();
            int i = storageClient.delete_file(groupName, remoteFileName);
            logger.info("删除成功"+i);
        } catch (Exception e) {
            logger.error("删除失败",e);
        }
    }

    /**
     * 查看文件信息
     * @param groupName
     * @param remoteFileName
     * @return
     */
    public static FileInfo getFile(String groupName,String remoteFileName){
        try {
            StorageClient storageClient = getStorageClient();
            storageClient.get_file_info(groupName,remoteFileName);
        } catch (Exception e) {
            logger.error("查看文件信息失败",e);
        }
        return null;
    }

    /**
     * 获取文件路径
     * @return
     */
    public static String getTrackerUrl() throws IOException {
        return "http://"+getTrackerServer().getInetSocketAddress().getHostString()+":8888";
    }
    /**
     * 生成StorageClient
     * @return
     * @throws IOException
     */
    private  static StorageClient getStorageClient() throws IOException {
        TrackerServer trackerServer = getTrackerServer();
        return new StorageClient(trackerServer,null);
    }
    /**
     * 生成tracker服务器端
     * @return
     * @throws IOException
     */
    public static TrackerServer getTrackerServer() throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        return trackerClient.getConnection();
    }

}
