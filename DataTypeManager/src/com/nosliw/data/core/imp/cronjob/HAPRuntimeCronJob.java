package com.nosliw.data.core.imp.cronjob;

import java.util.List;
import java.util.Map;

import com.nosliw.data.core.HAPData;
import com.nosliw.data.core.cronjob.HAPExecutableCronJob;
import com.nosliw.data.core.cronjob.HAPInstancePollSchedule;
import com.nosliw.data.core.cronjob.HAPManagerCronJob;
import com.nosliw.data.core.resource.HAPResourceId;

public class HAPRuntimeCronJob {

	private HAPManagerCronJob m_cronJobMan = null;
	
	public HAPDataAccess m_dataAccess = null;
	
	public HAPInstanceCronJob newJob(HAPResourceId cronJobId, Map<String, HAPData> parms) {
		HAPExecutableCronJob cronJob = this.m_cronJobMan.getCronJob(cronJobId);
		return this.newJob(cronJob, parms);
	}
	
	public HAPInstanceCronJob newJob(HAPExecutableCronJob cronJob, Map<String, HAPData> parms) {
		HAPInstanceCronJob instance = new HAPInstanceCronJob(null, cronJob);
		HAPInstanceCronJob out = this.m_dataAccess.updateOrNewCronJob(instance);
		
		HAPInstancePollSchedule pollSchedule = cronJob.getSchedule().newPoll();
		HAPCronJobState state = new HAPCronJobState(null, out.getId(), pollSchedule, parms);
		this.m_dataAccess.updateOrNewState(state);
		return out;
	}

	public List<HAPCronJobState> findValidCronJobState(){
		
	}
	
	public void processCronJob(HAPCronJobState jobState) {
		
		HAPInstanceCronJob cronJob = this.m_dataAccess.getCronJob(jobState.getCronJobId());
		
		executeTask(cronJob, jobState.getState());
		
		finishTask();
	}
	
	public void finishTask() {
		
	}
	
	public void executeTask(HAPInstanceCronJob cronJob, Map<String, HAPData> state) {
		
	}
	
	public boolean ifStop(String id) {
		
	}
	
}
