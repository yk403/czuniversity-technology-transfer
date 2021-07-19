package com.itts.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        boolean hasSetter = metaObject.hasSetter("cjsj");
        if(hasSetter){
            this.setFieldValByName("cjsj",new Date(),metaObject);
            this.setFieldValByName("gxsj",new Date(),metaObject);
        }
        /*this.strictUpdateFill(metaObject, "cjsj", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.3(推荐)
        this.strictUpdateFill(metaObject, "gxsj", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.3(推荐)
    */}

    @Override
    public void updateFill(MetaObject metaObject) {
        Object val = getFieldValByName("gxsj", metaObject);
        log.info("start update fill ....");
        if(val != null){
            this.setFieldValByName("gxsj",new Date(),metaObject);
        }
       /* this.strictUpdateFill(metaObject, "cjsj", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.3(推荐)
*/
    }
}