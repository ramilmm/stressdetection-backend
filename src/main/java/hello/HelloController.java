package hello;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class HelloController {

    private ArrayList<Integer> hr = new ArrayList<>();

    @GetMapping("/")
    public String index() {
        return "Будущий ДИПЛОМ!!!";
    }

    @GetMapping("/data/hr")
    @CrossOrigin
    public Integer sendHRData() {
//        hr.add(ThreadLocalRandom.current().nextInt(65, 180 + 1));
        return ThreadLocalRandom.current().nextInt(65, 180 + 1);
    }

    @GetMapping("/data/gsr")
    @CrossOrigin
    public Integer sendGSRData() {
        return ThreadLocalRandom.current().nextInt(10, 39 + 1);
    }
    @GetMapping("/data/stress")
    @CrossOrigin
    public Integer sendStressData() {
        return ThreadLocalRandom.current().nextInt(1, 5 + 1);
    }

    
}
