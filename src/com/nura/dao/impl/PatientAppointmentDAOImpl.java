/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.dao.impl;

import com.nura.dao.PatientAppointmentDAO;
import com.nura.entity.PatientAppointment;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ArunRamya
 */
public class PatientAppointmentDAOImpl implements PatientAppointmentDAO {

    private Session session;
    private Transaction tx;

    @Override
    public boolean updatePatientAppointment() {
        boolean saved = false;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            String hql = "UPDATE PatientAppointment set status = 'PROCESSED'";
            Query query = session.createQuery(hql);
            query.executeUpdate();
            tx.commit();
            saved = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            System.out.println("Saved status:-" + saved);
        }
        return saved;
    }

    @Override
    public boolean savePatientAppointment(PatientAppointment _patAppointment) {
        boolean saved = false;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(_patAppointment);
            session.flush();
            tx.commit();
            saved = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            System.out.println("Saved status:-" + saved);
        }
        return saved;
    }

    @Override
    public List<PatientAppointment> getPatientAppointment() {
        List<PatientAppointment> patList = null;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            String hql = "From PatientAppointment where status ='PENDING'";
            Query query = session.createQuery(hql);
            patList = query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return patList;
    }

    public static void main(String[] args) {
        new PatientAppointmentDAOImpl().updatePatientAppointment();
    }
}
