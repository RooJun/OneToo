package com.onetoo.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Slf4j
/**
 * 公共字段自动填充
 * 在需要填充的部分加入@TableField(fill = FieldFill.INSERT_UPDATE)
 */
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {

        log.info("公共字段自动填充[insert]......");
        log.info(metaObject.toString());

        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime",LocalDateTime.now());
        metaObject.setValue("createUser",BaseContext.getCurrentId());
        metaObject.setValue("updateUser",BaseContext.getCurrentId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        log.info("公共字段自动填充[update]......");
        log.info(metaObject.toString());

//        long id = Thread.currentThread().getId();
//        log.info("线程的id为：{}",id);


        metaObject.setValue("updateTime",LocalDateTime.now());

        metaObject.setValue("updateUser",BaseContext.getCurrentId());
    }
}
