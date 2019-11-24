package com.utils.db.handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;

import cn.hutool.db.Entity;

/**
 * 结果集处理类 ，处理出的结果为Entity列表，结果不能重复（按照Entity对象去重）
 * @author loolly
 *
 */
public class EntitySetHandler implements RsHandler<LinkedHashSet<Entity>>{
	private static final long serialVersionUID = 8191723216703506736L;

	/**
	 * 创建一个 EntityHandler对象
	 * @return EntityHandler对象
	 */
	public static EntitySetHandler create() {
		return new EntitySetHandler();
	}

	@Override
	public LinkedHashSet<Entity> handle(ResultSet rs) throws SQLException {
		return HandleHelper.handleRs(rs, new LinkedHashSet<Entity>());
	}
}
