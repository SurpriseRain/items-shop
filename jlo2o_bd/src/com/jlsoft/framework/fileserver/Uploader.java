/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jlsoft.framework.fileserver;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

/**
 * 文件上传抽象类
 * 1.0  2014-06-13
 *
 * @author XiongJian
 */
public abstract class Uploader {

    private InputStream inputData = null;
    private String hostName = null;
    private String homeDir = null;
    private String extraDir = null;
    private String dateDir = null;
    private String fileName = null;
    private long fileSize = 0;
    private int bufSize = 1024; // 默认缓存

    public Uploader(InputStream inputData) {
        this.inputData = inputData;
    }

    public abstract void upload() throws Exception;

    /**
     * @return the inputData
     */
    public InputStream getInputData() {
        return inputData;
    }

    /**
     * @param inputData the inputData to set
     */
    public void setInputData(InputStream inputData) {
        this.inputData = inputData;
    }

    /**
     * @return the hostName
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * @param hostName the hostName to set
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * @return the homeDir
     */
    public String getHomeDir() {
        return homeDir;
    }

    /**
     * @param homeDir the homeDir to set
     */
    public void setHomeDir(String homeDir) {
        if (null != homeDir && !"".equals(homeDir)) {
            if ("\\".equals(File.separator)) { // for Windows OS
                // 虽然Windows目录也可以使用UNIX系统的"/"分隔符，
                // 但为了更好的兼容Windows系统中的其他程序，还是替换成"\"分隔符
                homeDir = homeDir.replace('/', '\\');
            }
        }
        this.homeDir = homeDir;
    }

    /**
     * @return the extraDir
     */
    public String getExtraDir() {
        return extraDir;
    }

    /**
     * @param extraDir the extraDir to set
     */
    public void setExtraDir(String extraDir) {
        if (null != extraDir && !"".equals(extraDir)) {
            if ("\\".equals(File.separator)) { // for Windows OS
                extraDir = extraDir.replace('/', '\\');
            }
        }
        this.extraDir = extraDir;
    }

    /**
     * @return the dateDir
     */
    public String getDateDir() {
        return dateDir;
    }

    /**
     * @param dateDir the dateDir to set
     */
    public void setDateDir(String dateDir) {
        if (null != dateDir && !"".equals(dateDir)) {
            if ("\\".equals(File.separator)) { // for Windows OS
                dateDir = dateDir.replace('/', '\\');
            }
        }
        this.dateDir = dateDir;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        if (null != fileName && !"".equals(fileName)) {
            if ("\\".equals(File.separator)) { // for Windows OS
                fileName = fileName.replace('/', '\\');
            }
        }
        this.fileName = fileName;
    }

    /**
     * @return the fileSize
     */
    public long getFileSize() {
        return fileSize;
    }

    /**
     * @param fileSize the fileSize to set
     */
    public void setFileSize(long fileSize) {
        this.fileSize = 0 > fileSize ? 0 : fileSize;
    }

    /**
     * @return the bufSize
     */
    public int getBufSize() {
        return bufSize;
    }

    /**
     * @param bufSize the bufSize to set
     */
    public void setBufSize(int bufSize) {
        this.bufSize = 1024 > bufSize ? 1024 : bufSize;
    }

    /**
     * @return the file full directory
     */
    public String getFileDir() {
        boolean hasDir = false;
        String fileDir = null;
        if (null != getHomeDir() && !"".equals(getHomeDir())) {
            fileDir = getHomeDir();
            hasDir = true;
        }
        if (null != getExtraDir() && !"".equals(getExtraDir())) {
            if (hasDir) {
                fileDir += File.separator + getExtraDir();
            } else {
                fileDir = getExtraDir();
                hasDir = true;
            }
        }
        if (null != getDateDir() && !"".equals(getDateDir())) {
            if (hasDir) {
                fileDir += File.separator + getDateDir();
            } else {
                fileDir = getDateDir();
            }
        }
        return fileDir;
    }

    /**
     * @return the file full name
     */
    public String getFileFullName() {
        if (null != getFileName() && !"".equals(getFileName())) {
            if (null != getFileDir() && !"".equals(getFileDir())) {
                return getFileDir() + File.separator + getFileName();
            } else {
                return getFileName();
            }
        } else {
            return null;
        }
    }

    public void setRandomUniqueFileName() {
        setFileName(UUID.randomUUID().toString().replaceAll("-", ""));
        File file = new File(getFileFullName());
        while (file.exists()) {
            setFileName(UUID.randomUUID().toString().replaceAll("-", ""));
            file = new File(getFileFullName());
        }
    }
}
