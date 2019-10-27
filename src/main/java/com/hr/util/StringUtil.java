package com.hr.util;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {

    /**
     * 资源文件信息取得
     *
     * @param value  信息KEY
     * @param params 埋字
     * @return 信息
     */
    public static String formartMsg(String value, Object[] params) {
        if (value == null)
            return "";
        if (params == null)
            return value;
        MessageFormat mf = new MessageFormat(value);
        return mf.format(params);
    }

    /**
     * 检查文字列是<code>null</code。
     *
     * @param str 文字列
     * @return true或者false
     */
    public static boolean isEmpty(Object str) {
        if (str == null || str.toString().length() == 0)
            return true;
        else
            return false;
    }

    /**
     * 指定长度的random生成
     *
     * @param length 长度
     * @return random结果
     */
    public static String randStr(int length) {
        return randomStr(length, false);
    }

    /**
     * 指定长度的random生成
     *
     * @param length 长度
     * @return random结果
     */
    public static String randInt(int length) {
        return randomStr(length, true);
    }

    /**
     * 指定长度的random生成
     *
     * @param length 长度
     * @return random结果
     */
    public static String randomStr(int length, boolean isOnlyNumber) {
        // 英数字组合
        int NUM_CHAR = 59;
        // 数字
        if (isOnlyNumber)
            NUM_CHAR = 10;

        String t = "";
        int j = 0;
        String[] v = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i",
                "j", "k", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D",
                "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

        for (int i = 0; i < length; i++) {
            j = (int) (NUM_CHAR * Math.random());
            t = t + v[j];
        }

        return t;
    }

    /**
     * 文字列变换
     *
     * @param str 输入文字列
     * @return pInt
     */
    public static int strToInt(String str) {
        return str == null || "".equals(str) ? 0 : new BigDecimal(str).intValue();
    }

    /**
     * 文字列比較<br>
     *
     * @param pStr1 文字列1
     * @param pStr2 文字列2
     * @return true or not
     */
    public static boolean strEquals(String pStr1, String pStr2) {
        return pStr1 == null ? pStr2 == null : pStr1.equals(pStr2);
    }

    /**
     * @Title: checkTime5M @Description: 时间为5分钟内 @param @return 设定文件 @return boolean
     * 返回类型 @throws
     */
    public static boolean is1MiTime(long str) {
        long systime = System.currentTimeMillis();

        return (systime - 60000) <= str && str <= systime;
    }

    /**
     * @Title: isEmail @Description:判断邮箱 @param str 设定字符 @return 返回结果 @throws
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\" + "" +
                ".][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     *
     * @param mobile 移动、联通、电信运营商的号码段
     *               <p>
     *               移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
     *               、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）
     *               </p>
     *               <p>
     *               联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）
     *               </p>
     *               <p>
     *               电信的号段：133、153、180（未启用）、189
     *               </p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isMobile(String mobile) {

        String regex = "(\\+\\d+)?1[34578]\\d{9}$";
        return Pattern.matches(regex, mobile);
    }

    /**
     * Object对象向文字列变更。
     *
     * @param obj Object对象
     * @return 向文字
     */
    public static String objToStr(Object obj) {
        if (obj == null) {
            return "";
        } else {
            return String.valueOf(obj);
        }
    }

    /**
     * Object对象向Long变更。
     *
     * @param obj Object对象
     * @return 向文字
     */
    public static long objToLong(Object obj) {
        if (obj == null) {
            return 0;
        } else {
            return Long.parseLong(obj.toString());
        }
    }

    /**
     * Object对象向Double变更。
     *
     * @param obj Object对象
     * @return 向文字
     */
    public static double objToDouble(Object obj) {
        if (obj == null) {
            return 0;
        } else {
            return Double.parseDouble(obj.toString());
        }
    }

    /**
     * 检索起始位置初期化。
     *
     * @param page 文字列
     * @return true或者false
     */
    public static long initStart(long page, long pageSize) {
        long start = page;
        start = start > 0 ? start - 1 : start;
        long count = pageSize;
        start = start * count;
        return start;
    }

    /**
     * 获取身份证。
     *
     * @param str 文字列
     * @return true或者false
     */
    public static List<String> getIDCard(String str) {
        String reg = "([1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx])" +
                "" + "" + "" + "" + "" + "" + "" + "" + "" + "|([1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])" +
                "|10|20|30|31)" + "" + "\\d{2}[0-9Xx])";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        List<String> strs = new ArrayList<String>();
        while (m.find()) {
            strs.add(m.group());
        }
        return strs;
    }

    public static String getExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static String hideMobile(String mobile) {
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**type=0：纯数字(0-9)
     type=1：全小写字母(a-z)
     type=2：全大写字母(A-Z)
     type=3: 数字+小写字母
     type=4: 数字+大写字母
     type=5：大写字母+小写字母
     type=6：数字+大写字母+小写字母
     type=7：固定长度33位：根据UUID拿到的随机字符串，去掉了四个"-"(相当于长度33位的小写字母加数字)
	*/
    public static String getRandomCode(int passLength, int type) {
        StringBuffer buffer = null;
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        r.setSeed(new Date().getTime());
        switch (type) {
        case 0:
            buffer = new StringBuffer("0123456789");
            break;
        case 1:
            buffer = new StringBuffer("abcdefghijklmnopqrstuvwxyz");
            break;
        case 2:
            buffer = new StringBuffer("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            break;
        case 3:
            buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyz");
            break;
        case 4:
            buffer = new StringBuffer("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            break;
        case 5:
            buffer = new StringBuffer("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
            break;
        case 6:
            buffer = new StringBuffer("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
            sb.append(buffer.charAt(r.nextInt(buffer.length() - 10)));
            passLength -= 1;
            break;
        case 7:
            String s = UUID.randomUUID().toString();
            sb.append(s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24));
        }

        if (type != 7) {
            int range = buffer.length();
            for (int i = 0; i < passLength; ++i) {
                sb.append(buffer.charAt(r.nextInt(range)));
            }
        }
        return sb.toString();
    }


private static String byteArrayToHexString(byte b[]) {

		StringBuffer resultSb = new StringBuffer();

		for (int i = 0; i < b.length; i++)

			resultSb.append(byteToHexString(b[i]));

 

		return resultSb.toString();

	}

 

	private static String byteToHexString(byte b) {

		int n = b;

		if (n < 0)

			n += 256;

		int d1 = n / 16;

		int d2 = n % 16;

		return hexDigits[d1] + hexDigits[d2];

	}

 

	public static String MD5Encode(String origin, String charsetname) {

		String resultString = null;

		try {

			resultString = new String(origin);

			MessageDigest md = MessageDigest.getInstance("MD5");

			if (charsetname == null || "".equals(charsetname))

				resultString = byteArrayToHexString(md.digest(resultString

						.getBytes()));

			else

				resultString = byteArrayToHexString(md.digest(resultString

						.getBytes(charsetname)));

		} catch (Exception exception) {

		}

		return resultString;

	}

 

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",

		"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	
	

	/**

	 * 微信支付签名算法sign

	 * @param characterEncoding

	 * @param parameters

	 * @return

	 */

	@SuppressWarnings("unchecked")

	public static String createSign(String characterEncoding,SortedMap<Object,Object> parameters){

		StringBuffer sb = new StringBuffer();

		Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）

		Iterator it = es.iterator();

		while(it.hasNext()) {

			Map.Entry entry = (Map.Entry)it.next();

			String k = (String)entry.getKey();

			Object v = entry.getValue();

			if(null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {

				sb.append(k + "=" + v + "&");

			}

		}


		String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();

		return sign;

	}
	/**
	 * 保留两位小数
	 * @param d
	 * @return
	 */
	  public static String formatDouble(double d) {
	        NumberFormat nf = NumberFormat.getNumberInstance();
	        

	        // 保留两位小数
	        nf.setMaximumFractionDigits(2); 

	        
	        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
	        nf.setRoundingMode(RoundingMode.UP);

	        
	        return nf.format(d);
	    }
	  /**

	     * 随机加密

	     * @param hash

	     * @return

	     */

	    public  static String byteToHex(final byte[] hash) {

	        Formatter formatter = new Formatter();

	        for (byte b : hash)

	        {
	            formatter.format("%02x", b);

	        }
	        String result = formatter.toString();

	        formatter.close();

	        return result;

	    }

}
