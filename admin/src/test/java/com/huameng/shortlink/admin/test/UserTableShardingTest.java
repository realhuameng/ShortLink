package com.huameng.shortlink.admin.test;

public class UserTableShardingTest {

    public static final String SQL = "CREATE TABLE `t_link_goto_%d`  (\n" +
            "  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',\n" +
            "  `full_short_uri` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '短链接',\n" +
            "  `gid` varbinary(32) NULL DEFAULT NULL COMMENT '分组标识',\n" +
            "  PRIMARY KEY (`id`) USING BTREE\n" +
            ") ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;\n" +
            "\n" +
            "SET FOREIGN_KEY_CHECKS = 1;";

    public static void main(String[] args) {
        for (int i = 0; i < 16; i++) {
            System.out.println(String.format(SQL, i));

        }

    }
}
