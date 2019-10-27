package com.hr.util;

import org.apache.commons.lang3.StringUtils;

import com.vdurmont.emoji.EmojiParser;

public class EmojiUtil {
	
	/**
	 * 将Emoji表情加密成普通字符串
	 * @param emojiStr
	 * @return
	 */
	public static String enCodeEmoji(String emojiStr) {
		try {
			if(StringUtils.isEmpty(emojiStr)) {
				return "";
			}else {
				return EmojiParser.parseToAliases(emojiStr);
			}
		}catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 将已加密的Emoji字符串还原成原始表情
	 * @param emojiStr
	 * @return
	 */
	public static String deCodeEmoji(String emojiStr) {
		try {
			if(StringUtils.isEmpty(emojiStr)) {
				return "";
			}else {
				return EmojiParser.parseToUnicode(emojiStr);
			}
		}catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}

}
