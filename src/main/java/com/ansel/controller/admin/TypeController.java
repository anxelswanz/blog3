package com.ansel.controller.admin;

import com.ansel.pojo.Type;
import com.ansel.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
public class TypeController {
  @Autowired
  private TypeService typeService;



    @GetMapping("/types")
    public String types(@PageableDefault(size = 3,sort = {"id"},direction = Sort.Direction.DESC)
                        Pageable pageable, Model model) {
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }

    //新增
  @GetMapping("/types/input")
  public String addInput(Model model){
        model.addAttribute("type",new Type());
    return "admin/types-input";
  }

  //type 后面一定要紧挨着bindingresult
  @PostMapping("/types")
  public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        //重复校验
      Type type2 = typeService.getTypeByName(type.getName());
      if (type2 != null) {
          //name pojo类字段 nameError自定义
          result.rejectValue("name","nameError","The name already exists");
          return "admin/types-input";
      }
      System.out.println("type=" + type.getName());
      if (result.hasErrors()){
          return "admin/types-input";
      }
   if (type == null) {
             attributes.addFlashAttribute("msg","The name can't be empty");
   }else {
       Type t = typeService.saveType(type);
       System.out.println("t=" + t);
       attributes.addFlashAttribute("msg","Add Success");
   }
   return "redirect:/admin/types";
  }

    //type 后面一定要紧挨着bindingresult
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result, RedirectAttributes attributes,@PathVariable Long id){
        if (type == null) {
            attributes.addFlashAttribute("msg","The name can't be empty");
        }else {
            Type t = typeService.updateType(id,type);
            System.out.println("t=" + t);
            attributes.addFlashAttribute("msg","Update Success");
        }
        return "redirect:/admin/types";
    }

  //edit
    @GetMapping("/types/input/{id}")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-edit";
    }

    @GetMapping("/types/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("msg","Delete Success");
        return "redirect:/admin/types";
    }
}
