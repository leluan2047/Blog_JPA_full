package com.codegym.cms.controller;


import com.codegym.cms.model.Blog;
import com.codegym.cms.model.Category;
import com.codegym.cms.service.BlogService;
import com.codegym.cms.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class BlogController {
    @Autowired
    BlogService blogService;
    @Autowired
    CategoryService categoryService;

    @ModelAttribute("category")
    public Iterable<Category> categories(){
        return categoryService.findAll();
    }


    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/blog/create");
        modelAndView.addObject("blog", new Blog());
        return modelAndView;
    }
    @PostMapping("/create")
    public ModelAndView create(@ModelAttribute("blog") Blog blog) {
        blogService.save(blog);
        ModelAndView modelAndView = new ModelAndView("/blog/create");
        modelAndView.addObject("blog", blog);
        modelAndView.addObject("message", "successfull");
        return modelAndView;

    }
    @GetMapping("/")
    public ModelAndView list(Pageable pageable, @RequestParam("s") Optional<String> s) {
        Page<Blog> list;
        if(s.isPresent()){
            list = blogService.findAllByTitle(s.get(),pageable);
        }else{
            list = blogService.findAll(pageable);
        }

        ModelAndView modelAndView = new ModelAndView("/blog/list");
        modelAndView.addObject("list", list);
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Blog blog = blogService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/blog/editForm");

        modelAndView.addObject("blog", blog);
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView edit(@ModelAttribute("blog") Blog blog) {
        blogService.save(blog);
        ModelAndView modelAndView = new ModelAndView("/blog/editForm");
        modelAndView.addObject("blog", blog);
        modelAndView.addObject("message", "Successful");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){

        Blog blog = blogService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/blog/deleteForm");
        modelAndView.addObject("blog",blog);
        return modelAndView;
    }

    @PostMapping("/delete")
    public ModelAndView delete(@ModelAttribute("blog") Blog blog){
        ModelAndView modelAndView = new ModelAndView("/blog/deleteForm");
        blogService.remove(blog.getId());
        modelAndView.addObject("message","Successful");
        return modelAndView;
    }

    @GetMapping("/view/{id}")
    public ModelAndView show(@PathVariable Long id){
        Blog blog = blogService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/blog/view");
        modelAndView.addObject("blog",blog);

        return modelAndView;
    }

    @GetMapping("/view-category/{id}")
    public String viewByCategory(@PathVariable Long id, Model model){
        Category category = categoryService.findById(id);
        Iterable<Blog> list = blogService.findAllByCategory(category);

        model.addAttribute("category",category.getName());
        model.addAttribute("list",list);
        return "/blog/viewByCategory";
    }
}
