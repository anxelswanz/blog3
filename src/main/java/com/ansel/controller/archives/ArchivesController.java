package com.ansel.controller.archives;

import com.ansel.pojo.Post;
import com.ansel.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @author Ansel Zhong
 * @title blog
 * @Date 2023/1/27
 * @Description
 */
@Controller
public class ArchivesController {
    @Autowired
    PostService postService;
    @RequestMapping("/archives")
    public String archives(Model model){
        Map<String, List<Post>> map = postService.archivePosts();
        model.addAttribute("archiveMap", map);
        model.addAttribute("postCount",postService.countPost());
        return "pages/archives";
    }
}
