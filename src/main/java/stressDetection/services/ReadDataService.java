package stressDetection.services;

import jssc.SerialPort;
import jssc.SerialPortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stressDetection.controllers.MainController;

import java.util.*;

@Service
public class ReadDataService implements Runnable {

    StressService stressService;


    public ReadDataService(StressService stressService) {
        this.stressService = stressService;
    }

    public void run() {
        String port = "COM3";

        List<Integer> rawDataHR = new ArrayList<>();
        ArrayList<Integer> templateDataHR = new ArrayList<>();
        ArrayList<Integer> templateDataGSR = new ArrayList<>();

        Map<Date, Integer> mapHR = new HashMap<>();

        SerialPort serialPort = new SerialPort(port);
        try {
            serialPort.openPort();
            serialPort.setParams(9600, 8, 0, 0);
            serialPort.setDTR(true);
            serialPort.setRTS(true);

            for (int i = 0; i < 100; i++) {
                serialPort.readBytes();
            }

            Date start = new Date();
            int old = 1, bytesCount = 1, hr = 0, gsr = 0;
            String inputData = "";
            boolean raise = false, sw = true, templateRdy = false;
            while (true) {
                int inputBufferBytesCount = serialPort.getInputBufferBytesCount();
                if (inputBufferBytesCount > 1) {
                    inputData = serialPort.readString(bytesCount);
                    while (!inputData.contains(";")) {
                        inputData += serialPort.readString(++bytesCount);
                    }
                    bytesCount = 1;
                    inputData = inputData.replace('.', ' ').trim();
                    inputData = inputData.substring(0, inputData.lastIndexOf(";"));
                    hr = Integer.valueOf(inputData.split(":")[0]);
                    gsr = Integer.valueOf(inputData.split(":")[1]);
//                    System.out.println("HR: " + hr + " GSR: " + gsr);
                    if (hr != 0) {
//                        System.out.println("OLD: " + old + " NEW: " + hr);
                        rawDataHR.add(hr);
                        int percent = getPercent(hr, old);
                        if (percent >= 5) {
                            old = hr;
                            raise = true;
                        } else if (raise && hr > 100 && percent <= -10) {
//                            System.out.println("PEAK!!!!!!!!!!!!!!!!!!!!!");
                            raise = false;
                            mapHR.put(new Date(), hr);
                        } else if (percent <= -20) {
                            raise = false;
                            old = hr;
                        }

                        sw = !sw;

                        stressService.setGsr(gsr);
                        templateDataGSR.add(gsr);
                        sw = !sw;

                    }

                    Date finish = new Date();

                    if ((finish.getTime() - start.getTime()) / 1000 > 20) {
                        mapHR.entrySet().removeIf(entry -> (finish.getTime() - entry.getKey().getTime()) / 1000 > 20);

                        templateDataHR.add(mapHR.size() * 3);
                        stressService.setHr(mapHR.size() * 3);
                        //System.out.println("BPM: " + mapHR.size()*3);

                        if (!templateRdy && (finish.getTime() - start.getTime()) / 1000 > 40) {
                            templateRdy = true;
                            stressService.initTemplate(templateDataHR, templateDataGSR);
                        }
                    }
                }
            }
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    private static int getPercent(int current, int template) {
        return ((current * 100) / template) - 100;
    }

}
