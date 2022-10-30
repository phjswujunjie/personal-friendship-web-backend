package cn_java_controller;

import cn_java_service_impl.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@SuppressWarnings("all")
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    //供用户上传博客信息
    @PostMapping
    @ResponseBody
    public Map<String, Object> createBlog(MultipartFile[] files, String text, HttpServletRequest request, String isPublic) throws Exception{
        System.out.println("传过来的文本" + text);
        String token = request.getHeader("token");
        return blogService.uploadBlog(files, text, isPublic, token);
    }

    //得到附近全部的博客信息
    @GetMapping
    @ResponseBody
    public List<Map<String, Object>> displayAroundBlog(){
        List<Map<String, Object>> mapList = blogService.displayAroundBlog();
        return mapList;
    }
}
