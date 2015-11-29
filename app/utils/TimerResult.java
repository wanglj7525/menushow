package utils;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TimerResult {
	public String taskname;
	public long startTime;
	public long endTime;
	public Date startDate;
	public Date endDate;
	public LinkedHashMap<String, Long> timeresult=new LinkedHashMap<String, Long>();
}
