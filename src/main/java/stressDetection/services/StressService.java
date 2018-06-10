package stressDetection.services;

import org.springframework.stereotype.Service;
import stressDetection.models.Template;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class StressService {

    private Integer hr = 0;
    private Integer gsr = 0;

    private Boolean readyToCheck = false;

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
        readyToCheck = true;
    }

    public int findStress() {

        if (readyToCheck) {
            return getStressLevel(template, hr, gsr);
        }else return 0;
    }

    private int getStressLevel(Template template, double hrVal, double gsrVal) {
        int stressLevel = 0;
        double percentage = getPercent(hrVal, template.getHrAverage() + template.getHrDispersion());

        stressLevel += checkHRStress(percentage);

        percentage = getPercent(gsrVal, template.getGsrAverage() - template.getGsrDispersion());

        stressLevel += checkGSRStress(percentage);

        return stressLevel;

    }

    private int checkHRStress(double percentage) {
        if (percentage >= 1 && percentage < 5) {
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
        if (percentage <= -2 && percentage > -5) {
            return 1;
        } else if (percentage <= -5 && percentage > -10) {
            return 2;
        } else if (percentage <= -10 && percentage > -20) {
            return 3;
        } else if (percentage <= -20 && percentage > -30) {
            return 4;
        } else if (percentage <= -30) {
            return 5;
        } else return 0;

    }

    private double getPercent(Double current, Double template) {
        return ((current * 100) / template) - 100;
    }

    public Template getTemplate() {
        return template;
    }

    public Integer getHr() {
        return hr;
    }

    public void setHr(Integer hr) {
        this.hr = hr;
    }

    public Integer getGsr() {
        return gsr;
    }

    public void setGsr(Integer gsr) {
        this.gsr = gsr;
    }
}
