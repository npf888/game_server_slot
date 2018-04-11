package com.gameserver.givealike.msg;

import java.util.HashMap;
import java.util.Map;

import com.gameserver.common.msg.MessageType;
import com.common.MessageMappingProvider;

/**
 *  Generated by MessageCodeGenerator,don't modify please.
 *  Need to register in<code>GameMessageRecognizer#init</code>
 */
public class GivealikeMsgMappingProvider implements MessageMappingProvider {

	@Override
	public Map<Short, Class<?>> getMessageMapping() {
		Map<Short, Class<?>> map = new HashMap<Short, Class<?>>();
		map.put(MessageType.CG_GIVEALIKE_SAVE, CGGivealikeSave.class);
		map.put(MessageType.CG_ISNOT_GIVEALIKE, CGIsnotGivealike.class);
		return map;
	}

}