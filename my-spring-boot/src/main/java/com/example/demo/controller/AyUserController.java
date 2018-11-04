package com.example.demo.controller;

import com.example.demo.model.AyUser;
import com.example.demo.service.AyUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/ayuser")
public class AyUserController {

    @Resource
    private AyUserService ayUserService;

    @RequestMapping("/findall")
    public String test(Model model){
        List<AyUser> ayUsers=ayUserService.findAll();
        model.addAttribute("ayUsers",ayUsers);
        return "ayUser";
    }
}
