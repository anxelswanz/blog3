package com.ansel.controller.tagsIndex;

import com.ansel.pojo.Post;
import com.ansel.pojo.Tag;
import com.ansel.pojo.Type;
import com.ansel.service.PostService;
import com.ansel.service.TagService;
import com.ansel.vo.PostQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Ansel Zhong
 * @title blog
 * @Date 2023/1/27
 * @Description
 */
@Controller
@RequestMapping("/tags")
public class TagsIndexController {
    @Autowired
    PostService postService;
    @Autowired
    TagService tagService;

    @RequestMapping("/doTags/{id}")
    public String doTags(@PageableDefault(size = 10, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                         Model model, @PathVariable Long id){
        System.out.println("id=" + id);
        List<Tag> tags = tagService.listTagTop(100);
        if (id == -1){
            id = tags.get(0).getId();
        }
        Page<Post> posts = postService.listPost(id, pageable);
        System.out.println("posts=" + posts);
        model.addAttribute("tags",tags);
        model.addAttribute("page",posts);
        model.addAttribute("activeTagId",id);
        return "pages/tagsIndex";
    }
    @RequestMapping("/toTags")
    public String doTag(){
        return "pages/tagsIndex";
    }
}
