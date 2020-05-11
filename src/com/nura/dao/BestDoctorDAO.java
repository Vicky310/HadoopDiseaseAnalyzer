/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.dao;

import com.nura.entity.BestDoctor;

/**
 *
 * @author ArunRamya
 */
public interface BestDoctorDAO {

    public boolean saveBestDocDAO(BestDoctor _bstDoc);

    public BestDoctor getBestDocDtls(String diseaseName);

}
