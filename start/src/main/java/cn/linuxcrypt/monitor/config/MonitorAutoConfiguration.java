package cn.linuxcrypt.monitor.config;

import cn.linuxcrypt.monitor.annotation.Monitor;
import org.aopalliance.aop.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @function:
 * @author: liuboxun
 * @email: wmsjhappy@gmail.com
 * @date: 2018/4/6
 * @remark:
 * @version: 1.0
 */
@Configuration
@EnableConfigurationProperties(MonitorProperties.class)
public class MonitorAutoConfiguration extends AbstractPointcutAdvisor {
    private Logger logger = LoggerFactory.getLogger(MonitorAutoConfiguration.class);
    private Pointcut pointcut;
    private Advice advice;

    @Autowired
    private MonitorProperties profilerProperties;

    @PostConstruct
    public void init() {
        logger.info("init ProfilerAutoConfiguration start");
        this.pointcut = new AnnotationMatchingPointcut(null, Monitor.class);
        this.advice = new MonitorMethodInterceptor(profilerProperties.getSpan());
        logger.info("init ProfilerAutoConfiguration end");
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }
}
