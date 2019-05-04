package com.codegym.cms.controller;

import com.codegym.cms.model.Category;
import com.codegym.cms.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/category")
    public String showListCate(Model model) {
        Iterable<Category> list = categoryService.findAll();
        model.addAttribute("list", list);
        return "/category/list";
    }

    @GetMapping("/create-category")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new Category());
        return "/category/create";
    }

    @PostMapping("/create-category")
    public String create(@ModelAttribute Category category, Model model) {
        categoryService.save(category);
        model.addAttribute("message", "Successful");
        model.addAttribute("category", category);
        return "/category/create";
    }

    @GetMapping("/edit-category/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        return "/category/editForm";
    }

    @PostMapping("/edit-category")
    public String Edit(@ModelAttribute Category category, Model model) {
        categoryService.save(category);
        model.addAttribute("message", "Successfull");
        model.addAttribute("category", category);
        return "/category/editForm";
    }

    @GetMapping("/delete-category/{id}")
    public String showDeleteForm(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        return "/category/deleteForm";
    }

    @PostMapping("/delete-category")
    public String delete(@ModelAttribute("category") Category category, Model model) {
        categoryService.remove(category.getId());
        model.addAttribute("category", category);
        model.addAttribute("message", "Succcessful");
        return "/category/deleteForm";
    }

}
