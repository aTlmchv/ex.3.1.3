package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.MyUserDetailService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@Controller
public class UserController {
    private MyUserDetailService myUserDetailService;

    private UserServiceImpl userServiceImpl;

    @Autowired
    public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Autowired
    public void setUserService(MyUserDetailService myUserDetailService) {
        this.myUserDetailService = myUserDetailService;
    }

    @GetMapping("/index")
    public String indexPage() {
        return "index";
    }

    @RequestMapping("user")
    public String updateUser(Principal principal, Model model) {
        User user = myUserDetailService.findByUsername(principal.getName());
        model.addAttribute("userInfo", user);
        return "user";
    }

    @RequestMapping("/admin/add")
    public String addUser(@ModelAttribute("user") User user) {
        userServiceImpl.addUser(user);
        return "redirect:/admin";
    }

    @RequestMapping("/admin")
    public String listUsers(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("listUsers", userServiceImpl.listUsers());

        return "admin";
    }
    @RequestMapping("admin/id/delete")
    public String delete(Model model, @RequestParam("id") int id) {
        model.addAttribute("user", userServiceImpl.getUserById(id));
        return "delete";
    }

    @RequestMapping("admin/id")
    public String removeUser(@RequestParam("id") int id) {
        userServiceImpl.removeUser(id);

        return "redirect:/admin";
    }

    @RequestMapping("admin/id/edit")
    public String edit(Model model, @RequestParam("id") int id) {
        model.addAttribute("user", userServiceImpl.getUserById(id));
        return "edit";
    }

    @RequestMapping("admin/{id}")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("id") int id) {
        userServiceImpl.updateUser(user, id);
        return "redirect:/admin";
    }
}
