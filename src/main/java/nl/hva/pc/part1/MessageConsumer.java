package nl.hva.pc.part1;

import nl.hva.pc.part1.common.Constants;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MessageConsumer {

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

            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue("QUEUE.MESSAGE");

            // Create a MessageConsumer from the Session to the Topic or Queue
            javax.jms.MessageConsumer consumer = session.createConsumer(destination);

            // Wait for a message
            Message message = consumer.receive();

            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
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
