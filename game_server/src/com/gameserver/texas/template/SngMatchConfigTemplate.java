package com.gameserver.texas.template;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelRowBinding;

@ExcelRowBinding
public class SngMatchConfigTemplate extends SngMatchConfigTemplateVO{
	@Override
	public void check() throws TemplateConfigException
	{
		// TODO Auto-generated method stub
	}
	
	@Override
	public void patchUp() throws Exception 
	{
	}
}
