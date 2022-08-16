package kr.co.direa.nginx_web.service;

import kr.co.direa.nginx_web.dto.OpenPort;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MonitoringService {

    @Value("${node1.nginx_api.ip}")
    private String node1Ip;

    @Value("${node1.nginx_api.port}")
    private int node1Port;

    // 해당 노드 nginx 정보 가져오기
    public List<OpenPort> getNodeStatus() throws ParseException {
        String getBody = getHttpMethod(node1Ip, node1Port, "/nginx/api/monitoring");
        List<OpenPort> openPortList = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(getBody);
        // nginx 기동 검사
        if (jsonObject.get("run").equals(false)) {
            return openPortList;
        }

        // 열려있는 포트 목록 조회
        JSONArray list = (JSONArray) jsonObject.get("openPortList");
        for (Object o : list) {
            JSONObject ipPort = (JSONObject) o;
            OpenPort openPort = OpenPort.builder()
                    .ip(ipPort.get("ip").toString())
                    .port((long)ipPort.get("port"))
                    .build();
            openPortList.add(openPort);
        }

        // nginx 구동중?
        return openPortList;
    }
    public void startNginx() {
        getHttpMethod(node1Ip, node1Port, "/nginx/api/start");
    }
    public void stopNginx() {
        getHttpMethod(node1Ip, node1Port, "/nginx/api/stop");
    }
    public void reloadNginx() {
        getHttpMethod(node1Ip, node1Port, "/nginx/api/reload");
    }


    // get http body
    private String getHttpMethod(String ip, int port, String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://" + ip + ":"+ port + url, String.class);
        return responseEntity.getBody();
    }
}
