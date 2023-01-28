package com.ansel.controller.register;

import com.ansel.pojo.User;
import com.ansel.service.UserService;
import com.ansel.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author Ansel Zhong
 * coding time
 */
@Controller
@RequestMapping("/register")
public class RegisterController {
  @Autowired
  UserService userService;

  @Value("${user.avatar}")
  String avatarDefault;

  @RequestMapping("/toRegisterPage")
  public String about(){
    return "admin/register";
  }
  @RequestMapping("/verifyUsername")
  @ResponseBody
  public String verifyUsername(String username){
    String msg;
    User name = userService.checkUserByUsername(username);

    if (name != null) {
      msg = "User name already exits";

    }else if (username == null) {
      msg = "User name cannot be null";
    }
    else if (username.length() > 20 || username.length() < 4) {
      msg = "wrong username";
      System.out.println(msg);
    }else {
      msg = "OK";
    }

    return msg;
  }
  @RequestMapping("/verifyPassword")
  @ResponseBody
  public String verifyPassword(String password){
    String msg;
    String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
    if (password == null) {
      msg = "Message cannot be null";
    }else if (password.matches(regex)) {
      msg = "OK";
    }else {
      msg = "Password must be within 6-16 and include both numbers and letters";
    }
    return msg;
  }

  @RequestMapping("/verifyPassword2")
  @ResponseBody
  public String verifyPassword2(String password, String password2){
    String msg;
    if (password == null) {
      msg = "Message cannot be null";
    }else if (password2.equals(password)) {
      msg = "OK";
    }else {
      msg = "The second password doesn't match the first one";
    }
    return msg;
  }

  @RequestMapping("/doRegister")
  public String doRegisterPage(String username, String password2,
                               String email, HttpSession session){
    System.out.println("username:" + username);
    System.out.println("password:" + password2);
    System.out.println("email:"+email);


//        insert a user
    User user = new User();
    //加密
    String passwordFinal = MD5Utils.pass2(password2);
    System.out.println(passwordFinal);
    user.setUsername(username);
    user.setPassword(passwordFinal);
    user.setEmail(email);
    user.setNickname(username);
    user.setAvatar(avatarDefault);
    userService.saveUser(user);
    session.setAttribute("username",user.getUsername());
    return "admin/login";
  }

  @RequestMapping("/update")
  public String update(String avatar,String password2,
                               String nickname, HttpSession session){

    System.out.println("password:" + password2);
    System.out.println("avatar:"+avatar);
    System.out.println("nickname:"+nickname);
    User user =(User) session.getAttribute("user");
    if (user == null) {
      return "admin/login";
    }
    //加密
    String passwordFinal = MD5Utils.pass2(password2);
    System.out.println(passwordFinal);
    user.setPassword(passwordFinal);
    user.setNickname(nickname);
    user.setAvatar(avatar);
    userService.saveUser(user);
    session.setAttribute("username",user.getUsername());
    return "admin/login";
  }
}
