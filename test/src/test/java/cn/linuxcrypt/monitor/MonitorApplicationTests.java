package cn.linuxcrypt.monitor;

import cn.linuxcrypt.monitor.core.QueryStatCollector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MonitorApplicationTests {

    @Autowired
    private SimpleService simpleService;

    @Test
    public void contextLoads() {
//        simpleService.test(1);
        simpleService.test(0);
        QueryStatCollector.get().tree();
//        simpleService.work(3);
    }


}
