package kr.co.direa.nginx_web.controller;

import kr.co.direa.nginx_web.dto.OpenPort;
import kr.co.direa.nginx_web.service.MonitoringService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequestMapping(value = "/nginx/web")
@Controller
public class MonitoringController {

    private final MonitoringService monitoringService;

    @Autowired
    public MonitoringController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @GetMapping("/management")
    public String getManagement(Model model) throws ParseException {

        List<OpenPort> openPortList = monitoringService.getNodeStatus();

        // nginx 서버가 기동중이 아니라면
        if (openPortList.size() == 0) {
            model.addAttribute("isRun", false);
            return "management";
        }

        model.addAttribute("isRun", true);
        model.addAttribute("openPortList", openPortList);
        return "management";
    }

    @PostMapping("/management/start")
    public String startNginx(RedirectAttributes ra) {
        monitoringService.startNginx();
        ra.addFlashAttribute("result", "기동 완료!");
        ra.addFlashAttribute("isRun", true);
        return "redirect:/nginx/web/management";
    }

    @PostMapping("/management/stop")
    public String stopNginx(RedirectAttributes ra) {
        monitoringService.stopNginx();
        ra.addFlashAttribute("result", "정지 완료!");
        ra.addFlashAttribute("isRun", false);
        return "redirect:/nginx/web/management";
    }

    @PostMapping("/management/reload")
    public String reloadNginx(RedirectAttributes ra) {
        monitoringService.reloadNginx();
        ra.addFlashAttribute("result", "재기동 완료!");
        ra.addFlashAttribute("isRun", true);
        return "redirect:/nginx/web/management";
    }
}
