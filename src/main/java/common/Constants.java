package common;

import java.awt.image.BufferedImage;

public interface Constants {

    String URL = "tcp://localhost:61616";
    String extension = "png";
    String queueName = "QUEUE.DI";
    String input = "files/input2.png";
    String output = "files/output2.png";
    Image image = new Image(input, output, extension);
    Integer packagesAmount = 3;

}
