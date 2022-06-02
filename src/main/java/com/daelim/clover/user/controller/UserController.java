package com.daelim.clover.user.controller;


import com.daelim.clover.board.domain.Board;
import com.daelim.clover.board.domain.Criteria;
import com.daelim.clover.board.domain.PageDTO;
import com.daelim.clover.board.service.BoardService;
import com.daelim.clover.comment.service.CommentService;
import com.daelim.clover.user.domain.User;
import com.daelim.clover.user.kakao.KakaDTO;
import com.daelim.clover.user.mapper.UserMapper;
import com.daelim.clover.user.service.UserService;
import com.daelim.clover.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;


@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class UserController {


    @Autowired
    private final UserService userService;
    private final BoardService boardService;
    private final CommentService commentService;
    UserServiceImpl service;

    User user;

    // HttpSession 클래스 주입.
    @Autowired
    private HttpSession session;


    @Autowired
    UserMapper userMapper;


    @GetMapping(value = "/upload/{filename}",produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]>showImage(@PathVariable String filename) throws Exception{
        log.info(filename);
        if(filename.toString().equals("") || filename == null){
            filename = "NotImg";
        }
        log.info(filename);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<byte[]>(IOUtils.toByteArray(new FileInputStream(
                new File("C:\\uploadImg/"+ filename))), header, HttpStatus.CREATED);
    }//바꿔야 할것

    @PostMapping("imageupload")
    @ResponseBody
    public  String ImageUpLoad(@RequestParam("profileImgUrl") MultipartFile multipartFile,HttpServletRequest request)throws Exception{
    System.out.println("들어는옴" );
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("sUserId");
        log.info(userId);
        user.setUserId(userId);
        String str=userService.uploadFile(userId,multipartFile);
        log.info(str);
        return str;

    }
    @RequestMapping("/searchId")
    public void searchId()  {

    }
    @RequestMapping("/searchPwd")
    public void searchPwd(){

    }
    @PostMapping("/search")
    @ResponseBody
    public String search(String email,String userId)throws Exception{
        String str;
        System.out.println(userId);
        if(userId==""){
            System.out.println("이메일로 아이디찾는중");
            str=userService.searchUser(email);

        }else{
            if(userService.searchUser(email,userId)==0){
                System.out.println("숫자는 0");

            }else{
                System.out.println("숫자는1");
                return str="success";
            }
        }
        return str="";
    }

    @GetMapping("/kakaodrop")
    @ResponseBody
    public String KakaoDrop(HttpServletRequest request)throws  Exception{

        HttpSession session = request.getSession();

        String Token =(String) session.getAttribute("Token");
        String userId = (String) session.getAttribute("sUserId");
        userService.userDrop(userId);
        String result=userService.KakaoDropuser(Token);
        //유저 삭제시 관련 게시글 같이 삭제
        boardService.userAllDelete(userId);
        commentService.userCommentAllDelete(userId);
        //세선 삭제 (로그아웃)
        session.invalidate();
        return result;

    }

    @PostMapping("/dropUser")
    @ResponseBody
    public String userDrop(HttpServletRequest request)throws Exception{
        log.info("유저삭제@@");
        log.info(user.getUserId());

        HttpSession session = request.getSession();


        String userId=user.getUserId();
        userService.userDrop(userId);
        //유저 삭제시 관련 게시글 같이 삭제
        boardService.userAllDelete(userId);
        commentService.userCommentAllDelete(userId);
        //세선 삭제 (로그아웃)
        session.invalidate();
        return "redirect:/main";
    }

    @PostMapping("/update_popup")
    public @ResponseBody String userUpdate(HttpServletRequest request,User user) throws Exception{
//        log.info("유저 업뎃");
        log.info(user.getNickname());
//        log.info(name);
        HttpSession session = request.getSession();



        String userId = (String) session.getAttribute("sUserId");
        int kakao= (int) session.getAttribute("kakao");
      if(kakao==0){
        String pwd = (String) session.getAttribute("pwd");
        System.out.println(pwd);
        System.out.println(user.getName());
        System.out.println(user.getPwd().equals(user.getName()));
        System.out.println(user.getPwd());
        if(user.getPwd().equals(user.getName())){
            user.setPwd(pwd);
        }else{
            if(pwd==user.getPwd()){
                user.setPwd(pwd);
                return "faild";
            }else if(user.getPwd()!=null ){

                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                user.setPwd(passwordEncoder.encode(user.getPassword()));
                System.out.println(user.getPwd()+"바꿔지는 패스워드");
            }else if(userId==null){
                return "notId";
            }
        }

      }
        System.out.println(userId);
        user.setUserId(userId);
        System.out.println(user.getUserId());


        System.out.println(user+"패끝");

       // System.out.println(this.user.getNickname()+"현재닉네임");
       // System.out.println(user.getNickname()+"바꿀 닉네임");
        userService.userUpdate(user);

        return "success";
    }

    @PostMapping("/mypage")
    public String userPage() throws  Exception{

        //session.setAttribute("sUserId",userId);

        return "";
    }

    @GetMapping("/mypage")
    public String myPage(HttpServletRequest request, Model model, Criteria cri)throws Exception{
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("sUserId");
       String Pwd = (String) session.getAttribute("pwd");
       String Token = (String) session.getAttribute("Token");

        System.out.println("저장된변수"+userId);
        System.out.println("저장된변수"+Pwd);
      user=userService.myPage(userId);
      System.out.println("수정내용 분석"+user);
      user.setPwd(Pwd);
        System.out.println(user);
        String userName=user.getName();
        String userNickname=user.getNickname();
        String userPwd = user.getPwd();
        String email = user.getEmail();
        String image;

        if(user.getImage()==null){
            image="defult.png";
        }else{
            image= user.getImage();
        }
        System.out.println(image);






        session.setAttribute("userName",userName);
        session.setAttribute("userNickname",userNickname);
        session.setAttribute("userPwd",userPwd);
        session.setAttribute("userEmail",email);
        session.setAttribute("userImage",image);
        session.setAttribute("phoneNumber",user.getPhone());
        session.setAttribute("kakao",user.getKakao());
        session.setAttribute("Token",Token);

//        System.out.println(userPwd);
//        System.out.println(userName);
//        System.out.println(userNickname);
        List<Board> boardList = boardService.mypageListPaging(cri.getSkip(),cri.getAmount(),userId);
        int total = boardService.mypageGetTotal(userId);
        total = total + 10;
        model.addAttribute("total",total);
        model.addAttribute("boardList", boardList);
        model.addAttribute("pageMaker",new PageDTO(cri,total));
        model.addAttribute("user",user);
        System.out.println(user);

        return "/mypage";

    }


    @PostMapping("/mailchk")
    @ResponseBody
    public String emailchk(String email,boolean bool)throws Exception{
        int result =0;
        if(bool){
            result=0;

        }else{
            result = userService.emailCheck(email);
            System.out.println(result+"이메일 0-1");
        }

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
        return "redirect:main";
    }


    @GetMapping("/sign")
    public String userSignForm() throws Exception{
        log.info("회원가입 페이지");

        return "sign";
    }

    @GetMapping("/kakaologin")
    public String kakaoLogin(@RequestParam(value="code",required = false) String code, User user) throws Exception{
        System.out.println("########"+code);//사용자 코드

        String access_Token = userService.getAccessToken(code);
//        userService.KakaoDropuser(access_Token);
        HashMap<String,Object> userInfo = userService.getUserInfo(access_Token, user);
        KakaDTO kakaDTO= (KakaDTO) userInfo;
        System.out.println(kakaDTO);
        user = userMapper.selectionUser(kakaDTO.getK_email());
        System.out.println("####access_Token#### :"+access_Token);
        System.out.println("####access_Token#### :"+user.getUserId());
        System.out.println("####access_Token#### :"+user.getName());
        System.out.println("####access_Token#### :"+user.getSex());

        // 아래 코드가 추가되는 내용
        session.invalidate();
        // 위 코드는 session객체에 담긴 정보를 초기화 하는 코드.
        session.setAttribute("name", user.getName());
        session.setAttribute("email", user.getEmail());
        session.setAttribute("sUserId",user.getUserId());
        session.setAttribute("userImage",user.getImage());
        session.setAttribute("Token",access_Token);
        session.setAttribute("phoneNumber",user.getPhone());
        session.setAttribute("kakao",user.getKakao());
        // 위 2개의 코드는 닉네임과 이메일을 session객체에 담는 코드
        // jsp에서 ${sessionScope.name} 이런 형식으로 사용할 수 있다.
        return "redirect:/main";
    }

    @GetMapping("/logoutpage")
    public String userLogout(HttpServletRequest request)throws Exception{
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
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
    public void mainpage()throws Exception{

    }
    @GetMapping("/mainpage_In")
    public String userAccess(Model model, HttpServletRequest request, Authentication authentication) throws Exception {

        log.info("로그인 성공!!");
        //Authentication객체를 통해 유저 정보를 가져올 수 있다.
        User user = (User)  authentication.getPrincipal(); //userDetail 객체를 가져옴
        HttpSession session= request.getSession();
        String sUserId = user.getUserId();
        String pwd = user.getPwd();
        String Token = (String) session.getAttribute("Token");
        String result=userService.KakaoDropuser(Token);
        log.info(""+pwd);
        log.info(""+sUserId);
        session.setAttribute("pwd",pwd);
        session.setAttribute("sUserId",sUserId);
        session.setAttribute("kakao",user.getKakao());
        session.setAttribute("phoneNumber",user.getPhone());
        session.setAttribute("userImage",user.getImage());
        model.addAttribute("info", user.getUserId()+"의 "+user.getName());//유저 아이디

        return  "redirect:/main";
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
