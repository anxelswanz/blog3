package com.ansel.controller.commentController;

import com.ansel.pojo.Comment;
import com.ansel.pojo.Post;
import com.ansel.pojo.User;
import com.ansel.service.CommentService;
import com.ansel.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Ansel Zhong
 * @title blog
 * @Date 2023/1/26
 * @Description comment
 */
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{postId}")
    public String comments(@PathVariable Long postId, Model model){
        List<Comment> comments = commentService.listCommentByPostId(postId);
        Post post = postService.getAndConvert(postId);

        for (Comment comment :comments) {
            System.out.println(comment.getId());
            System.out.println(comment.getNickname());
            System.out.println(comment.getContent());
            System.out.println(comment.getEmail());
            System.out.println(comment.getParentComment());
            System.out.println(comment.getCreateTime());
            System.out.println(comment.getPost());
        }
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        return "pages/iMesPoDetail";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        System.out.println("enter-->" + "comments method");
        System.out.println("comment.getPost:"+comment.getPost());
        System.out.println("comment.getContent:"+comment.getContent());
        System.out.println("comment.getEmail():"+comment.getEmail());
        System.out.println("comment.getNickName:"+comment.getNickname());
        System.out.println("comment.getPost:"+comment.getPost());

        Long postId = comment.getPost().getId();
        System.out.println("postId: " + postId);
        Post post = postService.getPost(postId);
        System.out.println("post: " + post);


        //拿到user
        User user = (User) session.getAttribute("user");
        if (user != null){
            comment.setAvatar(user.getAvatar());
            if (post.getUser().getId() == user.getId()){
                comment.setAdminComment(true);
                comment.setNickname(user.getNickname());
                comment.setEmail(user.getEmail());
                comment.setAvatar(user.getAvatar());
            }
        }else{
            comment.setAvatar(avatar);
            System.out.println("avatar: " + avatar);
        }

        comment.setPost(post);
        commentService.saveComment(comment);
        return "redirect:/comments/" + postId;
    }
}
