package cn_java_controller;

import cn_java_service_impl.DisplayBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class DisplayBlogController {
    @Autowired
    private DisplayBlogService displayBlogService;

    @SuppressWarnings("all")
    @RequestMapping("/around")
    public String around(){
        return "/Around.html";
    }

    @RequestMapping("/getAroundBlog")
    @ResponseBody
    public List<Map<String, Object>> displayAroundBlog(){
        List<Map<String, Object>> mapList = displayBlogService.displayAroundBlog();
        return mapList;
    }
}
