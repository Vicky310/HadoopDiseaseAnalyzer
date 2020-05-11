/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.dao;

import com.nura.entity.UserDetails;

/**
 *
 * @author ArunRamya
 */
public interface UserDetailsDAO {

    public boolean saveUserDtls(UserDetails _userDtls);

    public UserDetails validateUserDetails(String userName, String password);
    
     public UserDetails getUserDtls(long userId);
     
     public UserDetails getRandUserBsdOnDis(String diseaseName);

}
