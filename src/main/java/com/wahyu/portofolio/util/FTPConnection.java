package com.wahyu.portofolio.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

public class FTPConnection {

    private static Logger logger = LoggerFactory.getLogger(FTPConnection.class);

    public static void sendFileToFTP (String server, int port, String user, String pass, String ftppath, String filename, String filepath) {

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            File localfile = new File(filepath + filename);

            String remotefile = ftppath + filename;
            InputStream inputStream = new FileInputStream(localfile);
            logger.info("UPLOADING FILE FROM: {}", filepath + filename);
            boolean done = ftpClient.storeFile(remotefile, inputStream);
            inputStream.close();
            if (done) {
                logger.info("SUCCESS UPLOAD FILE TO FTP: {}", remotefile);
            } else {
                logger.error("UPLOAD FILE FAILED!!");
            }
        } catch (SocketException e) {
            logger.error("FTP Connection Error: {} " , e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("Error while uploading file to FTP: {} " , e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
                logger.info("FTP Disconnected");
            } catch (IOException e) {
                logger.error("Error while disconnecting FTP connection: {} " , e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
