package com.daelim.clover.user.service;

//이메일

import com.daelim.clover.user.domain.User;
import com.daelim.clover.user.kakao.KakaDTO;
import com.daelim.clover.user.kakao.UserRepository;
import com.daelim.clover.user.mapper.UserMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Random;


@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    JavaMailSender emailSender;

    public   static   String ePw = createKey();

    @Value("${AdminMail.id}")
    private String id;
    @Value("${AdminMail.password}")
    private String password;
    @Value("${profileImg.path}")
    private String uploadFolder;

    // 클래스 주입
    @Autowired
    private UserRepository ur;

    //카카오 유저 정보
    public KakaDTO getUserInfo(String access_Token,User user){

        //요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");


            conn.setRequestProperty("Authorization","Bearer "+access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode :" +responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line ="";
            String result = "";

            while ((line = br.readLine()) != null){
                result +=line;
            }
            System.out.println("response body 첫번째 :" + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            System.out.println(element);
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account =element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String nickname =properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();
            String gender = kakao_account.getAsJsonObject().get("gender").getAsString();
            userInfo.put("nickname",nickname);
            userInfo.put("email",email);
            userInfo.put("gender",gender);
        }catch (Exception e){
            e.printStackTrace();
        }
        KakaDTO result = ur.findkakao(userInfo,user);
        // 위 코드로 정보가 저장되었는 확인 하는 콛,

        System.out.println("S :"+result);
        if(result.getK_name()==null&&result.getK_gender()==null && result.getK_email()==null){
            //result가 null 이면 정보가 저장이 안되있느거므로 정보를 저장.
            System.out.println("ssssssssssss");
            ur.kakaoinsert(userInfo,user);
            System.out.println("정보없는거 확인후 사입");
            return ur.findkakao(userInfo,user);
            //정보 저장후 컨트롤러에 정보를 보내는코드
        }else{
            return result;
            //정보가 이미 있는경우
        }
    }


    //카카오 유저 토큰
    public String getAccessToken(String authorize_code){
        String access_Token ="";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");

            sb.append("&client_id=774f346686b648d662cb1d75ccbd5cd1"); //본인 발급받은 key
            sb.append("&redirect_url=http://localhost:8080/kakaologin"); //본인 설정한 주소

            sb.append("&code="+authorize_code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode :"+responseCode);

            //요청을 통해 얻은 JSON 타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line ="";
            String result = "";

            while ((line = br.readLine())!=null){
                result +=line;
            }
            System.out.println("response body 두번쨰 :"+result);

            // Gson 라이브러리에 포함된 클래스로 JSON 파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();


            System.out.println("access_token :"+access_Token);
            System.out.println("refresh_token :"+refresh_Token);

            br.close();
            bw.close();



        }catch (Exception e){
            e.printStackTrace();
        }

        return access_Token;
    }

    //유저 프로필 설정
    @Transactional
    public String uploadFile (String userId, MultipartFile multipartFile)throws Exception{
    User user= userMapper.selectionUser(userId);
    System.out.println("파일 살제");
    System.out.println(multipartFile);
        System.out.println(multipartFile.getOriginalFilename());
    String imageFileName = userId+"_"+multipartFile.getOriginalFilename();
        System.out.println("파일 살제222");
    Path imageFilePath = Paths.get(uploadFolder+imageFileName);
        System.out.println("파일 살제333");
        System.out.println(user.getImage());
        System.out.println(user.getImage() !=null);

    if(multipartFile.getSize()!=0){//파일이 업로드 되었는지 확인
        try{
            if(user.getImage() !=null){
                System.out.println("파일 삭제");
                File file = new File(uploadFolder+ user.getImage());
                System.out.println(file);
                System.out.println(file.exists());
                System.out.println(file.delete());
                System.gc();
                file.delete();//원래파일 삭제
            }

            Files.write(imageFilePath,multipartFile.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(imageFileName);
        user.setImage(imageFileName);
        userMapper.ImageUpdate(userId,imageFileName);
        return "success";
    }


        return "failed";
    }



    //유저 비번찾기
    public int searchUser(String email,String userId)throws Exception{
        int result=0;
        System.out.println(result+"전");
        result=userMapper.SearchPwd(email,userId);
        System.out.println(result+"후");
        return result;
    }

    //유저 아이디 찾기
    public String searchUser(String email)throws Exception{

        System.out.println(userMapper.SearchUser(email)+"이메일로 아이디찾기");
        return userMapper.SearchUser(email);
    }

    // 유저 삭제
    public void userDrop(String userId)throws Exception{
        System.out.println(userId);
        userMapper.UserDrop(userId);
    }

    //닉네임 수정
    public int userUpdate(User user) throws Exception{

        System.out.println(user.getNickname()+"테스트");
        System.out.println(user.getPwd()+"테스트2");
        userMapper.UpdateUser(user);
        return 0;
    }

    //내정보 페이지
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
        ePw=createKey();
        message.addRecipients(MimeMessage.RecipientType.TO,to);//보내는 대상
        message.setSubject("Clover 이메일 인증");//제목

        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> 안녕하세요 Clover 입니다.</h>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>Clover 인증 코드입니다,</h3>";
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
