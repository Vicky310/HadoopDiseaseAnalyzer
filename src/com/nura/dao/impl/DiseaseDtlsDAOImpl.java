/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.dao.impl;

import com.nura.dao.DiseaseDtlsDAO;
import com.nura.entity.DiseaseMaster;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ArunRamya
 */
public class DiseaseDtlsDAOImpl implements DiseaseDtlsDAO {

    private Session session;
    private Transaction tx;

    @Override
    public boolean saveDiseaseDtls(DiseaseMaster _disDtls) {
        boolean saved = false;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(_disDtls);
            session.flush();
            tx.commit();
            saved = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            System.out.println("Disease details saved status:-" + saved);
        }
        return saved;
    }

    @Override
    public boolean diseaseExist(String diseaseName) {
        boolean exist = false;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            String hql = "From DiseaseMaster where diseaseName =:diseaseName";
            Query query = session.createQuery(hql);
            query.setParameter("diseaseName", diseaseName);
            if (query.list().size() > 0) {
                exist = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return exist;
    }

    @Override
    public List<DiseaseMaster> getDiseaseList() {
        List<DiseaseMaster> disList = null;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            String hql = "From DiseaseMaster";
            Query query = session.createQuery(hql);
            disList = query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return disList;
    }

}
