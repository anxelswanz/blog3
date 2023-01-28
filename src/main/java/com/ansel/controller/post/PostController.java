package com.ansel.controller.post;

import com.ansel.pojo.Tag;
import com.ansel.pojo.Type;
import com.ansel.pojo.User;
import com.ansel.service.TagService;
import com.ansel.service.TypeService;
import com.ansel.vo.PostQuery;
import com.ansel.vo.PostQuery2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.ansel.pojo.Post;
import com.ansel.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * @author Ansel Zhong
 * coding time
 */
@Controller
@RequestMapping("/admin")
public class PostController {

  private static final String POST = "admin/post";
  private static final String ADMIN = "admin/admin";
  private static final String REDIRECT_LIST = "redirect:/admin/admin";
    private static final String EDIT = "admin/edit";
  @Autowired
  private PostService postService;
  @Autowired
  private TypeService typeService;
  @Autowired
  private TagService tagService;


  @GetMapping("/admin")
  public String admin(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                      PostQuery2 postQuery2 , Model model, HttpSession session){

    System.out.println("enter-->" + "post");
    User user = (User)session.getAttribute("user");
    postQuery2.setUserId(user.getId());
    Page<Post> posts = postService.listPostByUser(postQuery2,pageable);
    System.out.println("post:" +"finish list");
    model.addAttribute("page",posts);
    System.out.println(posts);
    model.addAttribute("types",typeService.listType());
    System.out.println("finish post");
    return ADMIN;
  }


  @PostMapping("/post/search")
  public String search(@PageableDefault(size = 500, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                       PostQuery2 postquery2, Model model, HttpSession session){
       System.out.println("recommended? ->" + postquery2.isRecommended());
        User user = (User)session.getAttribute("user");
        postquery2.setUserId(user.getId());
        System.out.println("enter-->" + "post");
        Page<Post> posts = postService.listPostByUser(postquery2,pageable);
        System.out.println("post:" +"finish list");
        model.addAttribute("page",posts);
        System.out.println("finish post");
         return "admin/admin :: postList";
  }

      @RequestMapping("/post/input")
       public String input(Model model){
        System.out.println("enter-->"+"input");
        //初始化
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("post",new Post());

        return POST;
    }

//    @PostMapping("/admin")
//    public String post(Long typeId, Post post, HttpSession session, RedirectAttributes attributes){
//      //设置当前登录的用户
//      post.setUser((User)session.getAttribute("user"));
//      System.out.println("user set");
//      Type type1 = typeService.getType(typeId);
//      System.out.println("get type: " + type1);
//      post.setType(type1);
////      List<Tag> tags = tagService.listTags(post.getTagIds());
////      System.out.println("tags: " +tags);
////      post.setTags(tags);
//      Post p = postService.savePost(post);
//      if (p == null) {
//        attributes.addFlashAttribute("msg","Post failed");
//      }else {
//        attributes.addFlashAttribute("msg","Post succeeded");
//      }
//      return REDIRECT_LIST;
//    }

    @PostMapping("/doPost")
//    @ResponseBody
  public String post(Long typeId, Post post, HttpSession session, @PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                     PostQuery postQuery , Model model, RedirectAttributes attributes) {
            //post 设置 user
            User user = (User)session.getAttribute("user");
            post.setUser(user);

            //post 设置 type
            Type type = typeService.getType(typeId);
            System.out.println(type);
            post.setType(type);

            //check
            System.out.println("recommended:" + post.isRecommended());
            System.out.println("commentabeled:" + post.isCommentabled());
            System.out.println("disclaimer:" + post.isDisclaimer());
            System.out.println("published:" + post.isPublished());
            System.out.println("tips" + post.isTips());
            System.out.println("first picture" + post.getFirstPicture());
            // 设置TagIds
            String tagIds = post.getTagIds();
             System.out.println("tagIds: "+tagIds);
            List<Tag> tags = tagService.listTags(tagIds);
            post.setTags(tags);
            System.out.println("post:" +"finish list");

             Post p = postService.savePost(post);

            //model 渲染
//            Page<Post> posts = postService.listPost(pageable, postQuery);
//            model.addAttribute("page",posts);
//            model.addAttribute("types",typeService.listType());


            System.out.println("finish post");
      if (p == null) {
          //model.addAttribute("msg","Post failed");
          attributes.addFlashAttribute("msg","Post failed");
      }else {
          //model.addAttribute("msg","Post succeeded");
          attributes.addFlashAttribute("msg","Post succeeded");
      }
        return REDIRECT_LIST;
    }

    private void setTypeAndTag(Model model) {
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
    }
    @GetMapping("/post/edit/{id}")
    public String editInput(@PathVariable Long id, Model model){
        System.out.println("enter-->"+"editInput");
        setTypeAndTag(model);
        Post post = postService.getPost(id);
        post.init();
        model.addAttribute("post",post);
        return EDIT;
    }

    @PostMapping("/doEdit")
//    @ResponseBody
    public String doEdit(Long typeId, Post post, HttpSession session, @PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                       RedirectAttributes attributes) {
        //post 设置 user
        User user = (User)session.getAttribute("user");
        post.setUser(user);

        //post 设置 type
        Type type = typeService.getType(typeId);
        System.out.println(type);
        post.setType(type);
        System.out.println("type:"+type);
        //check
        System.out.println("recommended:" + post.isRecommended());
        System.out.println("commentabeled:" + post.isCommentabled());
        System.out.println("disclaimer:" + post.isDisclaimer());
        System.out.println("published:" + post.isPublished());
        System.out.println("tips" + post.isTips());
        System.out.println("createDate" + post.getCreateDate());
        System.out.println("views" + post.getViews());
        // 设置TagIds
        String tagIds = post.getTagIds();
        System.out.println("tagIds: "+tagIds);
        List<Tag> tags = tagService.listTags(tagIds);
        post.setTags(tags);
        System.out.println("post:" +"finish list");

        Post p = postService.updatePost(post.getId(),post);

        //model 渲染
//            Page<Post> posts = postService.listPost(pageable, postQuery);
//            model.addAttribute("page",posts);
//            model.addAttribute("types",typeService.listType());


        System.out.println("finish post");
        if (p == null) {
            //model.addAttribute("msg","Post failed");
            attributes.addFlashAttribute("msg","Post failed");
        }else {
            //model.addAttribute("msg","Post succeeded");
            attributes.addFlashAttribute("msg","Post succeeded");
        }
        return REDIRECT_LIST;
    }
    @RequestMapping("/post/delete/{id}")
    public String delete(@PathVariable Long id,Long typeId, Post post, HttpSession session, @PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         RedirectAttributes attributes, Model model){
         postService.deletePost(id);
        attributes.addFlashAttribute("msg","Delete succeeded");
        System.out.println("==================delete======================");
        model.addAttribute("msg","Delete succeeded");
        return REDIRECT_LIST;
    }
}
