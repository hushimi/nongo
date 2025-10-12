package bushigen.nongo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("bushigen.nongo")
public class NongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(NongoApplication.class, args);
	}

}
