<!DOCTYPE html>
<html>
<body>
	<br>
	<br>
	SchoolRating:<nosliw-floatinput data="criteria.schoolRating"/>  
	<br>
	<br>
	SchoolRating:<nosliw-floatinput data="criteria.schoolRating"/>  
	<br>

	<br>
	SchoolType:<nosliw-options id="schoolType" data="criteria.schoolType"/>
	<br>

</body>

	<contexts>
	{
		"group" : {
			"public" : {
				"element" : {
					"schoolType" : {
						"definition" : {
							"criteria" : "test.options;1.0.0"
						},
						"defaultValue" : {
							"dataTypeId": "test.options;1.0.0",
							"value": {
								"value" : "Public",
								"optionsId" : "schoolType"
							}
						}
					},
					"schoolRating" : {
						"definition" : {
							"criteria" : "test.float;1.0.0"
						},
						"defaultValue" : {
							"dataTypeId": "test.float;1.0.0",
							"value": 9.0
						}
					}
					
				}
				
			}
		
		}
	}
	</contexts>

</html>

