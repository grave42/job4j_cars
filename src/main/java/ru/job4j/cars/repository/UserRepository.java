package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sf;

    /**
     * Сохранить в базе.
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        Session session = null;
        try {
            session = sf.openSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    /**
     * Обновить в базе пользователя через HQL.
     * @param user пользователь.
     */
    public void update(User user) {
        Session session = null;
        try {
            session = sf.openSession();
            session.beginTransaction();
            String hql = "UPDATE User SET login = :login, password = :password WHERE id = :id";
            Query query = session.createQuery(hql)
                    .setParameter("login", user.getLogin())
                    .setParameter("password", user.getPassword())
                    .setParameter("id", user.getId());
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Удалить пользователя по id через HQL.
     * @param userId ID
     */
    public void delete(int userId) {
        Session session = null;
        try {
            session = sf.openSession();
            session.beginTransaction();
            String hql = "DELETE FROM User WHERE id = :id";
            Query query = session.createQuery(hql)
                    .setParameter("id", userId);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Список пользователь отсортированных по id.
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        Session session = null;
        List<User> users;
        try {
            session = sf.openSession();
            session.beginTransaction();
            String hql = "FROM User ORDER BY id";
            users = session.createQuery(hql, User.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return users;
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
        Session session = null;
        Optional<User> result;
        try {
            session = sf.openSession();
            session.beginTransaction();
            User user = session.get(User.class, userId);
            session.getTransaction().commit();
            result = Optional.ofNullable(user);
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        Session session = null;
        List<User> users;
        try {
            session = sf.openSession();
            session.beginTransaction();
            String hql = "FROM User WHERE login LIKE :key";
            users = session.createQuery(hql, User.class)
                    .setParameter("key", "%" + key + "%")
                    .getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return users;
    }

    /**
     * Найти пользователя по login.
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        Session session = null;
        Optional<User> result;
        try {
            session = sf.openSession();
            session.beginTransaction();
            String hql = "FROM User WHERE login = :login";
            User user = session.createQuery(hql, User.class)
                    .setParameter("login", login)
                    .uniqueResult();
            session.getTransaction().commit();
            result = Optional.ofNullable(user);
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }
}
