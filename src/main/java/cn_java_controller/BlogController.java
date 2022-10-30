package cn_java_controller;

import cn_java_service_impl.CreateBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@SuppressWarnings("all")
public class BlogController {

    @Autowired
    private CreateBlogService createBlogService;

    @RequestMapping(value = "/createBlog", method = RequestMethod.GET)
    public String createBlog() {
        return "/CreateBlog.html";
    }


    @RequestMapping("/uploadBlog")
    @ResponseBody
    public Map<String, Object> uploadBlog(MultipartFile[] files, String text, HttpServletRequest request, String isPublic) throws Exception{
        System.out.println("传过来的文本" + text);
        String token = request.getHeader("token");
        return createBlogService.uploadBlog(files, text, isPublic, token);
    }
}
