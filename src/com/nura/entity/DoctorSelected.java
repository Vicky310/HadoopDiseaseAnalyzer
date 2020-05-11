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
@Table(name = "doc_sel")
public class DoctorSelected implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private long seq;
    @Column(name = "token_id")
    private String tokenId;
    @Column(name = "doc_sel")
    private String docSelected;
    @Column(name = "dis_type")
    private String diesaeseType;
    @Column(name = "doc_id")
    private long docId;
    @Column(name = "match_count")
    private int matchCount;
    @Column(name = "status")
    private String status;
    
    public DoctorSelected(){
        
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
     * @return the tokenId
     */
    public String getTokenId() {
        return tokenId;
    }

    /**
     * @param tokenId the tokenId to set
     */
    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    /**
     * @return the docSelected
     */
    public String getDocSelected() {
        return docSelected;
    }

    /**
     * @param docSelected the docSelected to set
     */
    public void setDocSelected(String docSelected) {
        this.docSelected = docSelected;
    }

    /**
     * @return the diesaeseType
     */
    public String getDiesaeseType() {
        return diesaeseType;
    }

    /**
     * @param diesaeseType the diesaeseType to set
     */
    public void setDiesaeseType(String diesaeseType) {
        this.diesaeseType = diesaeseType;
    }

    /**
     * @return the docId
     */
    public long getDocId() {
        return docId;
    }

    /**
     * @param docId the docId to set
     */
    public void setDocId(long docId) {
        this.docId = docId;
    }

    /**
     * @return the matchCount
     */
    public int getMatchCount() {
        return matchCount;
    }

    /**
     * @param matchCount the matchCount to set
     */
    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
}
