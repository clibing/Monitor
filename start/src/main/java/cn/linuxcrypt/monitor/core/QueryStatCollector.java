package cn.linuxcrypt.monitor.core;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @function:
 * @author: liuboxun
 * @email: wmsjhappy@gmail.com
 * @date: 2018/4/6
 * @remark:
 * @version: 1.0
 */
public class QueryStatCollector {
    private static ThreadLocal<QueryStatCollector> collectors = new ThreadLocal<QueryStatCollector>() {
        @Override
        protected QueryStatCollector initialValue() {
            return new QueryStatCollector();
        }
    };
    private WeakReference<Thread> originThread;
    private ConcurrentHashMap<String, Monitor> concurrentHashMap = new ConcurrentHashMap<>();
    private Monitor currentMonitor;
    private Monitor root;

    public QueryStatCollector() {
        this.originThread = new WeakReference<Thread>(Thread.currentThread());
    }

    public static QueryStatCollector get() {
        return collectors.get();
    }

    public WeakReference<Thread> getOriginThread() {
        return originThread;
    }

    public synchronized void setCurrentMonitor(Monitor currentMonitor) {
        this.currentMonitor = currentMonitor;
    }

    /**
     * 加锁，顺序执行
     *
     * @param monitor
     */
    public synchronized void addMonitor(Monitor monitor) {
        // 将Monitor加入Map中
        concurrentHashMap.put(monitor.getToken(), monitor);
        if (currentMonitor != null) {
            currentMonitor.setNext(monitor);
            monitor.setPrevious(currentMonitor);
        } else {
            root = monitor;
        }
        this.setCurrentMonitor(monitor);
    }

    public void tree() {
        System.out.println(String.format("%duuid: %s, elapsed time: %d", 0,  root.getToken(), root.getElapsedNanos()));
        this.tree(1, root.getChildren());
    }

    public void tree(int deep, List<Monitor> monitorList) {
        StringBuffer tab = new StringBuffer();
        for (int i = 0; i < deep; i++) {
            tab.append("\t");
        }

        for(Monitor m: monitorList){
            List<Monitor> nextChildren = m.getChildren();
            if(nextChildren.size() != 0){
                deep++;
                tree(deep, nextChildren);
            }
            System.out.println(String.format("%d%s uuid: %s, elapsed time: %d",
                    deep, tab.toString(), m.getToken(), m.getElapsedNanos()));
        }
    }
}
