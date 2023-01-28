package com.ansel.controller.admin;

import com.ansel.pojo.Post;
import com.ansel.pojo.User;
import com.ansel.service.PostService;
import com.ansel.service.TypeService;
import com.ansel.service.UserService;

import com.ansel.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Ansel Zhong
 * @title blog
 * @Date 2023/1/23
 * @Description logincontroller
 */
@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;


    @RequestMapping("/ad/toAdmin")
    public String admin(){
        System.out.println("enter"+"toAdmin");
        return "admin/admin";
    }

    @RequestMapping("ad/toLogin")
    public String loginPage(){
        return "admin/login";
    }

//    @PostMapping("ad/login")
//    public String login(@RequestParam String username, @RequestParam String password, HttpSession session,
//                        RedirectAttributes attributes, Model model) {
//
//        System.out.println("enter:" +"doLogin");
//        //加密
//        String passwordFinal = MD5Utils.pass2(password);
//        System.out.println(passwordFinal);
//        User user = userService.checkUser(username, passwordFinal);
//        System.out.println("login--->"+"finish finding user");
//        System.out.println(user);
////        if (user != null){
////        //    user.setPassword(null);
////            session.setAttribute("user",user);
////            System.out.println("login--->"+"to index");
////            return "admin/index";
////        }else {
////            model.addAttribute("msg","Sorry the user name or the password is incorrect");
////            System.out.println("login--->"+"to login");
////            return "admin/login";
////        }
//       return "admin/index";
//    }
    @RequestMapping("ad/login")
    public String login(String username, String password, Model model, HttpSession session){
        String passwordFinal = MD5Utils.pass2(password);
        User user = userService.checkUser(username, passwordFinal);
        if (user != null) {
            if (session.getAttribute("user") != null) {
                session.removeAttribute("user");
            }
            session.setAttribute("user",user);
            System.out.println("user.getNickname() :" + user.getNickname());
            System.out.println("user :" + user);
            model.addAttribute("user",user);
            List<Post> posts1 = postService.listPostTop(6);
            System.out.println("posts-->" + posts1);
            session.setAttribute("postBottom",posts1);
            return "admin/index";
        }else {
            model.addAttribute("msg","Sorry the user name or the password is incorrect");
            return "admin/login";
        }
    }


    @RequestMapping ("ad/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "admin/login";
    }

    @RequestMapping ("ad/register")
    @ResponseBody
    public String register() {
        User user = new User();
        user.setNickname("kkasdasd");
        userService.saveUser(user);
        return "OK";
    }
    @RequestMapping("/ad/index")
    public String index2(HttpSession session, Model model){

        return "admin/index";
    }

    @RequestMapping("/ad/setting")
    public String setting(HttpSession session, Model model){
        User user =(User) session.getAttribute("user");
        if (user == null) {
            return "admin/login";
        }
        model.addAttribute("user",user);
        return "admin/setting";
    }

}
