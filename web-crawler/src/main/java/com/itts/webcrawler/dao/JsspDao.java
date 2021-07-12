package com.itts.webcrawler.dao;

import com.itts.webcrawler.model.JsspEntity;

/*
 *@Auther: yukai
 *@Date: 2021/07/12/13:51
 *@Desription:
 */
public interface JsspDao {
    void saveJssp(JsspEntity jsspEntity);

    void removeJssp(Long id);

    void updateJssp(JsspEntity jsspEntity);

    JsspEntity findJsspById(Long id);
}
