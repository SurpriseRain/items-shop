package com.jlsoft.framework.fileserver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 临时文件上传
 * 1.0  2014-06-16
 *
 * @author XiongJian
 */
public class TemporaryFileUploader extends Uploader {

    public TemporaryFileUploader(InputStream inputData, boolean archivesByDate) {
        super(inputData);
        if (archivesByDate) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            setDateDir(sdf.format(new Date()));
        } else {
            setDateDir(null);
        }
        setBufSize(1024 * 32); // 默认缓存区大小
    }

    public TemporaryFileUploader(InputStream inputData) {
        this(inputData, true);
    }

    public TemporaryFileUploader(byte[] inputData, boolean archivesByDate) {
        this(new ByteArrayInputStream(inputData), archivesByDate);
    }

    public TemporaryFileUploader(byte[] inputData) {
        this(new ByteArrayInputStream(inputData), true);
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
        // 以固定大小缓存区方式写入流文件
        String fullName = getFileFullName();
        OutputStream os = new FileOutputStream(fullName);
        byte[] buffer = new byte[getBufSize()];
        long count = 0;
        int n = 0;
        while (-1 != (n = getInputData().read(buffer))) {
            os.write(buffer, 0, n);
            count += n;
        }
        os.close();
        os = null;
        if (0 < count) {
            setFileSize(count);
        } else {
            // 用户上传了空数据导致生成了 0 字节的文件
            setFileName("");
            setFileSize(0);
            File file = new File(fullName);
            file.delete();
            throw new IOException();
        }
    }
}
