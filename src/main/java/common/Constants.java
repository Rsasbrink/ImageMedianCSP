package common;

public interface Constants {

    String URL = "tcp://localhost:61616";
    String extension = "png";
    String queueName = "QUEUE.IMAGEPARTS";
    String input = "files/input1.png";
    String output = "files/output1.png";
    Image image = new Image(input, output, extension);
    Integer packagesAmount = 100;
    Integer consumerAmount = 25;

}
