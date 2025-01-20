package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   public void clearUsers() {
      sessionFactory.getCurrentSession().createQuery("delete from User").executeUpdate();
   }

   @Override
   public void clearCars() {
      sessionFactory.getCurrentSession().createQuery("delete from Car").executeUpdate();
   }

   @Override
   public void resetAutoIncrement(String tableName) {
      String query = String.format("ALTER TABLE %S AUTO_INCREMENT = 1", tableName);
      sessionFactory.getCurrentSession().createNativeQuery(query).executeUpdate();
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      return sessionFactory.getCurrentSession().createQuery("from User").getResultList();
   }

   @Override
   public User getUserByCar(String model, int series) {
      String hql = "FROM User u WHERE u.car.model = :model AND u.car.series = :series";
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(hql, User.class);
      query.setParameter("model", model);
      query.setParameter("series", series);
      return ((Query<User>) query).uniqueResult();
   }

}
