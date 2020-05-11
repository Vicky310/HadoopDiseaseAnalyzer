/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.dao.impl;

import com.nura.dao.DiseaseSymptomsDAO;
import com.nura.entity.DiseaseSymptoms;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ArunRamya
 */
public class DiseaseSymptomsDAOImpl implements DiseaseSymptomsDAO {

    private Session session;
    private Transaction tx;

    @Override
    public boolean saveDiseaseSymptoms(DiseaseSymptoms _disSymp) {
        boolean saved = false;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(_disSymp);
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
    public boolean getDiseaseSymptomBsdOnNameNSymptom(String diseaseName, String diseaseSymp) {
        boolean exist = false;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            String hql = "From DiseaseSymptoms where diseaseName =:diseaseName and diseaseSymptoms =:diseaseSymptoms";
            Query query = session.createQuery(hql);
            query.setParameter("diseaseName", diseaseName);
            query.setParameter("diseaseSymptoms", diseaseSymp);
            if (query.list().size() > 0) {
                exist = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            System.out.println("Disease symptoms details saved status:-" + exist);
        }
        return exist;
    }

    @Override
    public List<DiseaseSymptoms> getDiseaseSympBsdOnDisName(String diseaseName) {
        List<DiseaseSymptoms> _disSymList = null;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            String hql = "From DiseaseSymptoms where diseaseName =:diseaseName";
            Query query = session.createQuery(hql);
            query.setParameter("diseaseName", diseaseName);
            _disSymList = query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return _disSymList;
    }

    @Override
    public List<String> getUniqueSymptoms() {
        List<String> symptoms = null;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            String hql = "select distinct disease_symptoms from dis_symptoms";
            SQLQuery query = session.createSQLQuery(hql);
            //query.addEntity(String.class);
            symptoms = query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return symptoms;
    }

    public static void main(String[] args) {
        DiseaseSymptomsDAOImpl _disImpl = new DiseaseSymptomsDAOImpl();
        List<String> getVal = _disImpl.getUniqueSymptoms();
        System.out.println("Val=>" + getVal);
    }
}
