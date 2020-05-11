/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ArunRamya
 */
@Entity
@Table(name = "dis_symptoms")
public class DiseaseSymptoms implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private long seq;
    @Column(name = "disease_name")
    private String diseaseName;
    @Column(name = "disease_symptoms")
    private String diseaseSymptoms;
    
    public DiseaseSymptoms(){
        
    }

    /**
     * @return the seq
     */
    public long getSeq() {
        return seq;
    }

    /**
     * @param seq the seq to set
     */
    public void setSeq(long seq) {
        this.seq = seq;
    }

    /**
     * @return the diseaseName
     */
    public String getDiseaseName() {
        return diseaseName;
    }

    /**
     * @param diseaseName the diseaseName to set
     */
    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    /**
     * @return the diseaseSymptoms
     */
    public String getDiseaseSymptoms() {
        return diseaseSymptoms;
    }

    /**
     * @param diseaseSymptoms the diseaseSymptoms to set
     */
    public void setDiseaseSymptoms(String diseaseSymptoms) {
        this.diseaseSymptoms = diseaseSymptoms;
    }
}
