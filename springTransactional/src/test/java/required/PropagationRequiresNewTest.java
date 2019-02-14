package required;

import com.ycy.center.dao.entity.User1;
import com.ycy.center.dao.entity.User2;
import com.ycy.service.User1Service;
import com.ycy.service.User2Service;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2019/2/12.
 */
@Data
@Slf4j
public class PropagationRequiresNewTest extends BaseJunit4 {

    @Autowired
    private User1Service user1Service;
    @Autowired
    private User2Service user2Service;

    @Test
    public void notransaction_exception_requiresNew_requiresNew(){
        User1 user1=new User1();
        user1.setName("张三_requiresNew");
        user1Service.addRequiresNew(user1);

        User2 user2=new User2();
        user2.setName("李四_requiresNew");
        user2Service.addRequiresNew(user2);
        log.error("独立事物，外围有事物挂起外围事物，独立提交。");
        throw new RuntimeException("notransaction_exception_requiresNew_requiresNew");
    }

    @Test
    public void notransaction_requiresNew_requiresNew_exception(){
        User1 user1=new User1();
        user1.setName("张三_requiresNew");
        user1Service.addRequiresNew(user1);

        User2 user2=new User2();
        user2.setName("李四_requiresNew");
        user2Service.addRequiredException(user2);
        log.error("独立事物，外围有事物挂起外围事物，独立提交。");
    }

    /**
     * 嵌套事物挂起
     */
    @Test
    public void  notransaction_requiresNewInRequires(){
        User1 user1=new User1();
        user1.setName("张三_addRequiresUser1AndRequiresNewUser2");
        User2 user2=new User2();
        user2.setName("李四_addRequiresUser1AndRequiresNewUser2");
        user1Service.addRequiresUser1AndRequiresNewUser2(user1,user2);
    }

}
