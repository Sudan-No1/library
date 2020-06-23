package com.sd.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import org.apache.commons.lang3.StringUtils;

/**
 * @Package: com.sd.common.util.PinyinUtil
 * @Description:
 * @author sudan
 * @date 2020/6/23 14:47
 */
 
 
public class PinyinUtil {

    public static String getPinyin(String str) throws Exception{
        char[] chars = str.toCharArray();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        String result = "";
        for (int i = 0; i< chars.length; i++){
            char aChar = chars[i];
            //判断是不是中文
            if(Character.toString(aChar).matches("[\\u4E00-\\u9FA5]")){
                result += PinyinHelper.toHanyuPinyinStringArray(aChar, format)[0];
            }else {
                result += Character.toString(aChar);
            }
        }
        return result;
    }

    public static String getPinyinHeaderChar(String str){
        if(StringUtils.isNotBlank(str)){
            char c = str.charAt(0);
            if(Character.toString(c).matches("[\\u4E00-\\u9FA5]")){
                String word = PinyinHelper.toHanyuPinyinStringArray(c)[0];
                return word.substring(0,1).toUpperCase();
            }else {
                return str.substring(0,1).toUpperCase();
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String str = getPinyin("苏丹");
        System.out.println(str);
    }
}