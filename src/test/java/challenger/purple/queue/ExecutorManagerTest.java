package challenger.purple.queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExecutorManagerTest {

    ExecutorManager executorManager = new ExecutorManager();
    Runnable execute;

    @BeforeEach
    void init(){
        execute = () -> System.out.println("Pegou papai");
    }

    @Test
    void testQueue() {
        int result = executorManager.queueSize();
        assertEquals(0,result);
        executorManager.addQueue(execute);
        result = executorManager.queueSize();
        assertEquals(1,result);
    }

}