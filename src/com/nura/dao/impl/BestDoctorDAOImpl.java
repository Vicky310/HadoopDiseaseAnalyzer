/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.dao.impl;

import com.nura.dao.BestDoctorDAO;
import com.nura.entity.BestDoctor;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ArunRamya
 */
public class BestDoctorDAOImpl implements BestDoctorDAO{

    private Session session;
    private Transaction tx;
    
    @Override
    public boolean saveBestDocDAO(BestDoctor _bstDoc) {
        boolean saved = false;
        try{
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(_bstDoc);
            session.flush();
            tx.commit();
            saved = true;
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(session != null){
                session.close();
            }
        }
        return saved;
    }

    @Override
    public BestDoctor getBestDocDtls(String diseaseName) {
        BestDoctor _bstDoc = null;
        try{
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            Criteria cr = session.createCriteria(BestDoctor.class);
            cr.add(Restrictions.eq("diseaseName", diseaseName));
            cr.setProjection(Projections.max("posFeeds"));
            _bstDoc = (BestDoctor) cr.list().get(0);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(session != null){
                session.close();
            }
        }
        return _bstDoc;
    }
    
    public static void main(String[] args) {
        BestDoctorDAOImpl _impl = new BestDoctorDAOImpl();
        _impl.getBestDocDtls("Tuberclosis");
    }
}
