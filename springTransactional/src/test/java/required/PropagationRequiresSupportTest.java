package required;

import com.ycy.center.dao.entity.User1;
import com.ycy.center.dao.entity.User2;
import com.ycy.service.User1Service;
import com.ycy.service.User2Service;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2019/2/14.
 */
public class PropagationRequiresSupportTest extends BaseJunit4 {

    @Autowired
    private User1Service user1Service;
    @Autowired
    private User2Service user2Service;

    /**
     *PROPAGATION_SUPPORTS 支持当前事务，如果当前没有事务，就以非事务方式执行。
     *张三李四都插入到数据库
     */
    @Test
    public void notransaction_supports_supports_exception(){
        User1 user1=new User1();
        user1.setName("张三notransaction_supports_supports_exception");
        user1Service.addSupports(user1);

        User2 user2=new User2();
        user2.setName("李四notransaction_supports_supports_exception");
        user2Service.addSupportsException(user2);

        throw new RuntimeException("notransaction_supports_supports_exception");
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void transaction_supports_supports_exception(){
        User1 user1=new User1();
        user1.setName("张三transaction_supports_supports_exception");
        user1Service.addSupports(user1);

        User2 user2=new User2();
        user2.setName("李四transaction_supports_supports_exception");
        user2Service.addSupportsException(user2);

        throw new RuntimeException("transaction_supports_supports_exception");
    }


    /**
     * required_supports嵌套事务
     * 张三李四都回滚
     * 李四supports级别，随上级事务回滚
     */
    @Test
    public void notransaction_required_supports_exception(){
        User1 user1=new User1();
        user1.setName("张三_notransaction_required_supports_exception");
        User2 user2=new User2();
        user2.setName("李四_notransaction_required_supports_exception");
        user1Service.addRequiresUser1AndSupportsUser2(user1,user2);
    }


}
