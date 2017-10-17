package com.blibli.distro_pos;

import com.blibli.distro_pos.DAO.UserDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.blibli.distro_pos.Controller", "com.blibli.distro_pos.DAO"})
public class DistroPosApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistroPosApplication.class, args);
		UserDao.createTableUser();
		UserDao.createTableUserRole();
	}
}
