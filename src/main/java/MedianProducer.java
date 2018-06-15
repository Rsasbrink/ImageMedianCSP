
import common.Constants;
import common.Image;
import common.ImagePart;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.imageio.ImageIO;
import javax.jms.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class MedianProducer {

    // TODO 2.2: Take a look at the producer code
    // TODO 2.4: Run the producer code. What's happened. Take a look at the client execution
    // TODO 2.5: Close the consumer and run many producers, what's happened
    // TODO 2.6: Run some consumers, what happens?
    // TODO 2.7: Change the code of the producer and the consumer to use topics instead of queues. What's happened?
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        try {
            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Constants.URL);
            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();
            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue(Constants.queueName);

            // Create a MedianProducer from the Session to the Topic or Queue
            javax.jms.MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);



            Image image = Constants.image;
            File imageFile = new File(image.getInput());
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            image.setOriginalImage(bufferedImage);
            int imageHeight = bufferedImage.getHeight();
            int imageWidth = bufferedImage.getWidth();
            int modulo;
            if (image.getImage() == null) {
                image.setImage(new BufferedImage(imageWidth, imageHeight, bufferedImage.getType()));
            }


            final int packageAmount = Constants.packagesAmount;
            final int heightPerPackage = imageHeight / packageAmount;
            modulo = imageHeight % packageAmount;
            for (int i = 0; i < packageAmount; i++) {

                final int ii = i;
                int offsetHeight = heightPerPackage * ii;
                int height = heightPerPackage;
                int width = imageWidth;
                ImagePart imagePart = new ImagePart(offsetHeight, height, width);
                ObjectMessage message = session.createObjectMessage(imagePart);

                // Tell the producer to send the message
                producer.send(message);

            }

            if (modulo > 0) {
                int offsetHeight = packageAmount * heightPerPackage;
                int height = imageHeight - (packageAmount * heightPerPackage);
                int width = imageWidth;
                ImagePart moduloPart = new ImagePart(offsetHeight, height, width);
                ObjectMessage message = session.createObjectMessage(moduloPart);
                producer.send(message);
            }


            for (int i = 0; i < Constants.consumerAmount; i++) {
                ImagePart emptyPart = new ImagePart(0, 0, 0);
                ObjectMessage message = session.createObjectMessage(emptyPart);
                producer.send(message);
                MedianConsumer.main();
            }

            // Clean up
            session.close();
            connection.close();
            System.out.println("Duration" + (System.currentTimeMillis() - startTime));

        } catch (JMSException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}





