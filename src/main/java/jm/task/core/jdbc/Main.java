package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Aryna", "Sabalenka", (byte) 23);
        userService.saveUser("Maria", "Sharapova", (byte) 18);
        userService.saveUser("Elena", "Rybakina", (byte) 20);
        userService.saveUser("Serena", "Williams", (byte) 22);
        List<User> users = userService.getAllUsers();
        for(User user : users){
            System.out.println(user.toString());;
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
