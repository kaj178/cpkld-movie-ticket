package com.cpkld;

// import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.ApplicationContext; 
// import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// import springfox.documentation.swagger2.annotations.EnableSwagger2;

// import com.cpkld.model.entity.Role;
// import com.cpkld.service.RoleService;

@SpringBootApplication //(exclude = {DataSourceAutoConfiguration.class })
public class MovieTicketApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieTicketApplication.class, args);

        // ApplicationContext context =
        // SpringApplication.run(MovieTicketApplication.class, args);

        // RoleService roleBean = context.getBean(RoleService.class);
        // List<Role> list = roleBean.getAllRole();
        // list.stream()
        // .forEach(System.out::println);
    }

}
