package com.perfree.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.json.JSONObject;
import com.perfree.common.DownlaodUtils;
import com.perfree.common.ResponseBean;
import com.perfree.generate.Generate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * TODO
 *
 * @author YinPengFei
 */
@Controller
public class TemplateController {
    @Value("${generateTemplatesPath}")
    private String generateTemplatesPath;

    @RequestMapping("/template")
    public String index(Model model){
        try {
            model.addAttribute("templates", Generate.getTemplates(generateTemplatesPath));
        } catch (Exception e) {
            model.addAttribute("templates", null);
        }
        return "template";
    }

    @RequestMapping("/template/download")
    public void download(HttpServletRequest request, HttpServletResponse response){
        String fileName = request.getParameter("fileName");
        if (fileName != null) {
            //设置文件路径
            File file = new File(generateTemplatesPath + fileName);
            File zip = ZipUtil.zip(file);
            DownlaodUtils.downloadFile(zip,response,true);
        }
    }

    /**
     * 生成代码
     *
     * @param param
     * @return
     */
    @RequestMapping("/template/del")
    @ResponseBody
    public ResponseBean generateCode(@RequestBody HashMap<String, Object> param) {
        String fileName = (String) param.get("fileName");
        boolean del = FileUtil.del(new File(generateTemplatesPath + fileName));
        if(del){
            return new ResponseBean(200,"删除成功",null);
        }else{
            return new ResponseBean(500,"删除失败",null);
        }
    }

    @RequestMapping("/template/upload")
    @ResponseBody
    public ResponseBean upload(@RequestParam(value = "file", required = false) MultipartFile file){
        if (!file.isEmpty()) {
            try {
                File templatePathFile = new File(generateTemplatesPath);
                if (!templatePathFile.exists()){
                    templatePathFile.mkdirs();
                }
                BufferedOutputStream out = new BufferedOutputStream(
                        Files.newOutputStream(new File(generateTemplatesPath, Objects.requireNonNull(file.getOriginalFilename())).toPath()));
                out.write(file.getBytes());
                out.flush();
                out.close();
                File zipFile = new File(generateTemplatesPath+file.getOriginalFilename());
                String type = FileUtil.getType(zipFile);
                if(type.equals("zip")){
                    String fileName = zipFile.getName().substring(0,zipFile.getName().length() - 4);
                    File templateDir = new File(generateTemplatesPath + fileName);
                    if(templateDir.exists()){
                        FileUtil.del(zipFile);
                        return new ResponseBean(500,"模板已存在!",null);
                    }
                    File unzip = ZipUtil.unzip(zipFile);
                    FileUtil.del(zipFile);
                    Map<String, String> templateParam = null;
                    try {
                        templateParam = Generate.getTemplateParam(unzip.getName(), generateTemplatesPath);
                    } catch (Exception e) {
                        FileUtil.del(unzip);
                        return new ResponseBean(500,"模板文件错误,未扫描到模板配置文件或配置文件不正确!",null);
                    }
                    return new ResponseBean(200,"上传成功", templateParam);
                }else{
                    return new ResponseBean(500,"文件类型错误,请上传模板压缩包",null);
                }
            } catch (IOException e) {
                return new ResponseBean(500,"上传失败",null);
            }
        } else {
            return new ResponseBean(500,"文件不能为空",null);
        }
    }
}
