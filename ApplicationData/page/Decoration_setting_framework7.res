<!DOCTYPE html>
<html>
<body nosliwattribute_placeholder="id:pleaseEmbed">
	<br>
	SettingName : <%=?(name)?%>
	
	<div>
		<a href="" nosliw-event="click:submit:">Submit</a>
		<a href="" nosliw-event="click:new:">New</a>
		<a href="" nosliw-event="click:delete:" style="display:<%=?(persist)?==true?'inline':'none'%>">Delete</a>
		<a href="" nosliw-event="click:save:" style="display:<%=(!?(persist)?)||?(modified)?==true?'inline':'none'%>">Save</a>
	</div>

<!--	<nosliw-contextvalue/>  -->



	<div id="pleaseEmbed"/>
	<br>


</body>

	<scripts>
	{
		submit : function(info, env){
			event.preventDefault();
			env.trigueEvent("submitSetting", info.eventData);
		},
		new : function(info, env){
			event.preventDefault();
			env.trigueEvent("newSetting", info.eventData);
		},
		delete : function(info, env){
			event.preventDefault();
			env.trigueEvent("deleteSetting", info.eventData);
		},
		save : function(info, env){
			event.preventDefault();
			env.trigueEvent("saveSetting", info.eventData);
		},
	}
	</scripts>


	<contexts>
	{
		"group" : {
			"public" : {
				"element" : {
					"persist" : {
						"definition" : {
						},
						defaultValue: true
					},
					"modified" : {
						"definition" : {
						},
						defaultValue: true
					},
					"name" : {
						"definition" : {
						},
						defaultValue: ""
					},
				}
			}
		}
	}
	</contexts>

	<events>
	[
		{
			name : "submit",
			data : {
				element : {
				}
			}
		}
	]
	</events>

</html>

