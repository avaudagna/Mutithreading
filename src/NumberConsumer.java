import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by alan on 10/21/15.
 */
public class NumberConsumer extends Thread {

    private int _counter;
    private Queue<Integer> _queue;
    private boolean _stop;
    private AtomicInteger _max;

    NumberConsumer(Queue<Integer> queue, AtomicInteger max) {
        _max = max;
        _counter = 0;
        _stop = false;
        _queue = queue;
    }

    ;

    @Override
    public void run() {
        synchronized (_max) {
            //super.run();
            _counter = 0;
            while (!_stop) {
                //_max.decrementAndGet();
                //El problema con esta expresion no es el get, sino el <= con el int
                //en donde para los 4 threads va a tener el mismo valor en la misma "tanda" de ejecucion
                if (_max.get() <= 0) {
                    dispose();
                    break;
                }

                try {
                    System.out.println(Integer.toString(_counter) + " : " + Integer.toString(_queue.poll()));
                    _counter++;
                    //Ahora tengo que syncronizarlo con el add tambien
                    _max.decrementAndGet();
                } catch (NullPointerException e) {
                    continue;
                }

            }
        }
    }

    public void dispose() {
        _stop = true;
    }
}
