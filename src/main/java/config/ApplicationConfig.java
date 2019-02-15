package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"dao"})
@Import({DBConfig.class}) //설정 파일을 여러개로 나눠서 작성한 뒤, @import()로 합칠 수 있음
public class ApplicationConfig {

}
