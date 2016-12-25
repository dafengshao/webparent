package com.github.nfs;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
@Component
public class SpringBeansContext implements ApplicationContextAware{
	private static ApplicationContext applicationContext; // Spring应用上下文环境
	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	 public static ApplicationContext getApplicationContext() {
         return applicationContext;
  }


   @SuppressWarnings("unchecked")
   public static <T> T getBean(String name) throws BeansException {
              return (T) applicationContext.getBean(name);
    }
}
