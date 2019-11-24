/*
package com.utils.db.ds.pooled;

import com.utils.db.ds.AbstractDSFactory;

import javax.sql.DataSource;

*
 * Hutool自身实现的池化数据源工厂类
 * 
 * @author Looly
 *


public class PooledDSFactory extends AbstractDSFactory {
	private static final long serialVersionUID = 8093886210895248277L;
	
	public static final String DS_NAME = "Hutool-Pooled-DataSource";

*
	 * 构造
	 *
	 * @param dataSourceName  数据源名称
	 * @param dataSourceClass 数据库连接池实现类，用于检测所提供的DataSource类是否存在，当传入的DataSource类不存在时抛出ClassNotFoundException<br>
	 *                        此参数的作用是在detectDSFactory方法自动检测所用连接池时，如果实现类不存在，调用此方法会自动抛出异常，从而切换到下一种连接池的检测。
	 * @param setting         数据库连接配置


	public PooledDSFactory(String dataSourceName, Class<? extends DataSource> dataSourceClass, Setting setting) {
		super(dataSourceName, dataSourceClass, setting);
	}

	//public PooledDSFactory() {}

	@Override
	protected DataSource createDataSource(String jdbcUrl, String driver, String user, String pass, Setting poolSetting) {
		return null;
	}

	public PooledDSFactory(Setting setting) {
		super(DS_NAME, PooledDataSource.class, setting);
	}

	@Override
	protected DataSource createDataSource(String jdbcUrl, String driver, String user, String pass, Setting poolSetting) {
		final DbConfig dbConfig = new DbConfig();
		dbConfig.setUrl(jdbcUrl);
		dbConfig.setDriver(driver);
		dbConfig.setUser(user);
		dbConfig.setPass(pass);

		// 连接池相关信息
		dbConfig.setInitialSize(poolSetting.getInt("initialSize", 0));
		dbConfig.setMinIdle(poolSetting.getInt("minIdle", 0));
		dbConfig.setMaxActive(poolSetting.getInt("maxActive", 8));
		dbConfig.setMaxWait(poolSetting.getLong("maxWait", 6000L));

		return new PooledDataSource(dbConfig);
	}

}
*/
