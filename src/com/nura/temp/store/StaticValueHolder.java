/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.temp.store;

import com.nura.entity.DiseaseMaster;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ArunRamya
 */
public class StaticValueHolder {

    private static Map<String, List<String>> storeVal = null;

    public static Map<String, List<String>> getVal() {
        if (storeVal == null) {
            storeVal = new HashMap<String, List<String>>();
            List<DiseaseMaster> getDiseaseList = new com.nura.dao.impl.DiseaseDtlsDAOImpl().getDiseaseList();
            for (DiseaseMaster dis : getDiseaseList) {
                System.out.println(dis.getDiseaseName());
                List<com.nura.entity.DiseaseSymptoms> disDescList = new com.nura.dao.impl.DiseaseSymptomsDAOImpl().getDiseaseSympBsdOnDisName(dis.getDiseaseName());
                List<String> disSymp = new ArrayList<>();
                for (com.nura.entity.DiseaseSymptoms diSymp : disDescList) {
                    disSymp.add(diSymp.getDiseaseSymptoms());
                }
                storeVal.put(dis.getDiseaseName(), disSymp);
            }
            System.out.println("Map val rec=>" + storeVal);
            return storeVal;
        } else {
            return storeVal;
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            getVal();
        }
    }
}
