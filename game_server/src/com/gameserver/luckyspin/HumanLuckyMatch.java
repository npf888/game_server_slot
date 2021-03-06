package com.gameserver.luckyspin;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.core.util.Assert;
import com.db.model.HumanLuckyMatchEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;

public class HumanLuckyMatch  implements PersistanceObject<Long, HumanLuckyMatchEntity>{

	private Human owner;
	private final LifeCycle lifeCycle;
	private long id;
	private long charId;
	private boolean inDb;
	/**上一次领取时间*/
	private long lastMatchTime;
	/**当前在线奖励id*/
	private int times;
	/**奖池顺序*/
	private List<Integer> poolList= new ArrayList<Integer>();

	/** 更新时间 */
	private long updateTime;
	/** 创建时间 */
	private long createTime;
	
	public HumanLuckyMatch(){
		this.lifeCycle = new LifeCycleImpl(this);
		
	}
	
	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}

	public Human getOwner() {
		Assert.notNull(owner);
		return owner;
	}
	
	public void setOwner(Human owner) {
		this.owner = owner;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCharId(long charId) {
		this.charId = charId;
	}
	
	@Override
	public long getCharId() {
		// TODO Auto-generated method stub
		return charId;
	}
	
	@Override
	public void setDbId(Long id) {
		// TODO Auto-generated method stub
		this.id = id;
	}
	@Override
	public Long getDbId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public String getGUID() {
		// TODO Auto-generated method stub
		return "luckymatch#"+this.id;
	}

	@Override
	public boolean isInDb() {
		// TODO Auto-generated method stub
		return inDb;
	}
	
	@Override
	public void setInDb(boolean inDb) {
		// TODO Auto-generated method stub
		this.inDb = inDb;
	}
	


	/**
	 * @return the lastFreeTime
	 */
	public long getLastMatchTime() {
		return lastMatchTime;
	}

	/**
	 * @param lastFreeTime the lastFreeTime to set
	 */
	public void setLastMatchTime(long lastMatchTime) {
		this.lastMatchTime = lastMatchTime;
	}



	/**
	 * @return the times
	 */
	public int getTimes() {
		return times;
	}

	/**
	 * @param times the times to set
	 */
	public void setTimes(int times) {
		this.times = times;
	}
	
	public List<Integer> getPoolList(){
		return this.poolList;
	}


	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	@Override
	public void setModified() {
		// TODO Auto-generated method stub
		onUpdate();
	}

	@Override
	public HumanLuckyMatchEntity toEntity() {
		// TODO Auto-generated method stub
		HumanLuckyMatchEntity entity = new HumanLuckyMatchEntity();
		entity.setId(getDbId());
		entity.setCharId(getCharId());
	;
		entity.setLastMatchTime(this.getLastMatchTime());
		entity.setTimes(this.getTimes());
		entity.setPoolPack(JSON.toJSONString(this.getPoolList()));
		entity.setCreateTime(getCreateTime());
		entity.setUpdateTime(getUpdateTime());
		return entity;
	}

	@Override
	public void fromEntity(HumanLuckyMatchEntity entity) {
		// TODO Auto-generated method stub
		this.id = entity.getId();
		this.charId = entity.getCharId();

		this.lastMatchTime = entity.getLastMatchTime();
		this.times = entity.getTimes();
		this.poolList.addAll(JSON.parseArray(entity.getPoolPack(), Integer.class));
		this.updateTime = entity.getUpdateTime();
		this.createTime = entity.getCreateTime();
		this.setInDb(true);
		this.active();
	}

	/**
	 * 激活此2关系
	 */
	public void active(){
		getLifeCycle().activate();
	}
	
	private void onUpdate()
	{
		if (owner != null) 
		{
			// TODO 为了防止发生一些错误的使用状况,暂时把此处的检查限制得很严格
			
			this.lifeCycle.checkModifiable();
			if (this.lifeCycle.isActive())
			{
				// 物品的生命期处于活动状态,并且该宠物不是空的,则执行通知更新机制进行
				long now = Globals.getTimeService().now();
				this.updateTime = now ;
				owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
			}
		}
	}
	
	
}
