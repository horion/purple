package challenger.purple.queue;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component
public class ExecutorManager {
    private final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(500);


    public void addQueue(Runnable r){
        this.queue.add(r);
    }

    private Runnable consumeQueue() throws InterruptedException {
        return this.queue.take();
    }

    @PostConstruct
    private void executor(){
        Thread t = new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    consumeQueue().run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

}
