import common.Constants;
import common.ImagePart;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Arrays;

public class MedianConsumer {

    // TODO 2.1. Take a look at the consumer source-code

    // TODO 2.3. Run the consumer.
    // What's happened? Take a look at the queues view on the activeMQ console.
    // http://localhost:8161

    // TODO 2.3.1. Is it possible specify a timeout when receiving messages?
    // The idea is leaving after some amount of time

    public static void main(String[] args) {
        try {

            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Constants.URL);
            connectionFactory.setTrustedPackages(Arrays.asList("java.lang", "common"));
            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue("QUEUE.imageParts");

            // Create a MedianConsumer from the Session to the Topic or Queue
            javax.jms.MessageConsumer consumer = session.createConsumer(destination);

            // Wait for a message
            ObjectMessage message = (ObjectMessage) consumer.receive();

            if (message instanceof ObjectMessage) {
                ObjectMessage textMessage = message;
                ObjectMessage text = textMessage;
                System.out.println("Received: " + text);
            } else {
                System.out.println("Received: " + message);
            }

            consumer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }

    }
}
