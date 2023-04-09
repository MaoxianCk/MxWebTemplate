package com.mx.server.framework.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.sql.Types;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Maoxian
 * @since 2023/4/9
 */
public class MybatisPlusGenerator {
    public static void mains(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3307/mx_web_temp?useUnicode=true&characterEncoding=utf-8&useSSL=false&useJDBCCompliantTimezoneShift=true&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true",
                        "root",
                        "123456")
                .globalConfig(builder -> {
                    builder.author("Maoxian") // 设置作者
                            .disableOpenDir()
                            // .enableSwagger() // 开启 swagger 模式
                            .outputDir(System.getProperty("user.dir") + "/framework/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.mx.server.framework") // 设置父包名
                            .entity("model.entity")
                            .mapper("dao")
                            .xml("mapper")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "/framework/src/main/resources/mappers")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addTablePrefix("t_", "s_", "b_", "r_")
                            .addInclude(getTables("all"))
                            .entityBuilder()
                            .enableLombok()
                            .idType(IdType.ASSIGN_ID)
                            .enableTableFieldAnnotation()
                            .addTableFills(new Column("create_time", FieldFill.INSERT),
                                    new Column("update_time", FieldFill.INSERT_UPDATE),
                                    new Column("create_user_id", FieldFill.INSERT),
                                    new Column("update_user_id", FieldFill.INSERT_UPDATE))
                            .formatFileName("%sEntity") // 设置过滤表前缀
                            .mapperBuilder()
                            .build();
                })
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new VelocityTemplateEngine())
                .templateConfig(builder -> {
                    builder.disable(TemplateType.CONTROLLER,
                            TemplateType.SERVICE,
                            TemplateType.SERVICE_IMPL);
                })
                .execute();
    }

    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
