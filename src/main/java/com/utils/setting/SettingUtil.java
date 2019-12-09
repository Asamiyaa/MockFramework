package com.utils.setting;

import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion;
import com.utils.core.io.FileUtil;
import com.utils.core.util.StrUtil;
import org.apache.coyote.http2.Setting;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Setting工具类<br>
 * 提供静态方法获取配置文件
 * 
 * @author looly
 *
 */
public class SettingUtil {
	/** 配置文件缓存 */
	private static Map<String, ManagementAssertion.Setting> settingMap = new ConcurrentHashMap<>();
	private static Object lock = new Object();

	/**
	 * 获取当前环境下的配置文件<br>
	 * name可以为不包括扩展名的文件名（默认.setting为结尾），也可以是文件名全称
	 * 
	 * @param name 文件名，如果没有扩展名，默认为.setting
	 * @return 当前环境下配置文件
	 */
	public static Setting get(String name) {/*
		ManagementAssertion.Setting setting = settingMap.get(name);
		if (null == setting) {
			synchronized (lock) {
				setting = settingMap.get(name);
				if (null == setting) {
					String filePath = name;
					String extName = FileUtil.extName(filePath);
					if(StrUtil.isEmpty(extName)) {
						filePath  = filePath + "." + ManagementAssertion.Setting.EXT_NAME;
					}
					//setting = new Setting(filePath, true);
					setting  = null ;
					settingMap.put(name, setting);
				}
			}
		}
		return setting;
	}*/
	return null ;
}
}