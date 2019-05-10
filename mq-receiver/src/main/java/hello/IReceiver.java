package hello;

public interface IReceiver {
    void receiveMessage(String message) throws InterruptedException;
}
