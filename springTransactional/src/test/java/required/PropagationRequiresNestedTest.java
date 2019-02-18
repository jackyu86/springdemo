package required;

import com.ycy.center.dao.entity.User1;
import com.ycy.center.dao.entity.User2;
import com.ycy.service.User1Service;
import com.ycy.service.User2Service;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2019/2/14.
 */
@Slf4j
public class PropagationRequiresNestedTest extends BaseJunit4 {

    @Autowired
    private User1Service user1Service;
    @Autowired
    private User2Service user2Service;

    /**
     *如果一个活动的事务存在，则运行在一个嵌套的事务中。
     * 如果没有活动事务，则按REQUIRED属性执行。它使用了一个单独的事务，这个事务拥有多个可以回滚的保存点。
     * 内部事务的回滚不会对外部事务造成影响。
     * 它只对DataSourceTransactionManager事务管理器起效。
     *
     *
     */
    @Test
    public void notransaction_exception_nested_nested(){
        User1 user1=new User1();
        user1.setName("张三_notransaction_nested_nested_exception");
        user1Service.addNested(user1);

        User2 user2=new User2();
        user2.setName("李四_notransaction_nested_nested_exception");
        user2Service.addNested(user2);
        throw new RuntimeException("notransaction_nested_nested_exception");
    }

    @Test
    public void notransaction_nested_nested_exception(){
        User1 user1=new User1();
        user1.setName("张三_notransaction_nested_nested_exception");
        user1Service.addNested(user1);

        User2 user2=new User2();
        user2.setName("李四_notransaction_nested_nested_exception");
        user2Service.addNestedException(user2);

    }



    /**
     *外围方法开启事务，内部事务为外围事务的子事务，
     * 外围方法回滚，内部方法也要回滚。
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void transaction_exception_nested_nested(){
        User1 user1=new User1();
        user1.setName("张三_transaction_exception_nested_nested");
        user1Service.addNested(user1);

        User2 user2=new User2();
        user2.setName("李四_transaction_exception_nested_nested");
        user2Service.addNested(user2);
        throw new RuntimeException("transaction_exception_nested_nested");

    }


    /**
     *外围方法开启事务，内部事务为外围事务的子事务，
     * 内部方法抛出异常回滚，且外围方法感知异常致使整体事务回滚。
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void transaction_nested_nested_exception(){
        User1 user1=new User1();
        user1.setName("张三_transaction_nested_nested_exception");
        user1Service.addNested(user1);

        User2 user2=new User2();
        user2.setName("李四_transaction_nested_nested_exception");
        user2Service.addSupportsException(user2);

    }

    /***
     * 无效
     */
    @Test
    @Transactional(propagation = Propagation.NESTED)
    @Deprecated
    public void transaction_nested_nested_exception_try_catch(){
        User1 user1=new User1();
        user1.setName("张三_transaction_nested_nested_exception_try_catch");
        user1Service.addNested(user1);

        User2 user2=new User2();
        user2.setName("李四_transaction_nested_nested_exception_try_catch");
        try {
            //user2Service.addSupportsException(user2); 整体回滚
            user2Service.addNestedException(user2);
            //user2Service.addRequiredException(user2);
        }catch (Exception e){
            user2.setName("李四_transaction_nested_nested_exception_try_catch222");
            user2Service.addNested(user2);
            log.error("transaction_nested_nested_exception_try_catch2222");
        }

    }

    @Test
    @Transactional
    public void transaction_required_nested_exception_try_catch(){
        User1 user1=new User1();
        user1.setName("张三_transaction_required_nested_exception_try_catch");


        User2 user2=new User2();
        user2.setName("李四_transaction_required_nested_exception_try_catch");
       user1Service.addRequiredUser1AndNestedUser2(user1,user2);

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
