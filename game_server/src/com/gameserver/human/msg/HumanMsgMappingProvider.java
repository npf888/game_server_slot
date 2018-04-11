package com.gameserver.human.msg;

import java.util.HashMap;
import java.util.Map;

import com.gameserver.common.msg.MessageType;
import com.common.MessageMappingProvider;

/**
 *  Generated by MessageCodeGenerator,don't modify please.
 *  Need to register in<code>GameMessageRecognizer#init</code>
 */
public class HumanMsgMappingProvider implements MessageMappingProvider {

	@Override
	public Map<Short, Class<?>> getMessageMapping() {
		Map<Short, Class<?>> map = new HashMap<Short, Class<?>>();
		map.put(MessageType.CG_HUMAN_DETAIL_INFO_TODAY_VIEW, CGHumanDetailInfoTodayView.class);
		map.put(MessageType.CG_HUMAN_NEW_GUIDE, CGHumanNewGuide.class);
		map.put(MessageType.CG_HUMAN_CHANGE_NAME, CGHumanChangeName.class);
		map.put(MessageType.CG_HUMAN_CHANGE_SEX, CGHumanChangeSex.class);
		map.put(MessageType.CG_HUMAN_CHANGE_IMG, CGHumanChangeImg.class);
		map.put(MessageType.CG_HUMAN_VIDEO_NUM, CGHumanVideoNum.class);
		map.put(MessageType.CG_CHANEAGE_COUNTRIES, CGChaneageCountries.class);
		map.put(MessageType.CG_SLOT_ROOM_GIFT, CGSlotRoomGift.class);
		map.put(MessageType.CG_SLOT_ROOM_PLEASE, CGSlotRoomPlease.class);
		map.put(MessageType.CG_ROOM_BIGWIN_GIFT, CGRoomBigwinGift.class);
		map.put(MessageType.CG_EXP_DOUBLE, CGExpDouble.class);
		map.put(MessageType.CG_BANK_STATE, CGBankState.class);
		map.put(MessageType.CG_BANK_SET_PASSWORD, CGBankSetPassword.class);
		map.put(MessageType.CG_BANK_LOGIN, CGBankLogin.class);
		map.put(MessageType.CG_BANK_STORE, CGBankStore.class);
		map.put(MessageType.CG_BANK_OUT, CGBankOut.class);
		map.put(MessageType.CG_BANK_CHANGE_PASSWORD, CGBankChangePassword.class);
		map.put(MessageType.CG_BANK_SEND_IDENTIFYING_CODE, CGBankSendIdentifyingCode.class);
		map.put(MessageType.CG_BANK_MAKE_SURE_IDENTIFYING_CODE, CGBankMakeSureIdentifyingCode.class);
		return map;
	}

}
