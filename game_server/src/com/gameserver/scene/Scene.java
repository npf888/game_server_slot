package com.gameserver.scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.common.DestroyRequired;
import com.common.InitializeRequired;
import com.common.constants.CommonErrorLogInfo;
import com.common.constants.Loggers;
import com.core.heartbeat.HeartBeatAble;
import com.core.heartbeat.HeartbeatTaskExecutor;
import com.core.heartbeat.HeartbeatTaskExecutorImpl;
import com.core.heartbeat.ITickable;
import com.core.msg.IMessage;
import com.core.msg.MessageQueue;
import com.core.util.Assert;
import com.core.util.ErrorsUtil;
import com.gameserver.common.listener.Listenable;
import com.gameserver.common.listener.Listener;
import com.gameserver.human.Human;
import com.gameserver.player.OnlinePlayerService;
import com.gameserver.player.Player;
import com.gameserver.player.PlayerState;
import com.gameserver.player.msg.GCEnterScene;
import com.gameserver.scene.manager.ScenePlayerManager;
import com.gameserver.scene.manager.SceneRoomManager;
import com.gameserver.scene.persistance.SceneDataUpdater;
import com.gameserver.scene.template.CityTemplate;

/**
 * 场景
 * 
 * @author Thinker
 *
 */
public class Scene implements ITickable, HeartBeatAble, Listenable<SceneListener>, InitializeRequired, DestroyRequired
{
	/** UUID */
	private long UUID;
	/** 场景最多人数 */
	public static final int MAX_PLAYER_COUNT = 1024;
	/** 场景最多房间*/
	public static final int MAX_ROOM_COUNT = 100;
	/** 场景的消息队列 */
	private MessageQueue<IMessage> msgQueue;
	
	/** 场景玩家管理 */
	private ScenePlayerManager playerManager;
	
	/**德州房间管理器*/
	private SceneRoomManager roomManager;
	
	/** 心跳任务处理器 */
	private HeartbeatTaskExecutor hbTaskExecutor;
	
	/** 注册到该场景上的监听器 */
	private List<SceneListener> listeners;
	/** 场景数据更新器 */
	private SceneDataUpdater dataUpdater;
	private CityTemplate sceneTmpl;

	public Scene(OnlinePlayerService olserv,CityTemplate sceneTmpl)
	{
		// 断言参数不为空
		Assert.notNull(olserv);
		this.sceneTmpl=sceneTmpl;
		msgQueue = new MessageQueue();
		playerManager = new ScenePlayerManager(this, olserv, MAX_PLAYER_COUNT);	
		roomManager = new SceneRoomManager(this);
		listeners = new ArrayList<SceneListener>();
		hbTaskExecutor = new HeartbeatTaskExecutorImpl();

		this.dataUpdater = new SceneDataUpdater();
	}

	@Override
	public void init() 
	{
		roomManager.init();
	}

	/**
	 * 玩家点击场景时, 发送场景功能列表
	 * 
	 * @param player
	 */
	public void onClick(Player player) 
	{
		if(player==null) return;
		Human human = player.getHuman();
		if (human == null)
		{
			// 记录错误日志
			Loggers.errorLogger.error(String.format("human is null, playerId = %d", player.getId()));
			return;
		}
	}

	/**
	 * @warning 在主处理线程中被调用
	 */
	@Override
	public void deleteListener(SceneListener listener)
	{
		if (!listeners.contains(listener)) return;
		listeners.remove(listener);
		listener.onDeleted(this);
	}

	/**
	 * @warning 在主处理线程中被调用
	 */
	@Override
	public void registerListener(SceneListener listener) 
	{
		if(listeners.contains(listener)) return;
		listeners.add(listener);
		listener.onRegisted(this);
		Collections.sort(listeners, Listener.comparator);
	}
	
	/**
	 * 获取场景ID
	 * 
	 * @return
	 */
	public int getId()
	{
		return this.sceneTmpl.getId();
	}
	
