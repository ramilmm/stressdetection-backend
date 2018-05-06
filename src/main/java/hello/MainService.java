package hello;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class MainService {

    ArrayList<Integer> hr, gsr;

    public ArrayList<Integer> generateHR() {
        ArrayList<Integer> hrArray = new ArrayList<>();

        for (int i = 0; i < 200; i++) {
            hrArray.add(ThreadLocalRandom.current().nextInt(-5, 5 + 1) + 78);
        }

        for (int i = 0; i < 100; i++) {
            hrArray.add(ThreadLocalRandom.current().nextInt(-10, 15 + 1) + 118);
        }

        for (int i = 0; i < 100; i++) {
            hrArray.add(ThreadLocalRandom.current().nextInt(-10, 10 + 1) + 138);
        }

        for (int i = 0; i < 100; i++) {
            hrArray.add(ThreadLocalRandom.current().nextInt(-10, 10 + 1) + 150);
        }

        for (int i = 0; i < 100; i++) {
            hrArray.add(ThreadLocalRandom.current().nextInt(-10, 10 + 1) + 138);
        }

        for (int i = 0; i < 100; i++) {
            hrArray.add(ThreadLocalRandom.current().nextInt(-10, 10 + 1) + 128);
        }

        for (int i = 0; i < 100; i++) {
            hrArray.add(ThreadLocalRandom.current().nextInt(-10, 10 + 1) + 108);
        }

        for (int i = 0; i < 100; i++) {
            hrArray.add(ThreadLocalRandom.current().nextInt(-10, 10 + 1) + 98);
        }

        for (int i = 0; i < 100; i++) {
            hrArray.add(ThreadLocalRandom.current().nextInt(-10, 10 + 1) + 88);
        }

        return hrArray;
    }

    public ArrayList<Integer> generateGSR() {
        ArrayList<Integer> gsrArray = new ArrayList<>();

        for (int i = 0; i < 200; i++) {
            gsrArray.add(ThreadLocalRandom.current().nextInt(-3, 3 + 1) + 10);
        }

        for (int i = 0; i < 100; i++) {
            gsrArray.add(ThreadLocalRandom.current().nextInt(-3, 3 + 1) + 15);
        }

        for (int i = 0; i < 100; i++) {
            gsrArray.add(ThreadLocalRandom.current().nextInt(-3, 3 + 1) + 20);
        }

        for (int i = 0; i < 100; i++) {
            gsrArray.add(ThreadLocalRandom.current().nextInt(-3, 3 + 1) + 25);
        }

        for (int i = 0; i < 100; i++) {
            gsrArray.add(ThreadLocalRandom.current().nextInt(-9, 10 + 1) + 30);
        }

        for (int i = 0; i < 100; i++) {
            gsrArray.add(ThreadLocalRandom.current().nextInt(-5, 5 + 1) + 28);
        }

        for (int i = 0; i < 100; i++) {
            gsrArray.add(ThreadLocalRandom.current().nextInt(-5, 5 + 1) + 23);
        }

        for (int i = 0; i < 100; i++) {
            gsrArray.add(ThreadLocalRandom.current().nextInt(-3, 3 + 1) + 15);
        }

        for (int i = 0; i < 100; i++) {
            gsrArray.add(ThreadLocalRandom.current().nextInt(-3, 3 + 1) + 11);
        }

        return gsrArray;
    }

    public Template buildTemplate() {
        Template t = new Template();
        hr = generateHR();
        gsr = generateGSR();
        int i = 0;

        ArrayList<Integer> hrAv = new ArrayList<>();
        ArrayList<Integer> gsrAv = new ArrayList<>();

        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < 20000) {
            hrAv.add(hr.get(i));
            gsrAv.add(gsr.get(i));
            i++;
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        t.setHrAverage(getAverage(hrAv));
        t.setGsrAverage(getAverage(gsrAv));
        t.setHrDispersion(getDispersion(hrAv));
        t.setGsrDispersion(getDispersion(gsrAv));

        System.out.println(t);

        return t;
    }

    public Integer getAverage(ArrayList<Integer> list){
        int sum = 0;

        for (int j = 0; j < list.size(); j++) {
            sum += list.get(j);
        }
        return sum/list.size();
    }

    public Double getDispersion(ArrayList<Integer> list) {
        Double dispersion;
        Map<Integer, Double> map = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            int item = list.get(i);
            if (map.containsKey(item)) {
                Double count = map.remove(item);
                count += 1*1.0/list.size();
                map.put(item, count);
            }else {
                map.put(item, 1*1.0/list.size());
            }

        }

        double expectedValue = 0.0;
        for (Map.Entry<Integer, Double> pair: map.entrySet()) {
            expectedValue += pair.getKey() * pair.getValue();
        }

        double expectedValue2 = 0.0;
        for (Map.Entry<Integer, Double> pair: map.entrySet()) {
            expectedValue2 += Math.pow(pair.getKey(), 2) * pair.getValue();
        }

        dispersion = expectedValue2 - Math.pow(expectedValue, 2);

        return dispersion;
    }

}
