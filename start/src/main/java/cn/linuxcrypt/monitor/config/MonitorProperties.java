package cn.linuxcrypt.monitor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

/**
 * @function:
 * @author: liuboxun
 * @email: wmsjhappy@gmail.com
 * @date: 2018/4/6
 * @remark:
 * @version: 1.0
 */
@ConfigurationProperties(prefix = "cn.linuxcrypt.monitor")
public class MonitorProperties {
    private String span;

    public String getSpan() {
        return span;
    }

    public void setSpan(String span) {
        this.span = span;
    }

    @PostConstruct
    public void init() {
        this.span = span;
    }
}
