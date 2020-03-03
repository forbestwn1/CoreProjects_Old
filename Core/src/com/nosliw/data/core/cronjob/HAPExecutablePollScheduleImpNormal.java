package com.nosliw.data.core.cronjob;

import java.time.Instant;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.utils.HAPBasicUtility;

public class HAPExecutablePollScheduleImpNormal extends HAPSerializableImp implements HAPExecutablePollSchedule {

	public static final String TYPE = "normal";
	
	@HAPAttribute
	public static String START = "start";
	
	@HAPAttribute
	public static String INTERVAL = "interval";
	
	//start time of schedule
	private Instant m_start;
	
	//how often poll
	private int m_intervalSec;

	@Override
	public String getType() {  return TYPE; }

	@Override
	public HAPInstancePollSchedule newPoll() {
		HAPInstancePollSchedule out = new HAPInstancePollSchedule(this.m_start);
		return out;
	}
	
	@Override
	public HAPInstancePollSchedule prepareForNextPoll(HAPInstancePollSchedule schedule) {
		Instant pollTime = schedule.getPollTime();
		Instant nextPoll = pollTime.plusSeconds(this.m_intervalSec);
		return new HAPInstancePollSchedule(nextPoll);
	}
	
	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		String startStr = jsonObj.optString(START);
		if(HAPBasicUtility.isStringEmpty(startStr)) {
			this.m_start = Instant.now();
		}
		else {
			this.m_start = Instant.parse(startStr);
		}
		this.m_intervalSec = jsonObj.optInt(INTERVAL);
		return true;  
	}
}
