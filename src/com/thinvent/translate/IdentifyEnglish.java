package com.thinvent.translate;

public class IdentifyEnglish
{
    // 判断一个字符是否是中文
    public static boolean isChinese(char c) {
          return c >= 0x4E00 &&  c <= 0x9FA5;// 根据字节码判断
    }
    // 判断一个字符串是否含有中文
    public static boolean isChinese(String str) {
        if (str == null) return false;
        for (char c : str.toCharArray()) {
            if (isChinese(c)) return true;// 有一个中文字符就返回
        }
        return false;
    }
    
    public static void main(String[] args) {
        String test = "this is xasdas asd!";
        System.out.println(isChinese(test));
    }
}
