package nl.hva.pc.part2.calculator;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hva.pc.part1.common.Constants;
import nl.hva.pc.part2.task.GregorySeriesParams;
import nl.hva.pc.part2.task.GregorySeriesReturn;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

// TODO 3.2 Take a look at the processor code
// TODO 3.3 Run the processor. What's happened
// TODO 3.6 Run the processor several times and check if the client got the proper result (check if the answer has the same loop count)
// TODO 3.7 You will find a side effect in 3.6: Fix the code
//          Hints: Study the following methods
//                  Message.setJMSCorrelationID
//                  Session.createConsumer using filters
public class GregorySeriesProcessor {

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
            Destination in = session.createQueue(Constants.IN);
            Destination out = session.createQueue(Constants.OUT);


            // Create a MessageConsumer from the Session to the Topic or Queue
            MessageConsumer consumer = session.createConsumer(in);

            // Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(out);

            ObjectMapper mapper = new ObjectMapper();

                // Wait for a message
                Message message = consumer.receive();

                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    String text = textMessage.getText();

                    GregorySeriesParams params = mapper.readValue(text,GregorySeriesParams.class);

                    GregorySeriesCalculator calculator = new GregorySeriesCalculator();
                    GregorySeriesReturn result = calculator.execute(params);

                    TextMessage returnMessage = session.createTextMessage(mapper.writeValueAsString(result));
                    returnMessage.setJMSCorrelationID(message.getJMSCorrelationID());
                    producer.send(returnMessage);
                    System.out.println("message sent: " + returnMessage);

                } else {
                    throw new IllegalArgumentException("Unexpected message " + message);
                }

            consumer.close();
            session.close();
            connection.close();
        } catch (JMSException|IOException e) {
            throw new RuntimeException(e);
        }

    }
}
