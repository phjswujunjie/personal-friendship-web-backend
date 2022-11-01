package cn_java_controller;

import cn_java_PO.Code;
import cn_java_PO.Result;
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
    public Result createBlog(MultipartFile[] files, String text, HttpServletRequest request, String isPublic) throws Exception{
        String token = request.getHeader("token");
        int result = blogService.uploadBlog(files, text, isPublic, token);
        if (result == 1){
            return new Result(Code.INSERT_OK, "创建成功");
        }else {
            return new Result(Code.INSERT_ERR, "创建失败");
        }
    }

    //得到附近全部的博客信息
    @GetMapping
    public Result displayAroundBlog(){
        List<Map<String, Object>> mapList = blogService.displayAroundBlog();
        return new Result(Code.SELECT_OK, mapList);
    }
}
