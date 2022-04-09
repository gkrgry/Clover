package com.daelim.clover.user.service;


import com.daelim.clover.user.domain.User;
import com.daelim.clover.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserMapper userMapper;

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

        if(user == null){
            throw new UsernameNotFoundException("User not authorized.");
        }

        log.info(user);
        return user;
    }
}
