package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州玩家坐下
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasSeat extends GCMessage{
	
	/** 玩家列表 */
	private com.gameserver.texas.data.TexasRoomPlayerInfoData playerJoin;

	public GCTexasSeat (){
	}
	
	public GCTexasSeat (
			com.gameserver.texas.data.TexasRoomPlayerInfoData playerJoin ){
			this.playerJoin = playerJoin;
	}

	@Override
	protected boolean readImpl() {
		playerJoin = new com.gameserver.texas.data.TexasRoomPlayerInfoData();
					playerJoin.setPlayerId(readLong());
							playerJoin.setName(readString());
							playerJoin.setImg(readString());
							playerJoin.setPlayerState(readInteger());
							playerJoin.setCoins(readLong());
							playerJoin.setVip(readInteger());
							playerJoin.setPos(readInteger());
							playerJoin.setCurrentBet(readLong());
							playerJoin.setAllBet(readLong());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(playerJoin.getPlayerId());
		writeString(playerJoin.getName());
		writeString(playerJoin.getImg());
		writeInteger(playerJoin.getPlayerState());
		writeLong(playerJoin.getCoins());
		writeInteger(playerJoin.getVip());
		writeInteger(playerJoin.getPos());
		writeLong(playerJoin.getCurrentBet());
		writeLong(playerJoin.getAllBet());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TEXAS_SEAT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_SEAT";
	}

	public com.gameserver.texas.data.TexasRoomPlayerInfoData getPlayerJoin(){
		return playerJoin;
	}
		
	public void setPlayerJoin(com.gameserver.texas.data.TexasRoomPlayerInfoData playerJoin){
		this.playerJoin = playerJoin;
	}
}