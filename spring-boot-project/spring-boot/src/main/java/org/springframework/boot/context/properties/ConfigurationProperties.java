/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.context.properties;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * 作用：将配置文件(application.yaml)的每个属性的值，映射到这个组件中，告诉Springboot将本类中的所有属性和配置文件中的配置进行绑定。
 *
 * @ConfigurationProperties注解的作用和@Value类似，都是获取配置文件中相应的配置值，但是@ConfigurationProperties与@Value不同的是，
 * @Value一次只能获取一个值，但是@ConfigurationProperties可以获取多个值，例如，有如下配置:
 * my:
 *  test:
 *    name: lisi
 *    age: 21
 *
 * 我们使用@ConfigurationProperties就可以这么获取:
 *
 * @Data
 * @Configuration
 * @ConfigurationProperties(prefix = "my.test")
 * public class Config {
 *     private String name;
 *     private int age;
 * }
 *
 *
 *
 * 再例如如下配置文件内容：
 * spring.datasource.url=jdbc:mysql://127.0.0.1:8888/test?useUnicode=false&autoReconnect=true&characterEncoding=utf-8
 * spring.datasource.username=root
 * spring.datasource.password=root
 * spring.datasource.driver-class-name=com.mysql.jdbc.Driver
 * spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
 *
 * @Component
 * @ConfigurationProperties(prefix = "spring.datasource")
 * public class DatasourcePro {
 *
 *     private String url;
 *     private String username;
 *     private String password;
 *     // 配置文件中是driver-class-name, 转驼峰命名便可以绑定成
 *     private String driverClassName;
 *     private String type;
 *
 *     // 省略getter and setter 。。。
 * }
 *
 * @author Dave Syer
 * @see ConfigurationPropertiesBindingPostProcessor
 * @see EnableConfigurationProperties
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConfigurationProperties {

	/**
	 * 可以绑定到该对象的属性的名称前缀. 有效前缀由一个或多个用点分隔的单词定义（例如{@code“ acme.system.feature”}）。
	 *
	 * @return 要绑定的属性的名称前缀
	 */
	@AliasFor("prefix")
	String value() default "";

	/**
	 * 作用同{@link #value()}
	 * @return the name prefix of the properties to bind
	 */
	@AliasFor("value")
	String prefix() default "";

	/**
	 * Flag to indicate that when binding to this object invalid fields should be ignored.
	 * Invalid means invalid according to the binder that is used, and usually this means
	 * fields of the wrong type (or that cannot be coerced into the correct type).
	 * @return the flag value (default false)
	 */
	boolean ignoreInvalidFields() default false;

	/**
	 * Flag to indicate that when binding to this object unknown fields should be ignored.
	 * An unknown field could be a sign of a mistake in the Properties.
	 * @return the flag value (default true)
	 */
	boolean ignoreUnknownFields() default true;

}
