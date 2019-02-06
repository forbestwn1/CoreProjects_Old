<!DOCTYPE html>
<html>
	<body>
<!--
	<br>
	<nosliw-submit title="Submit" datasource="school" parms="schoolType:criteria.schoolType;schoolRating:criteria.schoolRating"  output="result"/>  
	<br>
-->
	<div>
	Query
	<br>
	<nosliw-include source="Page_MySchool_Query"/> 
	</div>
	
	<div>
	Result
	<br>
	<nosliw-include source="Page_MySchool_SchoolList"/> 
	</div>

	<br>

	</body>

	<services>
	{
		"use" : [
			{
				"name" : "getSchoolData",
				"provider" : "getSchoolDataService",
				"serviceMapping" :{
					"parmMapping" : {
						"element" : {
							"schoolTypeInService" : {
								"definition" : {
									"path" : "schoolType"
								}
							},
							"schoolRatingInService" : {
								"definition" : {
									"path" : "schoolRating"
								}
							}
						}
					},
					"resultMapping" : {
						"success" : {
							"schoolList" : {
								"definition" : {
									"path" : "outputInService"
								}
							}
						}
					}
				}
			}
		],
		"provider" : [
			{	
				"name" : "getSchoolDataService",
				"serviceId" : "schoolService"
			}		
		]
	}	
	</services>
	
</html>
