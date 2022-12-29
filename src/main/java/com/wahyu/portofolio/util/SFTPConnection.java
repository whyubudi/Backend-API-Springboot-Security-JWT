package com.wahyu.portofolio.util;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SFTPConnection {

    private static Logger logger = LoggerFactory.getLogger(SFTPConnection.class);

    public static void sendFileToSFTP(String server, int port, String user, String pass, String ftppath, String filename, String filepath) {

        JSch sftpClient = new JSch();
        Session session = null;
        String localFile = filepath+filename;
        String remotefile = ftppath+filename;

        try {
            session = sftpClient.getSession(user,server,port);
            session.setConfig("StrictHostKeyChecking","no");
            session.setPassword(pass);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.put(localFile,remotefile);
            sftpChannel.exit();
            logger.info("SUCCESS UPLOAD FILE TO SFTP: {}", remotefile);
        } catch (JSchException e) {
            logger.error("SFTP Connection Error: {} " , e.getMessage());
            e.printStackTrace();
        } catch (SftpException e) {
            logger.error("Error while uploading file to FTP: {} " , e.getMessage());
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.disconnect();
            }
        }
    }
}
