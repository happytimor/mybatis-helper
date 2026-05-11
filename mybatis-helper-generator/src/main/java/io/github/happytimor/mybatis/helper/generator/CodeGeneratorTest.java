package io.github.happytimor.mybatis.helper.generator;

/**
 * 代码生成器测试类
 *
 * @author gaomingyuan
 */
public class CodeGeneratorTest {

    /**
     * 主方法，用于测试代码生成器
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        /**
         * CREATE TABLE `action` (
         *   `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
         *   `project_id` int unsigned DEFAULT NULL COMMENT '分组id',
         *   `group_id` int unsigned DEFAULT NULL COMMENT '分组id',
         *   `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '动作名',
         *   `type` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '类型:action, locator,element',
         *   `node_type` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点类型,具体操作',
         *   `extra_info` text COLLATE utf8mb4_unicode_ci COMMENT '额外信息',
         *   `status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '状态',
         *   `x` int DEFAULT NULL COMMENT 'x坐标',
         *   `y` int DEFAULT NULL COMMENT 'y坐标',
         *   `code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '编码',
         *   `tag` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标签',
         *   `remark` text COLLATE utf8mb4_unicode_ci COMMENT '备注',
         *   `gmt_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
         *   `start_date` datetime DEFAULT NULL COMMENT '开始时间',
         *   `end_date` datetime DEFAULT NULL COMMENT '结束时间',
         *   `execute_count` int unsigned DEFAULT '0' COMMENT '执行次数',
         *   PRIMARY KEY (`id`) USING BTREE,
         *   KEY `idx_project_id` (`project_id`) USING BTREE,
         *   KEY `idx_group_id` (`group_id`) USING BTREE,
         *   KEY `idx_type` (`type`) USING BTREE,
         *   KEY `idx_status` (`status`) USING BTREE
         * ) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='动作信息表';
         */

        // 示例：生成表名为 "action" 的代码
        String tableName = "action";

        try {
            System.out.println("开始生成代码...");

            // 使用链式调用创建代码生成器
            CodeGenerator.create()
                    // 配置数据源
                    .dataSource(
                            "jdbc:mysql://127.0.0.1:3306/test?characterEncoding=UTF-8&useUnicode=true&useSSL=false&useInformationSchema=true&remarks=true",
                            "root",
                            "root"
                    )
                    // 全局配置
                    .globalConfig(builder -> {
                        builder.author("gaomingyuan") // 设置作者
                                .commentDate("yyyy-MM-dd hh:mm:ss") // 注释日期
                                .outputDir(System.getProperty("user.dir") + "/src/main/java") // 指定输出目录
                                .disableOpenDir() // 禁止打开输出目录，默认打开
                        ;
                    })
                    // 包配置
                    .packageConfig(builder -> {
                        builder.parent("com.gmy.mybatis.helper") // 设置父包名
                                .result("common.WebResponse") //通用返回体包名+类名(针对不同使用者各自项目规范)
                                .entity("domain.entity") // 设置实体包名
                                .request("domain.vo.request") // 设置请求对象包名
                                .response("domain.vo.response") // 设置返回对象包名
                                .mapper("mapper") // 设置Mapper包名
                                .service("service") // 设置Service包名
                                .controller("controller"); // 设置Controller包名
                    })
                    // 策略配置
                    .strategyConfig(builder -> {
                        builder.addInclude(tableName) // 设置需要生成的表
                                // 实体策略配置
                                .enableFileOverride() // 覆盖已生成文件(谨慎使用)
                        ;
                    })
                    // 执行生成
                    .execute();

            System.out.println("代码生成成功！");
        } catch (Exception e) {
            System.err.println("代码生成失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
