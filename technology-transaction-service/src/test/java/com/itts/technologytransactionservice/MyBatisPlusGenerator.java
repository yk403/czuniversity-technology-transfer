package com.itts.technologytransactionservice;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * @Author：lym
 * @Description：
 * @Date: 2021/3/12
 */
public class MyBatisPlusGenerator {

    public static void main(String[] args) {
        //1. 全局配置
        GlobalConfig config = new GlobalConfig();
        // 是否支持AR模式
        config.setActiveRecord(true)
                // 作者
                .setAuthor("yukai")
                // 生成路径，最好使用绝对路径，window路径是不一样的
                .setOutputDir("/Users/ASUS/Desktop/code-generate")
                // 文件覆盖
                .setFileOverride(true)
                // 主键策略
                .setIdType(IdType.AUTO)
                .setDateType(DateType.ONLY_DATE)
                // 设置生成的service接口的名字的首字母是否为I，默认Service是以I开头的
                .setServiceName("%sService")
                //实体类结尾名称
                .setEntityName("%s")
                //生成基本的resultMap
                .setBaseResultMap(true)
                //不使用AR模式
                .setActiveRecord(false)
                //生成基本的SQL片段
                .setBaseColumnList(true);
        //2. 数据源配置
        DataSourceConfig dsConfig = new DataSourceConfig();
        // 设置数据库类型
        dsConfig.setDbType(DbType.MYSQL)
                .setDriverName("com.mysql.cj.jdbc.Driver")
                //TODO  TODO  TODO  TODO
                .setUrl("jdbc:mysql://122.112.243.126:3306/technologyTransaction?useSSL=false")
                .setUsername("root")
                .setPassword("ZMHY@mysql");
        //3. 策略配置globalConfiguration中
        StrategyConfig stConfig = new StrategyConfig();
        //去掉表前缀
        stConfig.setTablePrefix("t_");
        //全局大写命名
        stConfig.setCapitalMode(true)
                // 数据库表映射到实体的命名策略
                .setNaming(NamingStrategy.underline_to_camel)
                //使用lombok
                .setEntityLombokModel(true)
                //使用restcontroller注解
                .setRestControllerStyle(true)
                // 生成的表, 支持多表一起生成，以数组形式填写
                .setInclude("t_ly_zp");
        //4. 包名策略配置
        PackageConfig pkConfig = new PackageConfig();
        pkConfig.setParent("com.itts.technologytransactionservice")
                .setMapper("mapper")
                .setService("service")
                .setController("controller")
                .setEntity("model")
                .setXml("mapper");
        //5. 整合配置
        AutoGenerator ag = new AutoGenerator();
        ag.setGlobalConfig(config)
                .setDataSource(dsConfig)
                .setStrategy(stConfig)
                .setPackageInfo(pkConfig);
        //6. 执行操作
        ag.execute();
        System.out.println("======= Done 相关代码生成完毕  ========");
    }
}