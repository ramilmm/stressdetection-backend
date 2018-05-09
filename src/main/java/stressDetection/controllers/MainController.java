package stressDetection.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
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
        hr = stressService.generateHR();
        gsr = stressService.generateGSR();
    }

    @GetMapping("/template")
    @CrossOrigin
    public void initTemplate() {
        stressService.initTemplate(hr, gsr);
        readyToCheck = 1;
    }

    @GetMapping("/data/hr")
    @CrossOrigin
    public Integer sendHRData() {
        i++;
        return hr.get(i);
    }

    @GetMapping("/data/gsr")
    @CrossOrigin
    public Integer sendGSRData() {
        j++;
        return gsr.get(j);
    }

    @GetMapping("/data/stress")
    @CrossOrigin
    public Integer sendStressData() {
        if (readyToCheck == 1) {
            return stressService    .findStress(hr, gsr, (i - 1));
        }else return 0;
    }

    
}
