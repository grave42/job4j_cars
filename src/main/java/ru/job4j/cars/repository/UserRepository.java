package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     */
    public void update(User user) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Удалить пользователя по id.
     * @param userId ID
     */
    public void delete(int userId) {
        Session session = sf.openSession();
        session.beginTransaction();
        User user = new User();
        user.setId(userId);
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Список пользователь отсортированных по id.
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        Session session = sf.openSession();
        session.beginTransaction();

        String hql = "FROM User ORDER BY id";
        List<User> users = session.createQuery(hql, User.class)
                .getResultList();

        session.getTransaction().commit();
        session.close();

        return users;
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
        Session session = sf.openSession();
        session.beginTransaction();
        User result = session.get(User.class, userId);
        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(result);
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        Session session = sf.openSession();
        session.beginTransaction();

        String hql = "FROM User WHERE login LIKE :key";
        List<User> users = session.createQuery(hql, User.class)
                .setParameter("key", "%" + key + "%")
                .getResultList();

        session.getTransaction().commit();
        session.close();

        return users;
    }

    /**
     * Найти пользователя по login.
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        Session session = sf.openSession();
        session.beginTransaction();
        String hql = "FROM User WHERE login = :login";
        User result = session.createQuery(hql, User.class)
                .setParameter("login", login)
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(result);
    }
}