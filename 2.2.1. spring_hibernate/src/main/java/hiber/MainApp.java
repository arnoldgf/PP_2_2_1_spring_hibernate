package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);
      UserService userService = context.getBean(UserService.class);

      //Можно убрать и пользователи будут копиться каждый запуск мейна, а не записываться по новой с 1 id.
      userService.clearAllData();

      userService.add(new User("Саня", "Булкин", "user1@mail.ru", new Car("Tesla", 1)));
      userService.add(new User("Димас", "Батайский", "user2@mail.ru", new Car("Mercedes", 3)));
      userService.add(new User("Паша", "Техник", "user3@mail.ru", new Car("Audi", 5)));
      userService.add(new User("Гена", "Букин", "user4@mail.ru", new Car("BMW", 7)));

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println("Car = " + (user.getCar() != null ? user.getCar().getModel() + " (Series: " + user.getCar().getSeries() + ")" : "No car"));
         System.out.println();
      }

      User use = userService.getUserByCar("BMW", 7);
      if (use != null) {
         System.out.println("Пользователь владеющий Бэхой: " + use.getFirstName() + " " + use.getLastName());
      } else {
         System.out.println("Пользователь с данным автомобилем не найден!");
      }

      context.close();
   }
}
