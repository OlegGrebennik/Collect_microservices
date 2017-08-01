package com.og;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class MainController {

    @RequestMapping(value="/upload", method= RequestMethod.POST)
    public void uploadFile (@RequestParam("name") String name,
                            @RequestParam("file") MultipartFile file) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        PersonalFile personalFile = new PersonalFile();
        personalFile.setFile(file.getBytes());
        personalFile.setFileName(file.getOriginalFilename());
        org.springframework.http.HttpEntity<PersonalFile> httpEntity = new org.springframework.http.HttpEntity<>(personalFile);
        ResponseEntity<String> respons = restTemplate.exchange("http://localhost:8111/upload", HttpMethod.POST, httpEntity, String.class);
        System.out.println(name);
    }

    @RequestMapping(value="/download", method= RequestMethod.GET)
    public PersonalFile downloadFile (@RequestParam("name") String name) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PersonalFile> response = restTemplate.exchange("http://localhost:8111/download?fileName="+name, HttpMethod.GET, null, PersonalFile.class);
        return response.getBody();
    }




    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/service/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }
}
