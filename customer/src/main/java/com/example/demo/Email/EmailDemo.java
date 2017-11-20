package com.example.demo.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by GYJ on 2017-09-21.
 */
@RestController
@RequestMapping("/email")
public class EmailDemo  {
    @Autowired
    private JavaMailSender mailSender;



    public EmailDemo(){
        JavaMailSenderImpl senderImpl  =   new  JavaMailSenderImpl();
        // 设定mail server
        senderImpl.setHost( "smtp.qq.com" );

        // 建立邮件消息
        SimpleMailMessage mailMessage  =   new  SimpleMailMessage();
        // 设置收件人，寄件人 用数组发送多个邮件
        // String[] array = new String[]    {"sun111@163.com","sun222@sohu.com"};
        // mailMessage.setTo(array);
        mailMessage.setTo( "gyjnuaa@126.com" );
        mailMessage.setFrom( "gaoyanjing@youbangsoft.com" );
        mailMessage.setSubject( " 测试简单文本邮件发送！ " );
        mailMessage.setText( " 测试我的简单邮件发送机制！！ " );

        senderImpl.setUsername( "gaoyanjing@youbangsoft.com" ) ;  //  根据自己的情况,设置username
        senderImpl.setPassword( "" ) ;  //  根据自己的情况, 设置password

    }
    @RequestMapping(value = "/sendMessage",method = RequestMethod.GET)
    public void sendSimpleMail() throws Exception {
        JavaMailSenderImpl senderImpl  =   new  JavaMailSenderImpl();
        // 设定mail server
        senderImpl.setHost( "smtp.qq.com" );

        // 建立邮件消息
       // SimpleMailMessage mailMessage  =   new  SimpleMailMessage();
        MimeMessage mailMessage = senderImpl.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true,"utf-8");
        // 设置收件人，寄件人 用数组发送多个邮件
        // String[] array = new String[]    {"sun111@163.com","sun222@sohu.com"};
        // mailMessage.setTo(array);
        messageHelper.setTo( "gyjnuaa@126.com" );
        messageHelper.setFrom( "gaoyanjing@youbangsoft.com" );
        messageHelper.setSubject( " 测试简单文本邮件发送！ " );
        messageHelper.setText( " 测试我的简单邮件发送机制！！ " );

        senderImpl.setUsername( "gaoyanjing@youbangsoft.com" ) ;  //  根据自己的情况,设置username
        senderImpl.setPassword( "" ) ;  //  根据自己的情况, 设置password
        messageHelper.setText("<html><head></head><body><h1>hello!!spring html Mail</h1></body></html>",true);
        /*Properties prop  =   new Properties() ;
        prop.put( "mail.smtp.auth" ,  "true" ) ;  //  将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
        prop.put( "mail.smtp.timeout" ,  "25000" ) ;
        senderImpl.setJavaMailProperties(prop);*/
        // 发送邮件
        senderImpl.send(mailMessage);
        /*SimpleMailMessage message = new SimpleMailMessage();
        message.
        message.setFrom("gyjnuaa@126.com");
        message.setTo("xxxx@qq.com");
        message.setSubject("主题：简单邮件");
        message.setText("简单邮件内容");
        mailSender.send(message);*/
    }
    @RequestMapping(value = "/sendAttachment",method = RequestMethod.POST)
    public void sendAttachment(HttpServletRequest request) throws Exception{
        JavaMailSenderImpl senderImpl  =   new  JavaMailSenderImpl();
        // 设定mail server
        senderImpl.setHost( "smtp.126.com" );

        // 建立邮件消息
        MimeMessage mailMessage = senderImpl.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true,"utf-8");

        // 设置收件人，寄件人 用数组发送多个邮件
        // String[] array = new String[]    {"sun111@163.com","sun222@sohu.com"};
        // mailMessage.setTo(array);
        messageHelper.setFrom( "gyjnuaa@126.com" );
        messageHelper.setTo( "gaoyanjing@youbangsoft.com" );
        messageHelper.setSubject( " 测试附件邮件发送！ " );
        messageHelper.setText( " 测试我的附件邮件发送机制！！ " );

        senderImpl.setUsername( "gyjnuaa@126.com" ) ;  //  根据自己的情况,设置username
        senderImpl.setPassword( "" ) ;  //  根据自己的情况, 设置password

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        /*while (multipartRequest.getFileNames().hasNext()){
            MultipartFile file = multipartRequest.getFile( multipartRequest.getFileNames().next());
            multipartRequest.getFileNames().remove();
            messageHelper.addAttachment(file.getName(), file);
        }*/
        if(multipartRequest.getFileNames().hasNext()){
            MultipartFile file = multipartRequest.getFile( multipartRequest.getFileNames().next());
            // FileSystemResource file1 = new FileSystemResource(file);
            // FileSystemResource file2 = new FileSystemResource(new File("C:\\Users\\Administrator\\Desktop\\Think in Java.pdf"));
            messageHelper.addAttachment(file.getName(), file);
        }

        //helper.addAttachment("附件-2.pdf", file2);
        senderImpl.send(mailMessage);
    }
}
