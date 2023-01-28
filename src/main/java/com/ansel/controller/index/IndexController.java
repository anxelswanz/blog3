package com.ansel.controller.index;

import com.ansel.pojo.Comment;
import com.ansel.pojo.Post;
import com.ansel.pojo.Tag;
import com.ansel.pojo.Type;
import com.ansel.service.CommentService;
import com.ansel.service.PostService;
import com.ansel.service.TagService;
import com.ansel.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Ansel Zhong
 * @title blog
 * @Date 2023/1/26
 * @Description indexController
 */
@Controller
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private PostService postService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CommentService commentService;

    @RequestMapping("/index")
    public String index(@PageableDefault(size = 10, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                        Model model, HttpSession session){
                System.out.println("enter:"+"index");
                Page<Post> posts = postService.listPublishedPost(pageable);
                System.out.println("posts-->" + posts);
                model.addAttribute("page", posts);

                List<Type> types = typeService.listTypeTop(6);
                System.out.println("types-->" + types);
                model.addAttribute("types",types);

                List<Tag> tags = tagService.listTagTop(4);
                System.out.println("tags-->" + tags);
                model.addAttribute("tags",tags);

                List<Post> posts1 = postService.listPostTop(6);
                System.out.println("posts-->" + posts1);
                session.setAttribute("postBottom",posts1);
                model.addAttribute("posts",posts1);
                return "index";
    }
    @RequestMapping("/search")
    public String search(@PageableDefault(size = 10, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                         Model model,@RequestParam String query){
        //查询博客title 和 content
        //加 %
        Page<Post> posts = postService.listPost("%"+query+"%", pageable);
        model.addAttribute("page",posts);
        model.addAttribute("query",query);
         return "pages/search";
    }
    @RequestMapping("/post/{id}")
    public String post(@PathVariable Long id, Model model){


        List<Comment> comments = commentService.listCommentByPostId(id);

        //getAndConvert 自带 views + 1
        Post post = postService.getAndConvert(id);


        //markdown --> html 插件commonmark-java https://github.com/atlassian/commonmark-java
        model.addAttribute("comments", comments);
        model.addAttribute("post",post);
        return "pages/iMesPoDetail";
    }
    @RequestMapping("/iMesPoDetail")
    public String iMesPoDetail(){
        return "pages/iMesPoDetail";
    }

    @RequestMapping("/bottomPosts")
    public String bottomPostList(Model model){
        List<Post> posts1 = postService.listPostTop(6);
        System.out.println("posts-->" + posts1);
        model.addAttribute("posts",posts1);
        return "index";
    }

}
