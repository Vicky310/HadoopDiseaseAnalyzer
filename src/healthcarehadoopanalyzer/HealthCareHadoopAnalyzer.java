/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package healthcarehadoopanalyzer;

import com.google.gson.Gson;
import com.nura.entity.PatientAppointment;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ArunRamya
 */
public class HealthCareHadoopAnalyzer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        PatientAppointment patApt = new PatientAppointment();
        patApt.setContactNumber("+9464");
        List<String> getList = new ArrayList<>();
        getList.add("sdkjds");
        getList.add("sddsl");
        patApt.setSymptoms(getList);
        Gson gson = new Gson();
        System.out.println(gson.toJson(patApt));
        
        PatientAppointment patApt1 = gson.fromJson(gson.toJson(patApt), PatientAppointment.class);
        System.out.println(patApt1.getSymptoms().get(0));
    }

}
