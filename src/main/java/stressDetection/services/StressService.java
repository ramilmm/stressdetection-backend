package stressDetection.services;

import org.springframework.stereotype.Service;
import stressDetection.models.Template;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class StressService {

    TemplateService templateService = new TemplateService();

    Template template;

    public ArrayList<Integer> generateHR() {
        ArrayList<Integer> hrArray = new ArrayList<>();

        for (int i = 0; i < 200; i++) {
            hrArray.add(ThreadLocalRandom.current().nextInt(-3, 3 + 1) + 68);
        }

        for (int i = 0; i < 100; i++) {
            hrArray.add(ThreadLocalRandom.current().nextInt(-10, 15 + 1) + 118);
        }

        for (int i = 0; i < 100; i++) {
            hrArray.add(ThreadLocalRandom.current().nextInt(-10, 10 + 1) + 138);
        }

        for (int i = 0; i < 100; i++) {
            hrArray.add(ThreadLocalRandom.current().nextInt(-10, 10 + 1) + 160);
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
            gsrArray.add(ThreadLocalRandom.current().nextInt(-3, 3 + 1) + 35);
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

    public void initTemplate(ArrayList<Integer> hr, ArrayList<Integer> gsr) {
        template = templateService.buildTemplate(hr, gsr);
    }

    public int findStress(ArrayList<Integer> hr, ArrayList<Integer> gsr, Integer i) {

        double hrVal = hr.get(i);
        double gsrVal = gsr.get(i);

        return getStressLevel(template, hrVal, gsrVal);
    }

    private int getStressLevel(Template template, double hrVal, double gsrVal) {
        int stressLevel = 0;
        double percentage = getPercent(hrVal, template.getHrAverage() + template.getHrDispersion());

        stressLevel += checkHRStress(percentage);

        percentage = getPercent(gsrVal, template.getGsrAverage() + template.getGsrDispersion());

        stressLevel += checkGSRStress(percentage);

        return stressLevel;

    }

    private int checkHRStress(double percentage) {
        if (percentage >= 10 && percentage < 29) {
            return 1;
        } else if (percentage >= 29 && percentage < 50) {
            return 2;
        } else if (percentage >= 50 && percentage < 71) {
            return 3;
        } else if (percentage >= 71 && percentage < 120) {
            return 4;
        } else if (percentage >= 120) {
            return 5;
        } else return 0;

    }

    private int checkGSRStress(double percentage) {
        if (percentage >= 20 && percentage < 100) {
            return 1;
        } else if (percentage >= 100 && percentage < 170) {
            return 2;
        } else if (percentage >= 170 && percentage < 250) {
            return 3;
        } else if (percentage >= 250 && percentage < 330) {
            return 4;
        } else if (percentage >= 330) {
            return 5;
        } else return 0;

    }

    private double getPercent(Double current, Double template) {
        return ((current * 100) / template) - 100;
    }

}
