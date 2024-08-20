import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;

public class CpiSearchApplicationTests {

  static {
    Dotenv dotenv = Dotenv.configure().filename(".env.test").load();
  }

  @Test
  void contextLoads() {}
}
