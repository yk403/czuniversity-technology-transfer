package com.itts.webcrawler.dao.impl;

import com.itts.webcrawler.dao.JsspDao;
import com.itts.webcrawler.model.JsspEntity;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/*
 *@Auther: yukai
 *@Date: 2021/07/12/13:59
 *@Desription:
 */
@Component
public class JsspDaoImpl implements JsspDao {
    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void saveJssp(JsspEntity jsspEntity) {
        mongoTemplate.save(jsspEntity);
    }

    @Override
    public void removeJssp(Long id) {
        mongoTemplate.remove(id);
    }

    @Override
    public void updateJssp(JsspEntity jsspEntity) {
        Query query = new Query(Criteria.where("id").is(jsspEntity.getId()));

        Update update = new Update();
        update.set("title", jsspEntity.getTitle());
        update.set("dwmc", jsspEntity.getDwmc());
        update.set("zptp", jsspEntity.getZptp());
        update.set("jyjg", jsspEntity.getJyjg());
        update.set("zllx", jsspEntity.getZllx());
        update.set("yxq", jsspEntity.getYxq());
        update.set("splx", jsspEntity.getSplx());
        update.set("fbrq", jsspEntity.getFbrq());
        update.set("jscsd", jsspEntity.getJscsd());
        update.set("hylb", jsspEntity.getHylb());
        update.set("lxr", jsspEntity.getLxr());
        update.set("fmr", jsspEntity.getFmr());

        mongoTemplate.updateFirst(query, update, JsspEntity.class);
    }

    @Override
    public JsspEntity findJsspById(Long id) {
        Query query = new Query(Criteria.where("id").is(id));
        JsspEntity jsspEntity = mongoTemplate.findOne(query, JsspEntity.class);
        return jsspEntity;
    }
}
