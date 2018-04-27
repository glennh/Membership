package org.dalgetybaysc.membership;

import java.util.Date;
import java.util.List;

import org.dalgetybaysc.membership.classes.Membership;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class DataManager {
    private static SessionFactory sessionFactory = null;
    private static String configFilePath = null;

    private static void configureSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(org.dalgetybaysc.membership.classes.Membership.class);
        if (configFilePath == null) {
            configuration.configure();
        } else {
            configuration.configure(configFilePath);
        }

        //Properties properties = configuration.getProperties();

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public static int getNumMemberships() {
        configureSessionFactory();

        Session session = null;
        int count = -1;

        try {
            session = sessionFactory.openSession();

            // Fetching saved data
            List membershipList = session.createQuery("FROM Membership WHERE status = '" + Membership.MembershipStatus.CURRENT.toString() + "'").list();
            count =  membershipList.size();
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally{
            if(session != null) session.close();
        }

        return count;
    }

    public static int getNextAvailableMembershipNumber() {
        configureSessionFactory();

        Session session = null;
        int highestCurrentMembershipNumber = -1;

        try {
            session = sessionFactory.openSession();

            List<Integer> membershipNumbers = session.createQuery("SELECT id FROM Membership ORDER BY id DESC").list();
            if (membershipNumbers.size() == 0) {
                highestCurrentMembershipNumber = 0;
            } else {
                highestCurrentMembershipNumber = membershipNumbers.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally{
            if(session != null) {
                session.close();
            }
        }

        return highestCurrentMembershipNumber + 1;
    }

    public static int addNewMembership(Integer mainMemberberId, Membership.MembershipClass memClass, Date joinedDate, String comment) {
        configureSessionFactory();

        Session session = null;
        Transaction tx = null;
        int newId = -1;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            newId = getNextAvailableMembershipNumber();
            Membership newMembership = new Membership(newId, mainMemberberId, memClass, joinedDate, comment);

            session.save(newMembership);
            session.flush();
            tx.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            assert tx != null;
            tx.rollback();

        } finally{
            if(session != null) {
                session.close();
            }
        }

        return newId;
    }

    public static Membership getMembership(int id) {
        configureSessionFactory();

        Session session = null;
        List<?> membershipList = null;

        try {
            session = sessionFactory.openSession();

            // Fetching saved data
            membershipList = session.createQuery("FROM Membership WHERE id = " + id).list();
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally{
            if(session != null) {
                session.close();
            }
        }
        if (membershipList == null || membershipList.size() == 0) {
            throw new IllegalArgumentException("No record for membership id " + id);
        }
        return (Membership) membershipList.get(0);
    }

    public static void retireMembership(int id) {
        configureSessionFactory();

        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            List<Membership> memberships = session.createQuery("FROM Membership where id = " + id).list();
            Membership membership = memberships.get(0);
            membership.setStatus(Membership.MembershipStatus.OLD);
            session.save(membership);

            session.flush();
            tx.commit();

        } catch (Exception ex) {
            ex.printStackTrace();

            assert tx != null;
            tx.rollback();
        } finally{
            if(session != null) {
                session.close();
            }
        }

    }

    public static void truncateTable(String table) {
        configureSessionFactory();

        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            session.createQuery("delete from " + table).executeUpdate();

            session.flush();
            tx.commit();

        } catch (Exception ex) {
            ex.printStackTrace();

            assert tx != null;
            tx.rollback();
        } finally{
            if(session != null) {
                session.close();
            }
        }
    }

//    public static String getConfigFilePath() {
//        return configFilePath;
//    }

    public static void setConfigFilePath(String configFilePath) {
        DataManager.configFilePath = configFilePath;
    }

}
