package nl.hva.pc.part2.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hva.pc.part1.common.Constants;
import nl.hva.pc.part2.task.GregorySeriesParams;
import nl.hva.pc.part2.task.GregorySeriesReturn;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;
import java.util.Random;

// TODO 3.1. Take a look at the client code
// TODO 3.3. What is the message body format?
// TODO 3.4. Run the client, what happens?
// TODO 3.5. Run different clients with different loop counts
public class GregorySeriesClient {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) {

        // Getting the loop count
        int loopCount = getLoopCount(args);
        System.out.println("Running with loop count = " + loopCount);

        try {
            // Generating a certain randomness for the exercise
            Thread.sleep(new Random().nextInt(5000));

            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Constants.URL);

            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create destinations
            Destination in = session.createQueue(Constants.IN);
            Destination out = session.createQueue(Constants.OUT);

            // Create a MessageProducer
            MessageProducer producer = session.createProducer(in);

            // Creating the parameter object
            GregorySeriesParams parms = new GregorySeriesParams(loopCount);

            // Creating a message
            TextMessage message = createTextMessage(session,parms);

            // Tell the producer to send the message
            producer.send(message);

            // Generating a certain randomness for the exercise
            Thread.sleep(new Random().nextInt(5000));

            // Wait for the returning message
            MessageConsumer consumer = session.createConsumer(out,"JMSCorrelationID='" +
                                    + parms.getLoopCount() + "'");
            Message returnMessage = consumer.receive();

            if (returnMessage instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) returnMessage;
                String text = textMessage.getText();

                GregorySeriesReturn result = parseReturnMessage(text);
                System.out.println(result);

            } else {
                throw new IllegalArgumentException("Unexpected message " + message);
            }

            // Clean up
            session.close();
            connection.close();
        }
        catch (JMSException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Transforming the JSON body to object
     * @param text JSON
     * @return GregorySeriesReturn object
     * @throws IOException
     */
    private static GregorySeriesReturn parseReturnMessage(String text) throws IOException {
        return MAPPER.readValue(text,GregorySeriesReturn.class);
    }

    private static int getLoopCount(String[] args) {

        int loopCount = new Random().nextInt(6000);
        return loopCount;
    }

    public static TextMessage createTextMessage(Session session,GregorySeriesParams parms) throws JMSException, JsonProcessingException {
        String jsonString = MAPPER.writeValueAsString(parms);
        TextMessage msg = session.createTextMessage(jsonString);
        msg.setJMSCorrelationID(Integer.toString(parms.getLoopCount()));
        return msg;
    }

}
