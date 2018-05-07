package stressDetection.services;

import org.springframework.stereotype.Service;
import stressDetection.models.Template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class TemplateService {

    public Template buildTemplate(ArrayList<Integer> hr, ArrayList<Integer> gsr) {
        Template t = new Template();
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
