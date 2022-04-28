package com.daelim.clover.user.service;

//이메일
import java.util.Random;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;


import org.springframework.beans.factory.annotation.Autowired;
import com.daelim.clover.user.domain.User;
import com.daelim.clover.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    JavaMailSender emailSender;


    public   static  final  String ePw = createKey();

    @Value("${AdminMail.id}")
    private String id;
    @Value("${AdminMail.password}")
    private String password;


    public User myPage(String userId) throws Exception{

        User user=userMapper.selectionUser(userId);

        return user;
    }



    private MimeMessage createMessage(String to)throws  Exception{
//        System.out.println("보내는 대상"+id);
//        System.out.println("보내는 대상"+password);
//        System.out.println("보내는 대상 :"+to);
//        System.out.println("인증 번호 :"+ePw);

       MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO,to);//보내는 대상
        message.setSubject("Clover 이메일 인증");//제목

        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> 안녕하세요 Clover 입니다.</h>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>회원가입 인증 코드입니다,</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= ePw+"</strong><div><br/>";
        msgg+= "</div>";
        message.setText(msgg,"utf-8","html");//내용
        message.setFrom(new InternetAddress(id,"Clover"));

        return  message;
    }

    public  static String createKey(){
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i<8;i++){//인증코드 8자리
            int index = rnd.nextInt(3);//0~2까지 랜덤

            switch (index){
                case 0:
                    key.append((char) ((int)(rnd.nextInt(26))+97));
                    // a~z (ex.1+97=98=>(char)98='b');
                    break;
                case 1:
                    key.append((char) ((int)(rnd.nextInt(26))+65));
                    // A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    //0~9
                    break;
            }
        }
        return key.toString();
    }

    @Override
    public String sendSimpleMessage(String to)throws Exception {
        //TODO Auto-generated method stub

            MimeMessage message = createMessage(to);
            try {//예외처리
                emailSender.send(message);
            } catch (MailException ex) {
                ex.printStackTrace();
                throw new IllegalArgumentException();
            }
//            System.out.println(ePw);
            return ePw;

    }

   @Transactional
    public void  userSingUp(User user) {

       BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
       user.setPwd(passwordEncoder.encode(user.getPassword()));

       userMapper.saveUser(user);
    }



    @Override
    public User loadUserByUsername(String userId) throws UsernameNotFoundException {
        //여기서 받은 유저 패스워드와 비교하여 로그인 인증

         User user = userMapper.getUserAccount(userId);
        log.info(user);
        if(user == null||user.getPassword()==null){
            throw new UsernameNotFoundException("User not authorized.");
        }

        log.info(user);
        return user;
    }




    //이메일 중복확인
    @Override
    public int emailCheck(String email) throws Exception{
        log.info(userMapper.emailCheck(email));
        return userMapper.emailCheck(email);
    }

    /* 유저 중복확인*/
    @Override
    public int idCheck(String userId) throws Exception{
       return userMapper.idCheck(userId);
    }
}
