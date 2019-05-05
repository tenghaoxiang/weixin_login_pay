package top.haibaraai.wx_login_pay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("top.haibaraai.wx_login_pay.mapper")
@EnableTransactionManagement
public class WxLoginPayApplication {

	public static void main(String[] args) {
		SpringApplication.run(WxLoginPayApplication.class, args);
	}

}
