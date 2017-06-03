package me.Tailo.KillCam.Utils;

import java.util.HashMap;

public class EntityIdUtil {
	
	private static HashMap<Integer, EntityIdUtil> getutil = new HashMap<Integer, EntityIdUtil>();
	public int realid;
	public int fakeid;

	public EntityIdUtil(int realid) {
		this.realid = realid;
		fakeid = ((int) Math.ceil(Math.random() * 1000) + 2000);
		getutil.put(realid, this);
	}

	public static EntityIdUtil getEntityIdUtil(int realid) {
		
		if(getutil.containsKey(realid)) {
			return getutil.get(realid);
		}
		
		return new EntityIdUtil(realid);
		
	}
	
}
