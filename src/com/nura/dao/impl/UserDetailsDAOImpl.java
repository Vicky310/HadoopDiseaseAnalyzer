/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.dao.impl;

import com.nura.dao.UserDetailsDAO;
import com.nura.entity.UserDetails;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.Session;

/**
 *
 * @author ArunRamya
 */
public class UserDetailsDAOImpl implements UserDetailsDAO {

    private Session session;
    private Transaction tx;

    @Override
    public UserDetails getUserDtls(long userId) {
        UserDetails _userDtls = null;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            String hql = "From UserDetails where id =:id";
            Query query = session.createQuery(hql);
            query.setParameter("id", userId);
            _userDtls = (UserDetails) query.list().get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return _userDtls;
    }

    @Override
    public boolean saveUserDtls(UserDetails _userDtls) {
        boolean saved = false;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(_userDtls);
            session.flush();
            tx.commit();
            saved = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            System.out.println("User details saved status:-" + saved);
        }
        return saved;
    }

    @Override
    public UserDetails validateUserDetails(String userName, String password) {
        UserDetails _userDtls = null;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            String hql = "From UserDetails where userName =:userName and password =:password";
            Query query = session.createQuery(hql);
            query.setParameter("userName", userName);
            query.setParameter("password", password);
            _userDtls = (UserDetails) query.list().get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return _userDtls;
    }

    @Override
    public UserDetails getRandUserBsdOnDis(String diseaseName) {
        UserDetails _usrDtls = null;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            System.out.println("Disease sel = " + diseaseName);
            String hql = "From UserDetails where specialist =:specialist order by rand()";
            Query query = session.createQuery(hql);
            query.setParameter("specialist", diseaseName);
            _usrDtls = (UserDetails) query.list().get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return _usrDtls;
    }

    public static void main(String[] args) {
        UserDetailsDAOImpl _impl = new UserDetailsDAOImpl();
        UserDetails _usr = _impl.getRandUserBsdOnDis("Tuberculosis");
        System.out.println(_usr.getUserName());
    }
}
