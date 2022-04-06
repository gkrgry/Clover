package com.daelim.clover.user.controller;


import com.daelim.clover.user.domain.User;
import com.daelim.clover.user.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/" )
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/sign")
    public String userSignForm() throws Exception{
        log.info("회원가입 페이지");

        return "sign";
    }
    @PostMapping("/sign")
    public String userSign(User user, Model model) throws  Exception{
        log.info("DB데이터 전송");
        userService.userSingUp(user);
        model.addAttribute("msg","가입 성공하셨습니다.");
        return "login";
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
    @GetMapping("/user_access")
    public String userAccess(Model model, Authentication authentication){
        log.info("로그인 성공!!");
        //Authentication객체를 통해 유저 정보를 가져올 수 있다.
        User user = (User)  authentication.getPrincipal(); //userDetail 객체를 가져옴
        model.addAttribute("info", user.getUserId()+"의 "+user.getName());//유저 아이디
        return  "user_access";
    }
}
