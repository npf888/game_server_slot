package com.gameserver.misc.msg;

import java.util.HashMap;
import java.util.Map;

import com.gameserver.common.msg.MessageType;
import com.common.MessageMappingProvider;

/**
 *  Generated by MessageCodeGenerator,don't modify please.
 *  Need to register in<code>GameMessageRecognizer#init</code>
 */
public class MiscMsgMappingProvider implements MessageMappingProvider {

	@Override
	public Map<Short, Class<?>> getMessageMapping() {
		Map<Short, Class<?>> map = new HashMap<Short, Class<?>>();
		map.put(MessageType.CG_ONLINE_REWARD, CGOnlineReward.class);
		map.put(MessageType.CG_NEW_USER, CGNewUser.class);
		map.put(MessageType.CG_KOREAN_SB, CGKoreanSb.class);
		map.put(MessageType.CG_FB_INVITE, CGFbInvite.class);
		map.put(MessageType.CG_FB_INVITE_GET_REWARD, CGFbInviteGetReward.class);
		map.put(MessageType.CG_FB_GET_REWARD, CGFbGetReward.class);
		map.put(MessageType.CG_FB_THUMB_REWARD, CGFbThumbReward.class);
		return map;
	}

}
