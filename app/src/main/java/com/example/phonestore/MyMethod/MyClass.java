package com.example.phonestore.MyMethod;

import android.annotation.SuppressLint;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MyClass {
    public static long createId()
    {
        return (long) new Date().getTime();
    }
    public static String FormatMoney(long money) // định dạnh tiền tệ
    {
        Locale locale = new Locale("vi","VN");
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        return  numberFormat.format(money)+"đ";
    }
    public static boolean checkInfo(String ...s)
    {
        for(String info : s)
        {
            if(info != null)
            {
                if(info.isEmpty())
                    return false;
            }
            else
            {
                return false;
            }
        }
        return true;
    }
    public static String VerificationEmail(String email) // gửi code về email
    {
        Random random = new Random();
        String code = random.nextInt(899999)+100000+"";
        String EmailSender = "trinhviethoang307@gmail.com";
        String password = "obonlgagrjadybvb", host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.port","587");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.auth","true");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EmailSender,password);
            }
        });
        Message mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.addRecipients(Message.RecipientType.TO,  InternetAddress.parse(email));
            mimeMessage.setFrom(new InternetAddress(email));
            mimeMessage.setSubject("Mã code xác thực tài khoản của bạn được gửi từ PhoneStore");
            mimeMessage.setText("Mã xác thực: "+code);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException ignored) {


                    }
                }
            });
            thread.start();
        } catch (MessagingException ignored) {

        }
        return code;

    }
    public static String createStrId()
    {
        Date currentDate = new Date();

        // In thời gian hiện tại theo định dạng tùy chỉnh
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
        return formatter.format(currentDate);
    }


}
