package com.gecko.fastdfs.util;

import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

import java.io.IOException;

/**
 * FastDFS 分布式文件系统 Java 客户端工具类
 * 具体功能：文件上传、下载、替换、删除、查询文件元数据、查看文件详情
 */
public class FastDFSUtils {
    private static TrackerServer trackerServer = null;
    private static StorageServer storageServer = null;
    private static StorageClient storageClient = null;

    static {
        //1.加载配置文件，默认去classpath下加载
        try {
            ClientGlobal.init("fdfs_client.conf");
        //2.创建TrackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        //3.创建TrackerServer对象
            trackerServer = trackerClient.getConnection();
        //4.创建StorageServer对象
            storageServer = trackerClient.getStoreStorage(trackerServer);
            //5.创建StorageClient对象，这个对象完成对文件的操作
            storageClient = new StorageClient(trackerServer, storageServer);
        } catch (IOException | MyException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // fileUpload("D:/2480.jpg", "jpg");
        // FileInfo fileInfo = getFileInfo("group1", "M00/00/00/wKi2gGBatqKALsMoACoeC5KngBs414.jpg");
        // System.out.println(fileInfo);
        // boolean delete = fileDelete("group1", "M00/00/00/wKi2gGBatGqAPp3bACoeC5KngBs6975405");
        fileDownload("group1", "M00/00/00/wKi2gGBatYiASR5EACoeC5KngBs842.jpg", "D:/888.jpg");
        String fileExt = getFileExt("hellllll.gif");
        System.out.println(fileExt);

    }

    /**
     * 资源释放
     */
    public static void releaseFastDFS() {
        if (storageServer != null) {
            try {
                storageServer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (trackerServer != null) {
            try {
                trackerServer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 文件上传
     * @param localFileName 本地文件名[具体位置+文件名]
     * @param fileExtName   上传文件的后缀
     */
    public static void fileUpload(String localFileName, String fileExtName) {
        try {
            //6.上传文件  第一个参数：本地文件路径 第二个参数：上传文件的后缀 第三个参数：文件信息
            String[] uploadArray = storageClient.upload_file(localFileName, fileExtName, null);
            for (String str : uploadArray) {
                System.out.println(str);
                // http:192.168.182.128/group1/M00/00/00/wKi2gGBas4aAEAdlACoeC5KngBs554.jpg
            }
        } catch (IOException | MyException e) {
            e.printStackTrace();
        } finally {
            releaseFastDFS();
        }
    }

    /**
     * 文件下载
     * @param groupName       组/卷名，默认值：group1
     * @param remoteFileName  文件名，例如："M00/00/00/wKgKZl9tkTCAJAanAADhaCZ_RF0495.jpg"
     * @param localFileName   本地文件位置  D:/222.jpg
     * @return 0为成功 ，非0为失败
     */
    public static int fileDownload(String groupName, String remoteFileName, String localFileName){
        int count = -1;
        try {
            count = storageClient.download_file(groupName == null ? "group1" : groupName, remoteFileName, localFileName);
            System.out.println("downloadFile : " + count);
        } catch (IOException | MyException e) {
            e.printStackTrace();
        } finally {
            releaseFastDFS();
        }
        return count;
    }

    /**
     * 文件删除
     *
     * @param groupName      组/卷名，默认值：group1
     * @param remoteFileName 文件名，例如："M00/00/00/wKgKZl9tkTCAJAanAADhaCZ_RF0495.jpg"
     * @return 0为成功 true，非0为失败 false
     */
    public static boolean fileDelete(String groupName, String remoteFileName) {
        int delete_file = -1;
        try {
            delete_file = storageClient.delete_file(groupName, remoteFileName);
            System.out.println("deleteCount : " + delete_file);
        } catch (IOException | MyException e) {
            e.printStackTrace();
        } finally {
            releaseFastDFS();
        }
        return delete_file == 0;
    }


    /**
     * 获取元数据
     *
     * @param groupName      组/卷名，默认值：group1
     * @param remoteFileName 文件名，例如："M00/00/00/wKgKZl9tkTCAJAanAADhaCZ_RF0495.jpg"
     * @return 文件的元数据数组
     */
    public static NameValuePair[] getMetaData(String groupName, String remoteFileName) {
        try {
            // 根据组名和文件名通过 Storage 客户端获取文件的元数据数组
            return storageClient.get_metadata(groupName == null ? "group1" : groupName, remoteFileName);
        } catch (IOException | MyException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文件详情
     *
     * @param groupName      组/卷名，默认值：group1
     * @param remoteFileName 文件名，例如："M00/00/00/wKgKZl9tkTCAJAanAADhaCZ_RF0495.jpg"
     * @return 文件详情
     */
    public static FileInfo getFileInfo(String groupName, String remoteFileName) {
        try {
            return storageClient.get_file_info(groupName == null ? "group1" : groupName, remoteFileName);
        } catch (IOException | MyException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取文件后缀名（不带点）
     *
     * @param fileName 文件名称
     * @return 如："jpg" or ""
     */
    private static String getFileExt(String fileName) {
        if (StringUtils.isBlank(fileName) || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1); // 不带最后的点
    }
}