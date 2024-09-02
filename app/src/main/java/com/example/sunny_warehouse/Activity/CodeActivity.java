package com.example.sunny_warehouse.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sunny_warehouse.R;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class CodeActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        btn = findViewById(R.id.button3);






        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tele();
            }
        });




    }

    public void tele() {

        //EditText number=(EditText)findViewById(R.id.number);
        //EditText message=(EditText)findViewById(R.id.message);

        String mail = "djangovih@mail.ru";
        String pass= "Aqm5XktUFGPwp7ydUEF1";

        String code = "123456gg";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.mail.ru");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactWory.class", "javax.net.ssl.SSLSocketFactory");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mail, pass);
            }
        });
        try {
            final Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("saldaykin1@gmail.com"));
            message.setText(code);
            Thread thr = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(message);
                    } catch (MessagingException e) {
                        Log.e("errmes", e.getMessage());
                    }
                }
            });
            thr.start();

        }catch (MessagingException ex){
            Log.e("errmes", ex.getMessage());
        }


    }


}