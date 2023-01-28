package com.ansel.controller.category;

import com.ansel.pojo.Post;
import com.ansel.pojo.Tag;
import com.ansel.pojo.Type;
import com.ansel.service.PostService;
import com.ansel.service.TypeService;
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
@RequestMapping("/category")
public class Category {
    @Autowired
    PostService postService;
    @Autowired
    TypeService typeService;

    @RequestMapping("/doType/{id}")
    public String doType(@PageableDefault(size = 10, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                         Model model, @PathVariable Long id){
        System.out.println("id=" + id);
        List<Type> types = typeService.listTypeTop(100);
        if (id == -1){
            id = types.get(0).getId();
        }
        PostQuery postQuery = new PostQuery();
        postQuery.setTypeId(id);
        Page<Post> posts = postService.listPost(pageable, postQuery);
        System.out.println("posts=" + posts);
        model.addAttribute("types",types);
        model.addAttribute("page",posts);

        model.addAttribute("activeTypeId",id);
        return "pages/category";
    }
}
