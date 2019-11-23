package com.utils.excel.controller;

import com.utils.excel.entity.User;
import com.utils.excel.util.ExcelUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yewenshu
 * @date 2019/11/22
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/import")
    public String importShow(){
        return "import";
    }

    @PostMapping("/import")
    @ResponseBody
    public String importExcel(MultipartFile file){
        List<User> users = ExcelUtil.importExcel(file,1,1,User.class);
        return users.toString();
    }

    @RequestMapping("/export")
    public void exportExcel(HttpServletResponse response){
        List<User> users = new ArrayList<>();
        for (int i=0;i<=10;i++){
            User user = new User();
            user.setId(i);
            user.setName("name"+i);
            user.setAddress("深圳");
            user.setAge(23+2*i);
            user.setEmail("1212@qq.com");
            user.setPhone("123567");
            user.setQq("554567");
            users.add(user);
        }
        ExcelUtil.exportExcel(users,"用户信息表","表一",User.class,"用户信息表.xls",true,response);
    }

}
