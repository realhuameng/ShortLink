package com.huameng.shortlink.project.toolkit;

import cn.hutool.core.lang.hash.MurmurHash;

/**
 * HASH 工具类
 */
public class HashUtil {

    private static final char[] CHARS = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    //Base62编码的进制数
    private static final int SIZE = CHARS.length;

    //将十进制数转换为 Base62 编码。在该方法中，通过不断取余数和除以 SIZE 的方式，将十进制数转换为 Base62 编码。
    private static String convertDecToBase62(long num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            int i = (int) (num % SIZE);
            sb.append(CHARS[i]);
            num /= SIZE;
        }
        return sb.reverse().toString();
    }

    //接收一个字符串参数 str，首先使用 MurmurHash32 对该字符串进行哈希，得到一个整数 i。
    //如果 i 小于 0，则将 i 转换为长整型并减去 Integer.MAX_VALUE，得到一个正数 num；否则直接将 i 赋值给 num。
    //调用 convertDecToBase62 方法，将 num 转换为 Base62 编码后返回结果。
    public static String hashToBase62(String str) {
        int i = MurmurHash.hash32(str);
        long num = i < 0 ? Integer.MAX_VALUE - (long) i : i;
        return convertDecToBase62(num);
    }
}