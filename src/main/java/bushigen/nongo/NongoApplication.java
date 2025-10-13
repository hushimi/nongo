package bushigen.nongo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(
    title = "nongo for Bushigen",
    description = "Management system for Bushigen family",
    version = "1.0.0"
))
@SpringBootApplication
@MapperScan("bushigen.nongo.dao")
public class NongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(NongoApplication.class, args);
	}

}
