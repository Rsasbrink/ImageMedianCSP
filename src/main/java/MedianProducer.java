import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import common.Constants;
import common.Image;
import common.ImagePart;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.imageio.ImageIO;
import javax.jms.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class MedianProducer {

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
            Destination destination = session.createQueue("QUEUE.imageParts");

            // Create a MedianProducer from the Session to the Topic or Queue
            javax.jms.MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);


            Image image = Constants.image;
            System.out.println(image.getInput());
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

                // Create a messages
                String text = "partial: " + i;
                ObjectMessage  message = session.createObjectMessage(imagePart);

                // Tell the producer to send the message
                System.out.println("Sent message: " + message + " : " + Thread.currentThread().getName());
                producer.send(message);
            }

            if (modulo > 0) {
                Object moduloObject = new Object() {
                    int offsetHeight = packageAmount * heightPerPackage;
                    int height = imageHeight - (packageAmount * heightPerPackage);
                    int width = imageWidth;
                };
                // queue.put(moduloObject);
            }


            // Clean up
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}




