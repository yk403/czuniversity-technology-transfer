package com.itts.userservice.request.spzb.thirdparty;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/7/15
 */
@Data
public class GetTokenRequest implements Serializable {

    private static final long serialVersionUID = -1080459133884707313L;

    //认证信息
    private Auth auth;

    /**
     * 认证信息
     */
    @Data
    public static class Auth implements Serializable {

        private static final long serialVersionUID = -8295757051655210341L;

        //认证参数
        private Identity identity;

        //Token的使用范围，取值为project或domain，二选一即可
        private Scope scope;

    }

    /**
     * 认证参数
     */
    @Data
    public static class Identity implements Serializable {

        private static final long serialVersionUID = 6705533086782626981L;

        //认证方法，该字段内容为["password"]
        private List<String> methods;

        //IAM用户密码认证信息
        private Password password;
    }

    /**
     * Token的使用范围，取值为project或domain，二选一即可
     * 如果您将scope设置为domain，该Token适用于全局级服务；如果将scope设置为project，该Token适用于项目级服务。
     * 如果您将scope同时设置为project和domain，将以project参数为准，获取到项目级服务的Token。
     */
    @Data
    public static class Scope implements Serializable {

        private static final long serialVersionUID = 3491877108156841761L;

        //取值为domain时，表示获取的Token可以作用于全局服务，全局服务不区分项目或区域
        private ScopeParams domain;

        //取值为project时，表示获取的Token可以作用于项目级服务，仅能访问指定project下的资源
        private ScopeParams project;
    }

    /**
     * IAM用户密码认证信息
     * user.name和user.domain.name可以在界面控制台“我的凭证”中查看，具体获取方法请参见：获取帐号、IAM用户、项目、用户组、区域、委托的名称和ID。
     * 该接口提供了锁定机制用于防止暴力破解，调用时，请确保用户名密码正确，输错一定次数（管理员可设置该规则，方法请参见：帐号锁定策略）将被锁定。
     */
    @Data
    public static class Password implements Serializable {

        private static final long serialVersionUID = -6641594952029812463L;

        //需要获取Token的IAM用户信息
        private User user;

    }

    /**
     * 需要获取Token的IAM用户信息
     */
    @Data
    public static class User implements Serializable {

        private static final long serialVersionUID = 6219807156809603606L;

        //IAM用户所属帐号信息
        private Domain domain;

        //IAM用户名
        private String name;

        //IAM用户的登录密码
        private String password;

    }

    /**
     * IAM用户所属帐号信息
     */
    @Data
    public static class Domain implements Serializable {

        private static final long serialVersionUID = 2610942773118449241L;

        //IAM用户所属帐号名
        private String name;
    }

    /**
     * 取值为domain时，表示获取的Token可以作用于全局服务，全局服务不区分项目或区域，如OBS服务。如需了解服务作用范围，请参考系统权限。domain支持id和name，二选一即可，建议选择“domain_id”。
     * 取值为project时，表示获取的Token可以作用于项目级服务，仅能访问指定project下的资源，如ECS服务。如需了解服务作用范围，请参考系统权限。project支持id和name，二选一即可。
     */
    @Data
    public static class ScopeParams implements Serializable {

        private static final long serialVersionUID = -3828112463802449520L;

        private String id;

        private String name;
    }
}
