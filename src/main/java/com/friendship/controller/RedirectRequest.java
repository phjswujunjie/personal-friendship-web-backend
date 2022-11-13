package com.friendship.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@SuppressWarnings("all")
@CrossOrigin(originPatterns = {"http://localhost:8081"}, allowCredentials = "true")
public class RedirectRequest {

    @RequestMapping("/around")
    public String around(){
        return "/Around.html";
    }

    @RequestMapping(value = "/createBlog", method = RequestMethod.GET)
    public String createBlog() {
        return "/CreateBlog.html";
    }

    @RequestMapping("/login")
    public String login(){
        return "/Login.html";
    }


    @RequestMapping("/register")
    public String register(){
        return "/Register.html";
    }

    @RequestMapping("/homepage")
    public String homepage(){
        return "/Homepage.html";
    }

    @RequestMapping("/")
    public String index(){
        return "/Homepage.html";
    }

    @RequestMapping("/personalInfo")
    public String personalInfo() {
        return "/PersonalInfo.html";
    }

    @RequestMapping("/editPhoto")
    public String editPhoto() {
        return "/EditPhoto.html";
    }

    @RequestMapping("/blogComment")
    public String blogComment() {
        return "/BlogComment.html";
    }
}
