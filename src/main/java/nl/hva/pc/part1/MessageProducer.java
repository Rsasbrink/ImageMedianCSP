package nl.hva.pc.part1;

import nl.hva.pc.part1.common.Constants;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Date;

public class MessageProducer {

    // TODO 2.2: Take a look at the producer code
    // TODO 2.4: Run the producer code. What's happened. Take a look at the client execution
    // TODO 2.5: Close the consumer and run many producers, what's happened
    // TODO 2.6: Run some consumers, what happens?
    // TODO 2.7: Change the code of the producer and the consumer to use topics instead of queues. What's happened?
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

            // Create a MessageProducer from the Session to the Topic or Queue
            javax.jms.MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);

            // Create a messages
            String text = "Hello world at " + new Date();
            TextMessage message = session.createTextMessage(text);

            // Tell the producer to send the message
            System.out.println("Sent message: "+ message.hashCode() + " : " + Thread.currentThread().getName());
            producer.send(message);

            // Clean up
            session.close();
            connection.close();
        }
        catch (JMSException e) {
            throw new RuntimeException(e);
        }


    }

}
