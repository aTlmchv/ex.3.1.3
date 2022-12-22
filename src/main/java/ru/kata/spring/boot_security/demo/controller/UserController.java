package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.MyUserDetailService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class UserController {
    private MyUserDetailService myUserDetailService;

    private UserServiceImpl userServiceImpl;

    public UserController(MyUserDetailService myUserDetailService, UserServiceImpl userServiceImpl) {
        this.myUserDetailService = myUserDetailService;
        this.userServiceImpl = userServiceImpl;
    }

    public String indexPage() {
        return "index";
    }

    @GetMapping("user")
    public String updateUser(Principal principal, Model model) {
        User user = myUserDetailService.findByUsername(principal.getName());
        model.addAttribute("userInfo", user);
        return "user";
    }

    @PostMapping("/admin/add")
    public String addUser(@ModelAttribute("user") User user) {
        userServiceImpl.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String listUsers(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("listUsers", userServiceImpl.listUsers());

        return "admin";
    }

    @GetMapping("admin/delete")
    public String delete(Model model, @RequestParam("id") int id) {
        model.addAttribute("user", userServiceImpl.getUserById(id));
        return "delete";
    }

    @GetMapping("admin/edit")
    public String edit(Model model, @RequestParam("id") int id) {
        model.addAttribute("user", userServiceImpl.getUserById(id));
        return "edit";
    }



    @DeleteMapping("admin/")
    public String removeUser(@RequestParam("id") int id) {
        userServiceImpl.removeUser(id);
        return "redirect:/admin";
    }

    @PostMapping("admin/")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("id") int id) {
        userServiceImpl.updateUser(user, id);
        return "redirect:/admin";
    }
}