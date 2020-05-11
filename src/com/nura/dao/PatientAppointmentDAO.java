/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.dao;

import com.nura.entity.PatientAppointment;
import java.util.List;

/**
 *
 * @author ArunRamya
 */
public interface PatientAppointmentDAO {

    public boolean savePatientAppointment(PatientAppointment _patAppointment);

    public List<PatientAppointment> getPatientAppointment();
    
    public boolean updatePatientAppointment();

}
