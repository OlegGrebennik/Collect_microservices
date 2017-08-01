package com.og;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class CollectorService {

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("128MB");
        factory.setMaxRequestSize("128MB");
        return factory.createMultipartConfig();
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody
    String uploadFile(@RequestBody PersonalFile file){
        if (file.getFile().length != 0) {
            try {
                byte[] bytes = file.getFile();
                String path = System.getProperty("catalina.home");
                File uploadedFile = new File(path + File.separator + file.getFileName());
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(uploadedFile));
                stream.write(bytes);
                stream.flush();
                stream.close();
                return "Вы удачно загрузили!";
            } catch (Exception e) {
                return "Вам не удалось загрузить => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить файл, потому что он пустой.";
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "download")
    public @ResponseBody PersonalFile downloadFile (@RequestParam("fileName") String fileName) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        File downloadedFile = new File(System.getProperty("catalina.home") + File.separator + fileName);
        byte[] data = Files.readAllBytes(Paths.get(downloadedFile.getPath()));
        PersonalFile personalFile = new PersonalFile(data, downloadedFile.getName());
        return personalFile;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public String getTest (){
        return "test ok";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/postFile")
    public String saveFile (@RequestBody String st){
        return "st" + "OK";
    }
}
