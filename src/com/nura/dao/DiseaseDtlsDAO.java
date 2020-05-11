/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.dao;

import com.nura.entity.DiseaseMaster;
import java.util.List;

/**
 *
 * @author ArunRamya
 */
public interface DiseaseDtlsDAO {
    
    public boolean saveDiseaseDtls(DiseaseMaster _disDtls);
    public boolean diseaseExist(String diseaseName);
    public List<DiseaseMaster> getDiseaseList();
    
}
