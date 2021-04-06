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

package org.springframework.boot;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.SpringFactoriesLoader;

/**
 *
 * SpringApplicationRunListener是SpringBoot应用main方法执行过程中接收不同执行时点事件通知的监听者。
 *
 * 对于我们来说，基本没什么常见的场景需要自己实现一个SpringApplicationRunListener，即使SpringBoot默认也只是实现了一个
 * org.springframework.boot.context.event.EventPublishingRunListener，用于在SpringBoot启动的不同时点发布不同的应用事件类型(ApplicationEvent)，
 * 如果有哪些ApplicationListener对这些应用事件感兴趣，则可以接收并处理。
 *
 * 假设我们真的有场景需要自定义一个SpringApplicationRunListener实现，那么有一点需要注意，即任何一个SpringApplicationRunListener实现类的构
 * 造方法需要有两个构造参数，一个构造参数是：org.springframework.boot.SpringApplication，另外一个是args参数列表的String[]：
 *
 * public class DemoSpringApplicationRunLinstener implements SpringApplicationRunListener {
 * 	// 省略。。。
 * }
 *
 * 之后，我们可以通过SpringFactoriesLoader立下的规矩，在当前SpringBoot 应用的classpath下的META-INF/spring. factories文件中进行类似如下的配置:
 * org.springframework.boot.SpringApplicationRunListener=\
 * com.keevol.springboot.demo.DemoSpringApplicationRunListener
 * 然后SpringApplication就会在运行的时候调用它啦!
 *
 * @author Phillip Webb
 * @author Dave Syer
 * @author Andy Wilkinson
 */
public interface SpringApplicationRunListener {

	/**
	 * Called immediately when the run method has first started. Can be used for very
	 * early initialization.
	 */
	void starting();

	/**
	 * Called once the environment has been prepared, but before the
	 * {@link ApplicationContext} has been created.
	 * @param environment the environment
	 */
	void environmentPrepared(ConfigurableEnvironment environment);

	/**
	 * Called once the {@link ApplicationContext} has been created and prepared, but
	 * before sources have been loaded.
	 * @param context the application context
	 */
	void contextPrepared(ConfigurableApplicationContext context);

	/**
	 * Called once the application context has been loaded but before it has been
	 * refreshed.
	 * @param context the application context
	 */
	void contextLoaded(ConfigurableApplicationContext context);

	/**
	 * The context has been refreshed and the application has started but
	 * {@link CommandLineRunner CommandLineRunners} and {@link ApplicationRunner
	 * ApplicationRunners} have not been called.
	 * @param context the application context.
	 * @since 2.0.0
	 */
	void started(ConfigurableApplicationContext context);

	/**
	 * Called immediately before the run method finishes, when the application context has
	 * been refreshed and all {@link CommandLineRunner CommandLineRunners} and
	 * {@link ApplicationRunner ApplicationRunners} have been called.
	 * @param context the application context.
	 * @since 2.0.0
	 */
	void running(ConfigurableApplicationContext context);

	/**
	 * Called when a failure occurs when running the application.
	 * @param context the application context or {@code null} if a failure occurred before
	 * the context was created
	 * @param exception the failure
	 * @since 2.0.0
	 */
	void failed(ConfigurableApplicationContext context, Throwable exception);

}
