package com.nf147.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin
public class ImgController {

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object UpLoadImg(@RequestParam(value = "myFileName") MultipartFile mf) throws IOException {
        String projectServerPath = request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath() + "/image/";
        String path = request.getSession().getServletContext().getRealPath("/image/");

        String filename = mf.getOriginalFilename();
        String[] names = filename.split("\\.");
        String tempNum = (int) (Math.random() * 100000) + "";
        String uploadFileName = tempNum + System.currentTimeMillis() + "." + names[names.length - 1];

        File file = new File(path + uploadFileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        mf.transferTo(new File(path + File.separator + uploadFileName));
        String serverFilePatn = projectServerPath + uploadFileName;
        Map<String, String> map = new HashMap<String, String>();
        map.put("data", serverFilePatn);//这里应该是项目路径
        return map;
    }

}
