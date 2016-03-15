package com.jlsoft.framework.fileserver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 附件(归档)文件上传
 * 1.0  2014-06-13
 *
 * @author XiongJian
 */
public class AttachmentUploader extends Uploader {

    private final char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public AttachmentUploader(InputStream inputData) {
        super(inputData);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        setDateDir(sdf.format(new Date()));
        setBufSize(1024 * 128); // 缓存区大小
    }

    public AttachmentUploader(byte[] inputData) {
        this(new ByteArrayInputStream(inputData));
    }

    @Override
    public void upload() throws Exception {
        if (null == getInputData()) {
            throw new FileNotFoundException();
        }
        if (null == getFileName() || "".equals(getFileName())) {
            throw new FileNotFoundException();
        }
        // 路径不存在，则递归创建
        File dir = new File(getFileDir());
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new FileNotFoundException();
            }
        }
        // 以固定大小缓存区方式写入流文件，同时计算文件的 md5 哈希值
        String fullName = getFileFullName();
        OutputStream os = new FileOutputStream(fullName);
        MessageDigest hash = MessageDigest.getInstance("MD5");
        byte[] buffer = new byte[getBufSize()];
        long count = 0;
        int n = 0;
        while (-1 != (n = getInputData().read(buffer))) {
            os.write(buffer, 0, n);
            hash.update(buffer, 0, n);
            count += n;
        }
        os.close();
        os = null;
        // 按哈希值作为正式文件名重命名临时文件(不管成功与否，必须清理掉临时文件)
        File tmpFile = new File(fullName);
        if (0 < count) {
            setFileName(toHexString(hash.digest()));
            setFileSize(count);
            File file = new File(getFileFullName());
            if (!file.exists()) {
                dir = new File(getFileDir());
                if (!dir.exists()) {
                    if (!dir.mkdirs()) {
                        throw new FileNotFoundException();
                    }
                }
                tmpFile.renameTo(file);
            } else {
                // 可能存在一个文件内容与 md5 哈希值不匹配的错误文件
                if (file.length() != tmpFile.length()) {
                    file.delete();
                    tmpFile.renameTo(file);
                } else {
                    // 文件重复，直接丢弃已上传的临时文件
                    tmpFile.delete();
                }
            }
        } else {
            // 用户上传了空数据导致生成了 0 字节的临时文件
            setFileName("");
            setFileSize(0);
            tmpFile.delete();
            throw new IOException();
        }
    }

    public String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
            sb.append(hexChar[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    public String getSizeDir() {
        String dir = "";
        long size = getFileSize();
        if (0 <= size && size <= 1024 * 8) {
            dir = "K008";
        } else if (1024 * 8 < size && size <= 1024 * 16) {
            dir = "K016";
        } else if (1024 * 16 < size && size <= 1024 * 32) {
            dir = "K032";
        } else if (1024 * 32 < size && size <= 1024 * 64) {
            dir = "K064";
        } else if (1024 * 64 < size && size <= 1024 * 128) {
            dir = "K128";
        } else if (1024 * 128 < size && size <= 1024 * 256) {
            dir = "K256";
        } else if (1024 * 256 < size && size <= 1024 * 512) {
            dir = "K512";
        } else if (1024 * 512 < size && size <= 1024 * 1024) {
            dir = "M001";
        } else if (1024 * 1024 < size && size <= 1024 * 1024 * 8) {
            dir = "M008";
        } else if (1024 * 1024 * 8 < size && size <= 1024 * 1024 * 16) {
            dir = "M016";
        } else if (1024 * 1024 * 16 < size && size <= 1024 * 1024 * 32) {
            dir = "M032";
        } else if (1024 * 1024 * 32 < size && size <= 1024 * 1024 * 64) {
            dir = "M064";
        } else if (1024 * 1024 * 64 < size && size <= 1024 * 1024 * 128) {
            dir = "M128";
        } else if (1024 * 1024 * 128 < size && size <= 1024 * 1024 * 256) {
            dir = "M256";
        } else if (1024 * 1024 * 256 < size && size <= 1024 * 1024 * 512) {
            dir = "M512";
        } else if (1024 * 1024 * 512 < size && size <= 1024 * 1024 * 1024) {
            dir = "G001";
        } else if (1024 * 1024 * 1024 < size && size <= 1024 * 1024 * 1024 * 2) {
            dir = "G002";
        } else if (1024 * 1024 * 1024 * 2 < size && size <= 1024 * 1024 * 1024 * 3) {
            dir = "G003";
        } else if (1024 * 1024 * 1024 * 3 < size && size <= 1024 * 1024 * 1024 * 4) {
            dir = "G004";
        } else {
            dir = "T001";
        }
        return dir;
    }

    @Override
    public String getFileDir() {
        if (null != super.getFileDir() && !"".equals(super.getFileDir())) {
            return super.getFileDir() + File.separator + getSizeDir();
        } else {
            return getSizeDir();
        }
    }
}
