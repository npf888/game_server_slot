package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * BounsElephont1Template.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BounsElephont1TemplateVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 多语言描述id */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 多语言描述 */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/** icon */
	@ExcelCellBinding(offset = 4)
	protected String icon;

	/** 老虎机编号类型 */
	@ExcelCellBinding(offset = 5)
	protected int slotsNum;

	/** bouns个数 */
	@ExcelCellBinding(offset = 6)
	protected int bonusNum;

	/** 相同的元素 */
	@ExcelCellBinding(offset = 7)
	protected int samNum;

	/** 游戏次数 */
	@ExcelCellBinding(offset = 8)
	protected int times;


	public int getNameId() {
		return this.nameId;
	}



	public void setNameId(int nameId) {
		this.nameId = nameId;
	}
	
	public int getDescrip() {
		return this.descrip;
	}



	public void setDescrip(int descrip) {
		this.descrip = descrip;
	}
	
	public String getLangDesc() {
		return this.langDesc;
	}



	public void setLangDesc(String langDesc) {
		if (StringUtils.isEmpty(langDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[多语言描述]langDesc不可以为空");
		}
		this.langDesc = langDesc;
	}
	
	public String getIcon() {
		return this.icon;
	}



	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public int getSlotsNum() {
		return this.slotsNum;
	}


	public final static int getSlotsNumMinLimit() {
		return 0;
	}

	public void setSlotsNum(int slotsNum) {
		if (slotsNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[老虎机编号类型]slotsNum的值不得小于0");
		}
		this.slotsNum = slotsNum;
	}
	
	public int getBonusNum() {
		return this.bonusNum;
	}


	public final static int getBonusNumMinLimit() {
		return 0;
	}

	public void setBonusNum(int bonusNum) {
		if (bonusNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[bouns个数]bonusNum的值不得小于0");
		}
		this.bonusNum = bonusNum;
	}
	
	public int getSamNum() {
		return this.samNum;
	}


	public final static int getSamNumMinLimit() {
		return 0;
	}

	public void setSamNum(int samNum) {
		if (samNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[相同的元素]samNum的值不得小于0");
		}
		this.samNum = samNum;
	}
	
	public int getTimes() {
		return this.times;
	}


	public final static int getTimesMinLimit() {
		return 0;
	}

	public void setTimes(int times) {
		if (times < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[游戏次数]times的值不得小于0");
		}
		this.times = times;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, BounsElephont1TemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BounsElephont1TemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BounsElephont1TemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BounsElephont1TemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", slotsNum=" + slotsNum + ", bonusNum=" + bonusNum + ", samNum=" + samNum + ", times=" + times + ",]";
	}
}