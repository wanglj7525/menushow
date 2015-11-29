package utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Timer {
	private static HashMap<String,Timer> cache=new HashMap<String, Timer>();
	private long tempTime;
	private TimerResult result=new TimerResult();
	private int i=0;
	private Timer(String taskName){
		tempTime=System.currentTimeMillis();
		result.startTime=tempTime;
		result.startDate=new Date(tempTime);
		result.taskname=taskName;
	}
	
	
	public static Timer create(String taskName){
		Timer timer=new Timer(taskName);
		cache.put(taskName,timer);
		return timer;
	}
	
	public static Timer get(String taskName){
		Timer timer=cache.get(taskName);
		if(timer==null)timer=create(taskName);
		return timer;
	}
	
	public void log(){
		log(String.valueOf(++i));
	}
	
	public void log(String name){
		long now=System.currentTimeMillis();
		result.timeresult.put(name, now-tempTime);
		tempTime=now;
		result.endTime=now;
		result.endDate=new Date(now);
	}
	
	public void print(){
		Timer.print(result);
	}
	
	public static void print(TimerResult result){
		StringBuffer sb=new StringBuffer();
		sb.append("****Timer Task {"+result.taskname+"}****\n");
		sb.append("Start:"+result.startTime+"("+result.startDate+")\n");
		sb.append("End:"+result.endTime+"("+result.startDate+")\n");
		sb.append("----\n");
		for(Map.Entry<String, Long> entry : result.timeresult.entrySet()){
			sb.append(entry.getKey() +":"+entry.getValue()+"\n");
		}
		sb.append("****************************************\n");
//		System.out.println(sb);
	}
	
	public TimerResult result(){
		return result;
	}
}
