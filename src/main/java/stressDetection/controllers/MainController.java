package stressDetection.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import stressDetection.services.StressService;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class MainController {

    @Autowired
    StressService stressService;

    ArrayList<Integer> hr = new ArrayList<>();
    ArrayList<Integer> gsr = new ArrayList<>();
    int i = 0,j = 0;


    @GetMapping("/")
    public ArrayList index() {
        stressService.findStress();
        return hr;
    }

    @GetMapping("/data/hr")
    @CrossOrigin
    public Integer sendHRData() {
        if (i == 0) hr = stressService.generateHR();
        i++;
        return hr.get(i);
    }

    @GetMapping("/data/gsr")
    @CrossOrigin
    public Integer sendGSRData() {
        if (j == 0) gsr = stressService.generateGSR();
        j++;
        return gsr.get(j);
    }

    @GetMapping("/data/stress")
    @CrossOrigin
    public Integer sendStressData() {
        return ThreadLocalRandom.current().nextInt(1, 5 + 1);
    }

    
}
