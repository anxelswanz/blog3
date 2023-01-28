package com.ansel.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Ansel Zhong
 * @title blog
 * @Date 2023/1/19
 * @Description 1
 */
@Controller
public class TestController {
    @Autowired

    @RequestMapping("/index")
    public String index(){
        return "index";
    }


    @RequestMapping("/index/category")
    public String category(){
        return "pages/category";
    }
    @RequestMapping("/index/tags")
    public String tags(){
        return "pages/tags";
    }
    @RequestMapping("/index/archives")
    public String archives(){
        return "pages/archives";
    }

    @RequestMapping("/about")
    public String about(){
        return "pages/about";
    }

    @RequestMapping("/types")
    public String types(){
        return "admin/types";
    }
    @RequestMapping("/types2")
    public String types2(){
        return "admin/types-input";
    }
//
//    @RequestMapping("/index/search")
//    public String search(){
//        return "pages/search";
//    }

//    @RequestMapping("/post")
//    public String post(){
//        return "admin/post";
//        }
//    @RequestMapping("/admin/post")
//    public String post(){
//        return "admin/post";
//    }


//    @RequestMapping("/admin/index")
//    public String index2(){
//        return "admin/index";
//    }
//    @RequestMapping("/admin")
//    public String index3(){
//        return "admin/admin";
//    }

        @RequestMapping("/re")
        public String index3(){
        return "admin/register";
    }
}
