import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

public class AuthWriteWorker extends WriteWorker {
  
  public AuthWriteWorker(NIOServerSASL mainObject) {
    super(mainObject);
  }
  
  public void run() {
    while(true) {
      System.out.println("AuthWriteWorker.run(): waiting for a client to appear in the write queue.");
      // blocks waiting for items to appear on the write queue.
      Pair<SelectionKey,String> messageTuple = main.takeFromWriteQueue();
      SelectionKey writeToMe = messageTuple.first;
      String message = messageTuple.second;
      System.out.println("AuthWriteWorker:run(): writing message: '" + message +"' to " + main.clientNick.get(writeToMe));
      main.WriteToClientByNetwork(writeToMe,message);
    }
  }
}

