package com.daelim.clover.user.controller;


import com.daelim.clover.user.domain.User;
import com.daelim.clover.user.service.UserService;
import com.daelim.clover.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Log4j2
@Controller
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    UserServiceImpl service;

    User user;


    @PostMapping("/update_popup")
    @ResponseBody
    public String userUpdate(HttpServletRequest request,User user) throws Exception{
//        log.info("유저 업뎃");
        log.info(user.getNickname());
//        log.info(name);
        HttpSession session = request.getSession();


        String pwd = (String) session.getAttribute("pwd");
        String userId = (String) session.getAttribute("sUserId");


        user.setUserId(userId);
        if(pwd==user.getPwd()){
            user.setPwd(pwd);
            return "faild";
        }else{
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPwd(passwordEncoder.encode(user.getPassword()));
            System.out.println(user.getPwd());
        }

//       System.out.println(pwd);

       // System.out.println(this.user.getNickname()+"현재닉네임");
       // System.out.println(user.getNickname()+"바꿀 닉네임");
        userService.userUpdate(user);

        return "success";
    }

    @PostMapping("/mypage")
    public String userPage() throws  Exception{

        //session.setAttribute("sUserId",userId);

        return "mainpage";
    }

    @GetMapping("/mypage")
    public String myPage(HttpServletRequest request)throws Exception{
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("sUserId");
//        String Pwd = (String) session.getAttribute("pwd");
        System.out.println("저장된변수"+userId);
      user=userService.myPage(userId);

        String userName=user.getName();
        String userNickname=user.getNickname();
        String userPwd = user.getPwd();
        String email = user.getEmail();
        session.setAttribute("userName",userName);
        session.setAttribute("userNickname",userNickname);
        session.setAttribute("userPwd",userPwd);
        session.setAttribute("userEmail",email);
//        System.out.println(email);
//        System.out.println(userPwd);
//        System.out.println(userName);
//        System.out.println(userNickname);

        return "mypage";

    }


    @PostMapping("/mailchk")
    @ResponseBody
    public String emailchk(String email)throws Exception{
        int result =0;
        result = userService.emailCheck(email);
        System.out.println(result+"이메일 0-1");
        if(result == 0) {
            return "success"; //생성가능한 이메일
        }else  return "fail";

        }

    //이메일 인증키 발행
    @PostMapping("/mail")
    @ResponseBody
    public String emailConfirm(String email)throws Exception{
            log.info(email);
//            System.out.println("전달받은 이메일"+email);

                userService.sendSimpleMessage(email);
//        System.out.println("전달받은 코드"+service.ePw);
           return service.ePw;
    }



    @PostMapping("/sign")
    public String userSign(User user, Model model) throws  Exception{
        log.info("DB데이터 전송");
        userService.userSingUp(user);
        model.addAttribute("msg","가입 성공하셨습니다.");
        return "mainpage";
    }


    @GetMapping("/sign")
    public String userSignForm() throws Exception{
        log.info("회원가입 페이지");

        return "sign";
    }



    @GetMapping("/login")
    public String userLogin() throws Exception{
        log.info("로그인 페이지");

        return "login";
    }
    @GetMapping("/access_denied")
    public  String accessDenied(){
        log.info("로그인 실패페이지");
        return "access_denied";
    }
    @GetMapping("/mainpage")
    public String userAccess(Model model, HttpServletRequest request, Authentication authentication){

        log.info("로그인 성공!!");
        //Authentication객체를 통해 유저 정보를 가져올 수 있다.
        User user = (User)  authentication.getPrincipal(); //userDetail 객체를 가져옴
        HttpSession session= request.getSession();
        String sUserId = user.getUserId();
        String pwd = user.getPwd();
        log.info(""+pwd);
        log.info(""+sUserId);
        session.setAttribute("pwd",pwd);
        session.setAttribute("sUserId",sUserId);
        model.addAttribute("info", user.getUserId()+"의 "+user.getName());//유저 아이디

        return  "mainpage";
    }


    //아이디 중복 검사
    @RequestMapping(value ="/memberIdChk",method = RequestMethod.POST)
    @ResponseBody
    public String memberIdChkPOST(String userId) throws Exception{
//        log.info("memberIdChk() 진입");
            log.info(userId);
        if(!StringUtils.isEmpty(userId)) {
            int result = userService.idCheck(userId);
            log.info("결과값" + result);
            if (result == 0) {
                return "success"; //사용가능한 아이디 
            } else if (result==1){
                return "fail"; //중복된 아이드 존재
            }
        }
        log.info("테스트");
        return null;
    }
}
