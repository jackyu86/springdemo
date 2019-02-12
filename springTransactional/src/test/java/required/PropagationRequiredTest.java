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
 * Created by Administrator on 2019/2/2.
 */
public class PropagationRequiredTest extends BaseJunit4 {

    @Autowired
    private User1Service user1Service;
    @Autowired
    private User2Service user2Service;


    /**
     * 张三”、“李四”均插入。
     * 外围方法未开启事务，
     * 插入“张三”、“李四”方法在自己的事务中独立运行，
     * 外围方法异常不影响内部插入“张三”、“李四”方法独立的事务。
     */
    @Test
    public void notransaction_exception_required_required(){
        User1 user1=new User1();
        user1.setName("张三");
        user1Service.addRequired(user1);

        User2 user2=new User2();
        user2.setName("李四");
        user2Service.addRequired(user2);

        throw new RuntimeException("notransaction_exception_required_required");
    }


    /**
     *张三插入，李四回滚
     *外围方法没有事务，插入“张三”、“李四”方法都在自己的事务中独立运行,
     * 所以插入“李四”方法抛出异常只会回滚插入“李四”方法，插入“张三”方法不受影响。
     */
    @Test
    public void notransaction_required_required_exception(){
        User1 user1=new User1();
        user1.setName("张三");
        user1Service.addRequired(user1);

        User2 user2=new User2();
        user2.setName("李四");
        user2Service.addRequiredException(user2);
    }

    /**
     *张三李四都回滚
     * 外围方法开启事务，内部方法加入外围方法事务，外围方法回滚，内部方法也要回滚。
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void transaction_required_required(){
        User1 user1=new User1();
        user1.setName("张三");
        user1Service.addRequired(user1);

        User2 user2=new User2();
        user2.setName("李四");
        user2Service.addRequired(user2);

        throw new RuntimeException("transaction_required_required");

    }

    /**
     * 张三李四都回滚
     * 外围方法开启事务，内部方法加入外围方法事务，
     * 内部方法抛出异常回滚，外围方法感知异常致使整体事务回滚。
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void transaction_required_required_exception(){
        User1 user1=new User1();
        user1.setName("张三");
        user1Service.addRequired(user1);

        User2 user2=new User2();
        user2.setName("李四");
        user2Service.addRequiredException(user2);
    }

    /**
     * 张三李四都回滚
     * 外围方法开启事务，内部方法加入外围方法事务，
     * 内部方法抛出异常回滚，即使方法被catch不被外围方法感知，整个事务依然回滚。
     */
    @Test
    @Transactional
    public void transaction_required_required_exception_try(){
        User1 user1=new User1();
        user1.setName("张三");
        user1Service.addRequired(user1);

        User2 user2=new User2();
        user2.setName("李四");
        try {
            user2Service.addRequiredException(user2);
        } catch (Exception e) {
            System.out.println("方法回滚");
        }
    }



}
