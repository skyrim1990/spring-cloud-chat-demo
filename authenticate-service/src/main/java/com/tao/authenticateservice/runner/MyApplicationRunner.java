package com.tao.authenticateservice.runner;

import com.tao.authenticateservice.model.Role;
import com.tao.authenticateservice.model.User;
import com.tao.authenticateservice.payload.RegistPayload;
import com.tao.authenticateservice.service.RoleService;
import com.tao.authenticateservice.service.SessionService;
import com.tao.authenticateservice.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class MyApplicationRunner implements ApplicationRunner {

  @Autowired
  private SessionService sessionService;

  @Autowired
  private UserService userService;

  @Autowired
  private RoleService roleService;



  @Override
  public void run(ApplicationArguments args) throws Exception {

    if(roleService.countRoles() == 0) {
      Role customer = new Role();
      customer.setName("customer");
      customer.setCount(0);
      roleService.insertRole(customer);

      Role admin = new Role();
      admin.setName("admin");
      admin.setCount(0);
      roleService.insertRole(admin);
    }


    if(userService.countUsers() == 0) {

      RegistPayload admin = new RegistPayload();
      admin.setAge(100);      
      admin.setGender(1);
      admin.setPassword("123456");
      admin.setUsername("系统管理员");
      admin.setPhonenumber("17875462790");

      sessionService.regist(admin);

      Role adminRole = roleService.findRoleByName("admin");

      User user = userService.findUserByPhonenumber("17875462790");
      userService.addRole(user.getId(), adminRole.getId());

      RegistPayload user1 = new RegistPayload();
      user1.setAge(100);      
      user1.setGender(1);
      user1.setPassword("123456");
      user1.setUsername("张三");
      user1.setPhonenumber("17875462791");

      sessionService.regist(user1);

      RegistPayload user2 = new RegistPayload();
      user2.setAge(100);      
      user2.setGender(1);
      user2.setPassword("123456");
      user2.setUsername("李四");
      user2.setPhonenumber("17875462792");

      sessionService.regist(user2);

      RegistPayload user3 = new RegistPayload();
      user3.setAge(100);      
      user3.setGender(1);
      user3.setPassword("123456");
      user3.setUsername("王五");
      user3.setPhonenumber("17875462793");

      sessionService.regist(user3);

    }
  

    
  }
  
}
