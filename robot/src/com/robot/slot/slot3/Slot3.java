package com.robot.slot.slot3;

import com.gameserver.slot.msg.GCSlotSlot;
import com.robot.Robot;
import com.robot.slot.GCSlotBase;
/**
 * 狮身人面
 * @author JavaServer
 *
 */
public class Slot3 extends GCSlotBase{

	@Override
	public synchronized void execute(Robot robot, GCSlotSlot gCSlotSlot,int slotId,int slotType) {
		//主转 流程
		robot.getgCSlotSlotHandler().handlerGCSlotSlot(robot,gCSlotSlot,slotId,slotType);
	}

}
