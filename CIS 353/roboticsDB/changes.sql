--< Testing The Unique Event ID constraint IC1 >--
INSERT INTO event (eventID, weekOfComp, eventName, "state", city, venue) VALUES ('18-FIM-TC', 1, 'Traverse City District Event', 'MI', 'Traverse City', 'Traverse City Central High School');

--< Testing the teamNumber must exist constraint IC2 >--
INSERT INTO district_ranking(rankID, teamNumber, districtPoints, advancedToStates) VALUES(11, 75, 83, 0);

--< Testing constraint IC3 >--
INSERT INTO team(teamNumber, teamName, teamOrigin, "state", city) VALUES(1, NULL, 'Grand Valley State University', 'MI', 'Allendale');

--< Testing constraint IC4 >--
INSERT INTO team(teamNumber, teamName, teamOrigin, "state", city) VALUES(NULL, 'The Lakers', 'Grand Valley State University', 'MI', 'Allendale');
