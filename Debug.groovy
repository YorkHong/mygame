package script;

import java.util.Collection;

import game.common.Session;
import game.common.SessionCache;
import game.common.StaticSysConst;
import game.module.base.country.CountryCache
import game.module.base.country.db.Country
import game.module.extend.countryFb.CountryFb;
import game.module.extend.countryFb.CountryFbGC;



/**
 * groovy脚本类
 * 用来动态执行，监控、修复系统中疑难问题
 * @author yxh
 * 
 */
public class Debug implements IFoo {
	@Override
	public Object run(Object foo) {
		//上传跨服竞技场数据
		Country cd = CountryCache.getCountryMap().get(22);
		if(cd!=null){
			CountryFb fb = cd.getCfb();
			if(fb!=null){
				println" fb id="+fb.getId();
				if(fb.getId()==5){
					fb.setId(4);
					println" fb update ";
					Collection<Session> sessions=SessionCache.getChannels();
					for(Session session:sessions){
						if(session==null||session.getRole()==null||session.getRole().getCountry()!=22)continue;
						CountryFbGC.sendIconData(session.getChannel(),fb);
						println"send update fb id role id="+session.getRole().getId();
					}
				}
			}
		}
    	println "execute groovy script success";
	}
	 
}
