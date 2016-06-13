package igniteTest;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.function.Supplier;

/**
 * Created by Razmik.Mkrtchyan on 5/30/2016.
 */
public class IgniteTest {

	public static void main(String[] args) {
		String igniteConfigPath = "ignite-config.xml";
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		IgniteCacheContainer igniteCacheContainer = ctx.getBean("igniteCacheContainer", IgniteCacheContainer.class);

	}

	public static <T> void durationOfFunctionByMilliseconds(Supplier<T> supplier) {
		long lStartTime = System.currentTimeMillis();
		supplier.get();
		long lEndTime = System.currentTimeMillis();
		long difference = lEndTime - lStartTime;
		System.out.println("Elapsed milliseconds: " + difference);
	}
}
