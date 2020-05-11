/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.dao;

import com.nura.entity.DiseaseSymptoms;
import java.util.List;

/**
 *
 * @author ArunRamya
 */
public interface DiseaseSymptomsDAO {

    public boolean saveDiseaseSymptoms(DiseaseSymptoms _disSymp);

    public boolean getDiseaseSymptomBsdOnNameNSymptom(String diseaseName, String diseaseSymp);
    
    public List<DiseaseSymptoms> getDiseaseSympBsdOnDisName(String diseaseName);
    
    public List<String> getUniqueSymptoms();

}
