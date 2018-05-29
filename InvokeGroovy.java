package script;

import groovy.lang.GroovyClassLoader;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvokeGroovy {
	private static final Logger logger  = LoggerFactory.getLogger(InvokeGroovy.class);
	private static long lastModifyTime = 0;
	public static void main(String[] args) {
		runScript();
	}
	
	/**
	 * 监控、执行脚本
	 */
	public static void runScript(){
		try {
			URL url = InvokeGroovy.class.getResource("/script/Debug.groovy");
			if(url==null)return;
			File file = new File(URLDecoder.decode(url.getFile(),"UTF-8"));
			if(file.lastModified()==lastModifyTime)return;
			if(lastModifyTime==0){
				lastModifyTime = file.lastModified();
				return;
			}else{
				lastModifyTime = file.lastModified();
			}
			long time=System.currentTimeMillis();
			ClassLoader cl = InvokeGroovy.class.getClassLoader();
			GroovyClassLoader groovyCl = new GroovyClassLoader(cl);
			@SuppressWarnings("unchecked")
			Class<IFoo> groovyClass = groovyCl.parseClass(file);
			IFoo foo = groovyClass.newInstance();
			foo.run(2);
			groovyCl.close();
			System.out.println("execute groovy script spend time="+(System.currentTimeMillis()-time));
		} catch (Exception e) {
			logger.error("",e);
		}
	}
}
