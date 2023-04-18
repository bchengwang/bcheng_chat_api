import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yeauty.annotation.EnableWebSocket;

/**
 * @Author 北橙
 * @Create 2023/4/18
 * @Description TODO
 * @Version 1.0
 */
@EnableRabbit
@EnableWebSocket
@SpringBootApplication
public class RSSServerMain {
    public static void main(String[] args) {
        SpringApplication.run(RSSServerMain.class, args);
    }
}
