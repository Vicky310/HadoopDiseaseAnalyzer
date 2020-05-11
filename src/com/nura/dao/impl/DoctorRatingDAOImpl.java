/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.dao.impl;

import com.nura.dao.DoctorRatingDAO;
import com.nura.entity.DoctorRating;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ArunRamya
 */
public class DoctorRatingDAOImpl implements DoctorRatingDAO {

    private Session session;
    private Transaction tx;

    @Override
    public boolean saveRating(DoctorRating _docRating, String feed) {
        boolean saved = false;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            String hql = "From DoctorRating where docId =:docId";
            Query query = session.createQuery(hql);
            query.setParameter("docId", _docRating.getDocId());
            if (query.list().size() == 0) {
                session.save(_docRating);
            } else {
                DoctorRating _docRate = (DoctorRating) query.list().get(0);;
                if (feed.equals("p")) {
                    int count = _docRate.getPosCount() + 1;
                    hql = "update DoctorRating set posCount =" + (count);
                } else {
                    int count = _docRate.getNegCount() + 1;
                    hql = "update DoctorRating set negCount =" + (count);
                }
                query = session.createQuery(hql);
                int rowsUpdated = query.executeUpdate();
                System.out.println("Rows updated =>" + rowsUpdated);

            }
            session.flush();
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return saved;
    }

    @Override
    public DoctorRating getBstDocBsdOnDiseaseName(String diseaseName) {
        DoctorRating docRate = null;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            Criteria cr = session.createCriteria(DoctorRating.class);
            cr.add(Restrictions.like("diseaseName", diseaseName));
            cr.addOrder(Order.desc("posCount"));
            docRate = (DoctorRating) cr.list().get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return docRate;
    }

    public static void main(String[] args) {
        DoctorRatingDAOImpl _docImpl = new DoctorRatingDAOImpl();
        DoctorRating _docRate = _docImpl.getBstDocBsdOnDiseaseName("Tuberculosis");
        System.out.println(_docRate.getPosCount());
    }
}
