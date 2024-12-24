package hiber.service;

import hiber.dao.UserDao;
import hiber.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

   @Autowired
   private UserDao userDao;
   @Autowired
   private SessionFactory sessionFactory;

   @Transactional
   @Override
   public void add(User user) {
      userDao.add(user);
   }

   @Transactional(readOnly = true)
   @Override
   public List<User> listUsers() {
      return userDao.listUsers();
   }

   //Очистка таблицы перед добавлением
   @Override
   @Transactional
   public void clearAllData() {
      Session session = sessionFactory.getCurrentSession();
      session.createQuery("delete from User").executeUpdate();
      session.createQuery("delete from Car").executeUpdate();
      session.createNativeQuery("ALTER TABLE users AUTO_INCREMENT = 1").executeUpdate();
      session.createNativeQuery("ALTER TABLE cars AUTO_INCREMENT = 1").executeUpdate();
   }

}
