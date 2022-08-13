package kr.co.direa.nginx_web.controller;

import kr.co.direa.nginx_web.service.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/nginx/web")
@Controller
public class MonitoringController {

    private final MonitoringService monitoringService;

    @Autowired
    public MonitoringController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @GetMapping(value = "/test")
    public String test() {
        System.out.println(monitoringService.getNodeInfo());
        return "index";
    }

}
