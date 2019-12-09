package com.utils.aop.proxy;

import com.utils.aop.aspects.Aspect;
import com.utils.aop.interceptor.CglibInterceptor;
import org.mockito.cglib.proxy.Callback;
import org.mockito.cglib.proxy.Enhancer;

/**
 * 基于Cglib的切面代理工厂
 * 
 * @author looly
 *
 */
public abstract class CglibProxyFactory extends ProxyFactory{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public <T> T proxy(T target, Aspect aspect) {
		final Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target.getClass());
		enhancer.setCallback((Callback) new CglibInterceptor(target, aspect));
		return (T) enhancer.create();
	}

}
