package com.itts.authorition.service;

import org.springframework.stereotype.Service;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/3/25
 */
@Service
public class SecurityUserDetailsServiceImpl {
/*
    @Autowired
    private TYhMapper yhMapper;

    @Autowired
    private TJsMapper jsMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SecurityUserDetails userDetails = new SecurityUserDetails();

        //查询用户基本信息并设置userDetails
        QueryWrapper query = new QueryWrapper();
        query.eq("yhm", username);
        query.eq("sfsc", false);

        TYh user = yhMapper.selectOne(query);
        if (user == null) {

            throw new ServiceException(ErrorCodeEnum.USER_NOT_FIND_ERROR);
        }

        userDetails.setUserName(user.getYhm());
        userDetails.setPassword(user.getMm());

        //查询用户角色信息并设置userDetails
        List<TJs> roles = jsMapper.findByYhId(user.getId());
        userDetails.setRoles(roles);

        return userDetails;
    }*/
}