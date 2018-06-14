package common;

public interface Constants {

    String URL = "tcp://localhost:61616";
    String extension = "png";
    String input = "files/input1.png";
    String output = "files/output1.png";
    Image image = new Image(input, output, extension);
    Integer consumerAmount = 2;
    Integer packagesAmount = consumerAmount + 1;
    Boolean queueIsEmpty = false;

}
