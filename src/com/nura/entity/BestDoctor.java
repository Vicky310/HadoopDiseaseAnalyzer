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
@Table(name = "best_doc")
public class BestDoctor implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private long seq;
    @Column(name = "doc_name")
    private String docName;
    @Column(name = "doc_id")
    private String docId;
    @Column(name = "disease_name")
    private String diseaseName;
    @Column(name = "positivie_feed")
    private long posFeeds;
    @Column(name = "neg_feed")
    private long negFeeds;
    
    public BestDoctor(){
        
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
     * @return the docName
     */
    public String getDocName() {
        return docName;
    }

    /**
     * @param docName the docName to set
     */
    public void setDocName(String docName) {
        this.docName = docName;
    }

    /**
     * @return the docId
     */
    public String getDocId() {
        return docId;
    }

    /**
     * @param docId the docId to set
     */
    public void setDocId(String docId) {
        this.docId = docId;
    }

    /**
     * @return the posFeeds
     */
    public long getPosFeeds() {
        return posFeeds;
    }

    /**
     * @param posFeeds the posFeeds to set
     */
    public void setPosFeeds(long posFeeds) {
        this.posFeeds = posFeeds;
    }

    /**
     * @return the negFeeds
     */
    public long getNegFeeds() {
        return negFeeds;
    }

    /**
     * @param negFeeds the negFeeds to set
     */
    public void setNegFeeds(long negFeeds) {
        this.negFeeds = negFeeds;
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
}
