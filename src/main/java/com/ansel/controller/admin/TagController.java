package com.ansel.controller.admin;

import com.ansel.pojo.Tag;
import com.ansel.pojo.Type;
import com.ansel.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author Ansel Zhong
 * coding time
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping("/tags")
    public String toTagsPage(@PageableDefault(size = 3,sort = {"id"},direction = Sort.Direction.DESC)
                        Pageable pageable, Model model) {
        System.out.println("enter:"+"toTagsPage");
        model.addAttribute("page",tagService.listTag(pageable));
        return "admin/tags";
    }
    //新增
    @GetMapping("/tags/input")
    public String addInput(Model model){
        model.addAttribute("tag",new Type());
        return "admin/tags-input";
    }

    //type 后面一定要紧挨着bindingresult
    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        //重复校验
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            //name pojo类字段 nameError自定义
            result.rejectValue("name","nameError","The tag name already exists");
            return "admin/tags-input";
        }
        System.out.println("type=" + tag.getName());
        if (result.hasErrors()){
            return "admin/tags-input";
        }
        if (tag == null) {
            attributes.addFlashAttribute("msg","The name can't be empty");
        }else {
            Tag t = tagService.saveTag(tag);
            System.out.println("t=" + t);
            attributes.addFlashAttribute("msg","Add Success");
        }
        return "redirect:/admin/tags";
    }

    //edit
    @GetMapping("/tags/input/{id}")
    public String editInput(@PathVariable Long id, Model model){
        Tag tag = tagService.getTag(id);

        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-edit";
    }

    //type 后面一定要紧挨着bindingresult
    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result, RedirectAttributes attributes,@PathVariable Long id){
        if (tag == null) {
            attributes.addFlashAttribute("msg","The name can't be empty");
        }else {
            if (tag.getName() == "Study"){
                attributes.addFlashAttribute("msg","Sorry the tag cannot be changed");
            }else {
                Tag t = tagService.updateTag(id,tag);
                System.out.println("t=" + t);
                attributes.addFlashAttribute("msg","Update Success");
            }

        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("msg","Delete Success");
        return "redirect:/admin/tags";
    }
}
