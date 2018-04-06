package cn.linuxcrypt.monitor.config;

import cn.linuxcrypt.monitor.core.Monitor;
import cn.linuxcrypt.monitor.core.QueryStatCollector;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @function:
 * @author: liuboxun
 * @email: wmsjhappy@gmail.com
 * @date: 2018/4/6
 * @remark:
 * @version: 1.0
 */
public class MonitorMethodInterceptor implements MethodInterceptor {
    private Logger logger = LoggerFactory.getLogger(MonitorMethodInterceptor.class);

    private String span;

    public MonitorMethodInterceptor(String span) {
        this.span = span;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        String methodName = methodInvocation.getMethod().getName();

        Object result = null;
        try(Monitor m = new Monitor()){
            result = methodInvocation.proceed();
        }
        return result;
    }
}
