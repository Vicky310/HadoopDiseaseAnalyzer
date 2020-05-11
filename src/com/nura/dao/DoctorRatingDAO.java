/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.dao;

import com.nura.entity.DoctorRating;

/**
 *
 * @author ArunRamya
 */
public interface DoctorRatingDAO {

    public boolean saveRating(DoctorRating _docRating, String feed);
    
    public DoctorRating getBstDocBsdOnDiseaseName(String diseaseName);

}
