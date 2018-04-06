package cn.linuxcrypt.monitor;

import cn.linuxcrypt.monitor.annotation.Monitor;
import org.springframework.stereotype.Service;

/**
 * @function:
 * @author: liuboxun
 * @email: wmsjhappy@gmail.com
 * @date: 2018/4/6
 * @remark:
 * @version: 1.0
 */
@Service
public class SimpleService {
    @Monitor
    public void test(int num) {
        this.work1(num);
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.work2(num);
    }

    public void work1(int num) {
        try(cn.linuxcrypt.monitor.core.Monitor m = new cn.linuxcrypt.monitor.core.Monitor()){
            Thread.sleep(1500);
            System.out.println("----test---- 1");
            try(cn.linuxcrypt.monitor.core.Monitor n = new cn.linuxcrypt.monitor.core.Monitor()){
                Thread.sleep(3000);
                System.out.println("----test---- 2");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void work2(int num) {
        try(cn.linuxcrypt.monitor.core.Monitor m = new cn.linuxcrypt.monitor.core.Monitor()){
            Thread.sleep(800);
            System.out.println("----work---- 1" );
            try(cn.linuxcrypt.monitor.core.Monitor n = new cn.linuxcrypt.monitor.core.Monitor()){
                Thread.sleep(1300);
                System.out.println("----work---- 2 ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
