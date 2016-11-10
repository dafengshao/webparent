package com.hwf.hessianty.spring;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.hwf.hessianty.annotation.RemoteService;

public class RemoteServcieExporter implements BeanDefinitionRegistryPostProcessor  {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String namespace = "/remote";
	
	@SuppressWarnings({ "rawtypes" })
	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		logger.debug("beanFactory");
		Map<String, Object> map=beanFactory.getBeansWithAnnotation(RemoteService.class);
        for (String key : map.keySet()) {
        	Object value = map.get(key);
        	Class clazz = value.getClass();
        	beanFactory.registerSingleton(getBeanName(clazz), value);
        }
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String getBeanName(Class clazz) {
		RemoteService annotation = (RemoteService) clazz.getAnnotation(RemoteService.class);
		String name = annotation.name();
		String namespace = annotation.namespace();
		String beanName = "";
		if(!StringUtils.isEmpty(name)){
			if(!StringUtils.isEmpty(namespace)){
				if(namespace.endsWith("/")&&name.startsWith("/")){
					namespace = namespace.substring(0,namespace.length()-1);
				}
				beanName =namespace + name;
			}else{
				beanName = RemoteServcieExporter.namespace+ name;
			}
		}else{
			Class[] interfaces = clazz.getInterfaces();
			if(interfaces==null||interfaces.length>0){
				clazz = interfaces[0];
			}
			beanName = clazz.getName();
			beanName ="/"+beanName.replaceAll("\\.", "_");
		}
		return beanName;
	}

	@Override
	public void postProcessBeanDefinitionRegistry(
			BeanDefinitionRegistry registry) throws BeansException {
		logger.debug("HessianServcieExporterBuilder.postProcessBeanDefinitionRegistry");
	}

	
}
