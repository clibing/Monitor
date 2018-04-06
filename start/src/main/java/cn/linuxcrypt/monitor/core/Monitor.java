package cn.linuxcrypt.monitor.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @function:
 * @author: liuboxun
 * @email: wmsjhappy@gmail.com
 * @date: 2018/4/6
 * @remark:
 * @version: 1.0
 */
public class Monitor implements AutoCloseable {
    private Logger logger = LoggerFactory.getLogger(Monitor.class);
    private WeakReference<Thread> thread;
    private String token;
    private long start;
    private long stop;

    private Monitor previous;
    private Monitor next;
    private List<Monitor> children = new ArrayList<>();

    public Monitor() {
        this.token = UUID.randomUUID().toString();
        this.start = System.nanoTime();
        this.stop = 0;
        this.thread = new WeakReference<Thread>(Thread.currentThread());
        QueryStatCollector.get().addMonitor(this);
    }



    public void stop(){
        // 判断调用stop方法是否为调用start方法同一个线程
        if(Thread.currentThread() != thread.get()){
           logger.debug("Monitor was create in {} but is stopped in {}", thread.get(), Thread.currentThread());
        }
        if(isStopped()){
            logger.debug("Monitor already stopped");
        }
        doStop();
    }

    public Monitor getPrevious() {
        return previous;
    }

    public void setPrevious(Monitor previous) {
        this.previous = previous;
    }

    public Monitor getNext() {
        return next;
    }

    public void setNext(Monitor next) {
        this.next = next;
        this.children.add(next);
    }

    public String getToken() {
        return token;
    }

    private void doStop(){
       stop = System.nanoTime();
    }

    private boolean isStopped(){
        return this.stop != 0;
    }

    @Override
    public void close() throws Exception {
        stop();
        // 当退出时，设置上一次的monitor唯一标识。
        QueryStatCollector.get().setCurrentMonitor(previous);
    }

    public long getElapsedNanos(){
        if(!isStopped()){
           logger.debug("Monitor not stopped");
           // 此处设计，当主动调用时，会触发一次停止时间记录，用于计算运行时间
           doStop();
        }
        return stop - start;
    }

    public List<Monitor> getChildren() {
        return children;
    }

    public boolean isEndMonitor(){
        return this.previous != null && this.next == null;
    }


}
