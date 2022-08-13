package kr.co.direa.nginx_web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MonitoringService {

    @Value("${node1.nginx_api.ip}")
    private String ip;

    @Value("${node1.nginx_api.port}")
    private int port;

    public String getNodeInfo() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://192.168.1.200:8888" + "/nginx/api/monitoring", String.class);
        return responseEntity.getBody();
    }

    public String getHttpMethod(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://192.168.1.200:8888" + "/nginx/api/monitoring", String.class);
        return responseEntity.getBody();
    }
}