	/**
	 * 玩家进入场景
	 * 
	 * @param player
	 */
	public boolean onPlayerEnter(Player player,int sceneId) 
	{
		this.playerManager.addPlayer(player.getId());
		this.playerManager.addScenePlayer(player.getId());
		
		Human human = player.getHuman();
		if (Loggers.msgLogger.isDebugEnabled())
		{
			Loggers.msgLogger.debug("player[" + human.getUUID()+ "] enter scene[" + this.getId() + "]");
		}
		//修改玩家所在场景
		human.setScene(this);
		human.setSceneId(sceneId);
		//更新客户端属性
		human.snapChangedProperty(true);
		
		//发送进入场景后的相关信息
		GCEnterScene gcEnter = new GCEnterScene();
		human.sendMessage(gcEnter);
		
		//human.SyncSceneHuman();
		
		// 监听器监听
		for (SceneListener listener : listeners)
		{
			listener.afterHumanEnter(this, human);
		}
		return true;
	}
	

	/**
	 * 玩家离开场景，此方法在场景线程中执行
	 * 
	 * @param player
	 */
	public void onPlayerLeave(Player player)
	{
		int playerId = player.getId();
		Human human = player.getHuman();
		if (human == null) return;
		player.heartBeat();
	//	human.RemoveSyncSceneHuman();
		try
		{
			if (!playerManager.containPlayer(playerId))
			{
				Loggers.sceneLogger.warn("player[" + human.getUUID()+ "] not in scene[" + this.getId() + "]");
				return;
			}
			if (Loggers.msgLogger.isDebugEnabled())
			{
				Loggers.msgLogger.debug("player[" + human.getUUID()+ "] leave scene[" + this.getId() + "]");
			}
			
			// 监听器监听,在玩家被置为离开之前
			if (PlayerState.logouting == human.getPlayer().getState())
			{
				for (SceneListener listener : listeners)
				{
					listener.leaveOnLogoutSaving(this, human);
				}
			} else
			{
				for (SceneListener listener : listeners)
				{
					listener.beforeHumanLeave(this, human);
				}
			}
			playerManager.removePlayer(playerId);
			playerManager.removeScenePlayer(playerId);
		} catch (Exception e)
		{
			Loggers.gameLogger.error("Error occurs when player leave scene", e);
		} finally
		{			
			
		}
	}
	
	public boolean putMessage(IMessage message)
	{
		msgQueue.put(message);
		return true;
	}
	
	@Override
	public void tick()
	{
		playerManager.tick();
		roomManager.tick();
		// 处理场景消息
		while(!msgQueue.isEmpty())
		{
			IMessage msg = msgQueue.get();
			msg.execute();
		}
		this.heartBeat();
	}

	@Override
	public void heartBeat() 
	{
		playerManager.heartBeat();//存库操作,要在其他manager调用后做
		roomManager.heartBeat();
		
		this.updateData();
		hbTaskExecutor.onHeartBeat();
	}

	@Override
	public void destroy() {
		msgQueue = null;
		playerManager = null;	
	}
	
	
	public ScenePlayerManager getPlayerManager()
	{
		return playerManager;
	}
	
	public SceneRoomManager getRoomManager()
	{
		return roomManager;
	}

	/**
	 * 获取场景数据更新器
	 * 
	 * @return
	 */
	public SceneDataUpdater getDataUpdater() 
	{
		return this.dataUpdater;
	}

	/**
	 * 更新数据
	 */
	private void updateData()
	{
		try 
		{
			this.dataUpdater.update();
		} catch (Exception e)
		{
			if (Loggers.updateLogger.isErrorEnabled())
			{
				Loggers.updateLogger.error(ErrorsUtil.error(CommonErrorLogInfo.INVALID_STATE,"#GS.ServiceBuilder.buildGameMessageHandler", ""), e);
			}
		}
	}
}
