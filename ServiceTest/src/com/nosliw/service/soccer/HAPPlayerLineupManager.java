package com.nosliw.service.soccer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPFileUtility;

public class HAPPlayerLineupManager {

	static private HAPPlayerLineupManager m_instance;
	private HAPPlayerLineupManager() {}
	
	
	public static HAPPlayerLineupManager getInstance() {
		if(m_instance==null) {
			m_instance = new HAPPlayerLineupManager();
		}
		return m_instance;
	}
	
	public List<String> getInitialLineupPlayers(){
		List<String> players = new ArrayList<String>();
		String lineUpContent = HAPFileUtility.readFile(HAPUtility.getOriginalLineupFile());
		JSONArray lineUpArray = new JSONArray(lineUpContent);
		for(int i=0; i<lineUpArray.length(); i++) players.add(lineUpArray.getString(i));
		return players;
	}

	public HAPPlayerLineup getLineup() {
		HAPPlayerLineup out = this.readLineUp();
		if(out==null) {
			out = this.getInitialLineup();
			this.writeLineUp(out);
		}
		return out;
	}
	
	public HAPActionResult updateLineUp(String player, String action) {
		HAPPlayerLineup lineup = this.getLineup();
		HAPActionResult out = null;
		if(action!=null) {
			out = lineup.action(player, action);
			this.writeLineUp(lineup);
		}
		else {
			out = new HAPActionResult(lineup.getPlayerStatus(player), null);
		}
		return out;
	}
	
	private HAPPlayerLineup getInitialLineup(){
		List<String> players = this.getInitialLineupPlayers();
		HAPPlayerLineup out = new HAPPlayerLineup(players);
		return out;
	}

	private HAPPlayerLineup readLineUp() {
		if(!new File(HAPUtility.getLineupFile()).exists()) return null;
		
		HAPPlayerLineup out = new HAPPlayerLineup();
		JSONObject lineUpJson = new JSONObject(HAPFileUtility.readFile(HAPUtility.getLineupFile()));
		out.buildObject(lineUpJson, HAPSerializationFormat.JSON);
		return out;
	}
	
	private void writeLineUp(HAPPlayerLineup lineup) {
		HAPFileUtility.writeFile(HAPUtility.getLineupFile(), HAPJsonUtility.formatJson(lineup.toStringValue(HAPSerializationFormat.JSON)));
	}
	
}
