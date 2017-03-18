package apacheCamel.apacheCamel;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.PropertyConfigurator;

public class CamelHelloWorldExample {
	public static void main(String[] args) throws Exception {
		String log4jConfPath = "C:/Users/dexter/Documents/WORK/workspace/apacheCamel/log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
		
		CamelContext context = new DefaultCamelContext();
		try {
			context.addComponent("activemq",
					ActiveMQComponent.activeMQComponent("vm://localhost?broker.persistent=false"));
			context.addRoutes(new RouteBuilder() {
				@Override
				public void configure() throws Exception {
					from("activemq:queue:test.queue").to("stream:out");
				}
			});
			ProducerTemplate template = context.createProducerTemplate();
			context.start();
			template.sendBody("activemq:test.queue", "Hello World");
			Thread.sleep(2000);
		} finally {
			context.stop();
		}
	}
}