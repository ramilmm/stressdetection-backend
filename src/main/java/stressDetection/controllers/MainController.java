package stressDetection.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import stressDetection.models.Template;
import stressDetection.services.ReadDataService;
import stressDetection.services.StressService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class MainController {

    @Autowired
    StressService stressService;


    ArrayList<Integer> hr = new ArrayList<>();
    ArrayList<Integer> gsr = new ArrayList<>();

    int i = 0,j = 0, readyToCheck = 0;


    @PostConstruct
    public void init() {
        Thread thread = new Thread(new ReadDataService(stressService), "Reader");
        thread.start();

        //hr = stressService.generateHR();
        //gsr = stressService.generateGSR();
    }

    //@GetMapping("/api/template")
    //@CrossOrigin
    //public void initTemplate() {
    //    stressService.initTemplate(hr, gsr);
    //    readyToCheck = 1;
    //}

    @GetMapping("/api/data/hr")
    @CrossOrigin
    public Integer sendHRData() {
        return stressService.getHr();
    }

    @GetMapping("/api/data/gsr")
    @CrossOrigin
    public Integer sendGSRData() {
        return stressService.getGsr();
    }

    @GetMapping("/api/data/stress")
    @CrossOrigin
    public Integer sendStressData() {
//        if (readyToCheck == 1) {
            return stressService.findStress();
//        }else return 0;
    }

    @GetMapping("/api/data/template")
    @CrossOrigin
    public Template sendTemplateData() {
        return stressService.getTemplate();
    }

    
}
