package com.tonik.util;

public class TextUtil {

	static public boolean isEmpty(String targetString){
		if(targetString == null){
			return true;
		}
		return targetString.isEmpty();
	}
	
	/**
	 * @desc: 首字母变成大写
	 * @param targetString
	 * @return
	 */
    static public String toUpperCap(String src)
    {
        char[] chars = src.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }
    
    /**
     * @desc: 首字母变成小写
     * @param targetString
     * @return
     */
    static public String toLowerCap(String src)
    {
        char[] chars = src.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }
}
