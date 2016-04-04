<?php

	class DbHandlerNeo{
		private $neoClient;

		function __construct() {
			require_once dirname(__FILE__).'/Config.php';
			$this->neoClient = $neoClient;
		}

		/***************************** USER ************************************/

		public function createUser($userId, $email, $username){
			$userExists = $this->isUserExists($email, $username);

			if(!$userExists){
				$query = 'CREATE (user:User {userId: {userId}, email: {email}, username: {username} }) RETURN user';
				$parameters = array('userId' => $userId, 'email' => $email, 'username' => $username);
				$result = $this->neoClient->sendCypherQuery($query, $parameters);
				return 'USER_CREATED_SUCCESSFULLY';
			} else if($userExists){
				return 'USER_ALREADY_EXISTED';
			} else{
				return 'USER_CREATE_FAILED';
			}
		}

		/***************************** POSTS ************************************/
		
		public function createPostNoPhotos($userId, $eventTagId, $eventTagSource, $postInfoArr){
			if(!$this->isEventExistsBySourceAndSourceId($eventTagSource, $eventTagId)){
				return 'EVENT_NOT_FOUND';
			}

			$postMessage = $postInfoArr['message'];
			$userLat = $postInfoArr['latitude'];
			$userLng = $postInfoArr['longitude'];
			$timestamp = date("Y-m-d H:i:s"); 

			if($eventTagSource == "internal"){
				$query = 'MATCH (user:User) WHERE user.userId= {uid}
	        		MATCH (e:Event) WHERE id(e) = {eid} 
	        		CREATE (p:Post)
	        		SET p.message = {post_msg}, p.latitude = {lat}, p.longitude = {lng}, p.timestamp = {ts}
	        		CREATE (user)-[:CREATED]->(p), (p)-[:TAGS]->(e)
	        		RETURN p';
		        $params = [
		            'uid' => $userId,
		            'post_msg' => $postMessage,
		            'lat' => floatval($userLat),
		            'lng' => floatval($userLng),
		            'ts' => $timestamp,
		            'eid' => intval($eventTagId)
		            ];
	        } else{
	        	$query = 'MATCH (user:User) WHERE user.userId= {uid}
	        		MATCH (e:Event) WHERE e.external_id = {eid} 
	        		CREATE (p:Post)
	        		SET p.message = {post_msg}, p.latitude = {lat}, p.longitude = {lng}, p.timestamp = {ts}
	        		CREATE (user)-[:CREATED]->(p), (p)-[:TAGS]->(e)
	        		RETURN p';
		        $params = [
		            'uid' => $userId,
		            'post_msg' => $postMessage,
		            'lat' => floatval($userLat),
		            'lng' => floatval($userLng),
		            'ts' => $timestamp,
		            'eid' => $eventTagId
		            ];
	        }

	        $result = $this->neoClient->sendCypherQuery($query, $params)->getResult();

	        if (null !== $result->getSingle('p')) {
	            return 'POST_CREATED';
	        }
	 
	        return "UNKNOWN_ERROR";
		}

		public function deletePostNoPhotos($userId, $postId){
	        if(!$this->isNodeExistsById($postId)){
	        	return 'POST_NOT_FOUND';
	        } 

	        $query = 'MATCH (p:Post)-[r]-() WHERE id(p) = {pid} DELETE p,r';
	        $params = [
	        	'pid' => intval($postId),
	            'uid' => $userId
	            ];

	        $result = $this->neoClient->sendCypherQuery($query, $params)->getResult();

	        if (null !== $result) {
	            return 'DELETED';
	        }
	 
	        return 'UNKNOWN_ERROR';
		}

		public function getAllPosts(){
			$query = "MATCH (posts:Post) MATCH (users:User)-[:CREATED]->(posts) MATCH (posts)-[:TAGS]->(events:Event) RETURN posts,users,events";

	        $result = $this->neoClient->sendCypherQuery($query)->getRows();

	        if (null !== $result) {
	        	$success = 'success';
				$message = "This is all the posts!";
				$numFound = count($results['p']);
	        	$response = array('result' => $success, 
	        		'message' => $message, 
	        		'numFound' => $numFound, 
	        		array('posts' => $results['posts']), 
	        		array('users' => $results['users']), 
	        		array('events' => $result['events'])
	        		);
	            return $response;
	        } else{
	        	$response['result'] = 'error';
				$response['message'] = 'Unknown Error. Post creation failed.';
				return $response;
	        }
		}

		public function getPostCreator($postId){
			$query = "MATCH (p:Post) WHERE id(p) = {pid} MATCH (user)-[:CREATED]->(p) RETURN user";
			$params = [
	            'pid' => $postId
	            ];

	        $result = $this->neoClient->sendCypherQuery($query, $params)->getResult();

	        if (null !== $result->getSingle('user')) {
	            return true;
	        }
	 
	        return false;
		}

		/***************************** EVENTS ************************************/
		
		public function createExternalEvents($eventInfoJson){
			$jsonToNeo = new JsonToNeo();
			$events = $jsonToNeo->jsonEventsToGEventObjects($eventInfoJson);

			$successCount = 0;
			$alreadyExistCount = 0;
			$errorCount = 0;
			$totalEvents = count($events);

			foreach($events as $event){
				$eventExtId = $event->external_id;
				$eventSource = $event->datasource;

				// Check if event already exists
				if($this->isEventExistsBySourceAndSourceId($eventSource, $eventExtId)){
					
					$alreadyExistCount++;
				
				} else{

					$query = $jsonToNeo->generateInsertCypher($event);
					$result = $this->neoClient->sendCypherQuery($query['query'], $query['params'])->getResult();

					if(isset($result)){
						$successCount++;
					}

					$errorCount;
				}
			}

			return "$successCount of $totalEvents were successfully created. $errorCount errors and $alreadyExistCount already existing events.";
		}

		public function createExternalEventsWithRel($events, $relId){
			$jsonToNeo = new JsonToNeo();

			$successCount = 0;
			$alreadyExistCount = 0;
			$errorCount = 0;
			$totalEvents = count($events);

			foreach($events as $event){
				$eventExtId = $event->external_id;
				$eventSource = $event->datasource;

				// Check if event already exists
				if($this->isEventExistsBySourceAndSourceId($eventSource, $eventExtId)){
					
					$alreadyExistCount++;
				
				} else{
					$query = $jsonToNeo->generateInsertCypher($event);
					$query['query'] = "MATCH (esr) WHERE id(esr) = $relId ".$query['query']."CREATE (esr)-[:CREATED]->(e)";
					$result = $this->neoClient->sendCypherQuery($query['query'], $query['params'])->getResult();

					if(isset($result)){
						$successCount++;
					}

					$errorCount;
				}
			}

			return "$successCount of $totalEvents were successfully created. $errorCount errors and $alreadyExistCount already existing events.";
		}

		public function createInternalEvent($userId, $eventInfoArr){
			$eventTitle = $eventInfoArr['title'];
			$eventDesc = $eventInfoArr['desc'];
			$eventCity = $eventInfoArr['city'];

	        $query = 'CREATE (e:Event { title: {e_title}, description: {e_desc}, city: {e_city} }) RETURN e';
	        $params = [
	            'e_title' => $eventTitle,
	            'e_desc' => $eventDesc,
	            'e_city' => $eventCity
	            ];

	        $result = $this->neoClient->sendCypherQuery($query, $params)->getResult();

	        if (null !== $result->getSingle('e')) {
	            return 'EVENT_CREATED';
	        }
	 
	        return 'UNKNOWN_ERROR';
		}

		public function destroyInternalEvent($eventId){
	        if(!$this->isNodeExistsById($eventId)){
	        	return 'EVENT_NOT_FOUND';
	        } 

	        $query = 'MATCH (e:Event) WHERE id(e) = {eid} OPTIONAL MATCH (e)-[r]-() DELETE e,r';
	        $params = [
	        	'eid' => intval($eventId)
	            ];

	        $result = $this->neoClient->sendCypherQuery($query, $params);

	        if (null !== $result) {
	            return 'DELETED';
	        }
	 
	        return 'UNKNOWN_ERROR';
		}

		public function destroyExternalEvent($eventSource, $eventId){
	        if(!$this->isEventExistsBySourceAndSourceId($eventSource, $eventId)){
	        	return 'EVENT_NOT_FOUND';
	        } 

	        $query = 'MATCH (e:Event) WHERE e.external_id = {eid} OPTIONAL MATCH (e)-[r]-() DELETE e,r';
	        $params = [
	        	'eid' => intval($eventId)
	            ];

	        $result = $this->neoClient->sendCypherQuery($query, $params);

	        if (null !== $result) {
	            return 'DELETED';
	        }
	 
	        return 'UNKNOWN_ERROR';
		}

		public function getEventCreator($eventId){
			$query = "MATCH (e:Event) WHERE id(e) = {eid} MATCH (user)-[:CREATED]->(e) RETURN user";
			$params = [
	            'eid' => $eventId
	            ];

	        $result = $this->neoClient->sendCypherQuery($query, $params)->getResult();

	        if (null !== $result->getSingle('user')) {
	            return true;
	        }
	 
	        return false;
		}

		public function getEventsByEsrId($esrId){
			$query = "MATCH (esr:Event_Search_Request) WHERE id(esr) = {esrid}
				MATCH (esr)-[:CREATED]->(e:Event) RETURN e";
			$params = [
	            'esrid' => $esrId
	            ];

	        $result = $this->neoClient->sendCypherQuery($query, $params)->getRows();

	        if (null !== $result['e']) {
	            return $result['e'];
	        }
	 
	        return null;
		}

		public function findEventsBySearch($query, $filters){
			$q = $query;
			$userLat = $filters['userLat'];
			$userLong = $filters['userLng'];
			$filterCity = $filters['city'];
			$filterState = $filters['state'];
			$filterDate = $filters['date'];

			$currTime = date("Y-m-d H:i:s"); 

			// Only the query parameter exists
			if($q != $filterCity && isset($q) && !isset($filterState) && !isset($filterDate)){
				$query = "MATCH (e:Event) WHERE e.description =~ {desc} AND e.start_time >= {startdate} RETURN e ORDER BY e.start_time LIMIT 100";
				$params = [
				'startdate' => $currTime,
	            'desc' => '(?i).*'.$q.'.*',
	            'startdate' => $currTime
	            ];
			} 
			// Only the city parameter exists
			else if($q == $filterCity && isset($filterCity) && !isset($filterState) && !isset($filterDate)){
				$query = "MATCH (e:Event) WHERE e.city_name =~ {city} AND e.start_time >= {startdate} RETURN e ORDER BY e.start_time LIMIT 100";
				$params = [
	            'startdate' => $currTime,
	            'city' => '(?i)'.$filterCity,
	            'startdate' => $currTime
	            ];
	            //echo $query."<br><br>";
	            //echo implode(",",$params);
			} 
			// Only the query and city parameter exist
			else if($q != $filterCity && isset($q) && isset($filterCity) && !isset($filterState) && !isset($filterDate)){
				$query = "MATCH (e:Event) WHERE e.city_name =~ {city} AND e.description =~ {desc} AND e.start_time >= {startdate} RETURN e ORDER BY e.start_time LIMIT 100";
				$params = [
				'startdate' => $currTime,
				'desc' => '(?i).*'.$q.'.*',
	            'city' => '(?i)'.$filterCity,
	            'startdate' => $currTime
	            ];
			} 
			// Only the query, city and state parameter exist
			else if($q != $filterCity && isset($q) && isset($filterCity) && isset($filterState) && !isset($filterDate)){
				$query = "MATCH (e:Event) WHERE e.city_name =~ {city} AND e.description =~ {desc} AND e.state_name =~ {state} AND e.start_time >= {startdate} RETURN e ORDER BY e.start_time LIMIT 100";
				$params = [
	            'startdate' => $currTime,
	            'desc' => '(?i).*'.$q.'.*',
	            'city' => '(?i)'.$filterCity,
	            'state' => '(?i)'.$filterState,
	            'startdate' => $currTime
	            ];
			} 
			// Only the query, city, state and date parameter exist
			else if($q != $filterCity && isset($q) && isset($filterCity) && isset($filterState) && isset($filterDate)){
				$query = "MATCH (e:Event) WHERE e.city_name =~ {city} AND e.description =~ {desc} AND e.state_name =~ {state} AND e.start_time >= {startdate} RETURN e ORDER BY e.start_time LIMIT 100";
				$params = [
	            'startdate' => $currTime,
	            'desc' => '(?i).*'.$q.'.*',
	            'city' => '(?i)'.$filterCity,
	            'state' => '(?i)'.$filterState,
	            'startdate' => $filterDate
	            ];
			} 
			// Only the state parameter exists
			else if(!isset($q) && !isset($filterCity) && isset($filterState) && !isset($filterDate)){
				$query = "MATCH (e:Event) WHERE e.state_name =~ {state} AND e.start_time > {startdate} RETURN e ORDER BY e.start_time LIMIT 100";
				$params = [
	            'startdate' => $currTime,
	            'state' => '(?i)'.$filterState
	            ];
			} 
			// Only the state and city parameter exists
			else if($q == $filterCity && isset($filterCity) && isset($filterState) && !isset($filterDate)){
				$query = "MATCH (e:Event) WHERE e.state_name =~ {state} AND e.city_name =~ {city} AND e.start_time > {startdate} RETURN e ORDER BY e.start_time LIMIT 100";
				$params = [
	            'startdate' => $currTime,
	            'state' => '(?i)'.$filterState,
	            'city' => '(?i)'.$filterCity
	            ];
			} 
			// Only the date exists
			else if(!isset($q) && !isset($filterCity) && !isset($filterState) && isset($filterDate)){
				$query = "MATCH (e:Event) WHERE e.start_time > {startdate} RETURN e ORDER BY e.start_time LIMIT 100";
				$params = [
	            'startdate' => $filterState
	            ];
	        }
			// Only the state and date parameter exists
			else if(!isset($q) && !isset($filterCity) && isset($filterState) && isset($filterDate)){
				$query = "MATCH (e:Event) WHERE e.start_time > {startdate} AND e.state_name =~ {state} RETURN e ORDER BY e.start_time LIMIT 100";
				$params = [
	            'startdate' => $filterDate,
	            'state' => '(?i)'.$filterState
	            ];
	        }
	        // Query the last 100 event entries by date
	        else {
	        	$query = "MATCH (e:Event) WHERE e.start_time > {startdate} RETURN e ORDER BY e.start_time LIMIT 100";
				$params = [
	            'startdate' => $currTime
	            ];
	        }

	        $result = $this->neoClient->sendCypherQuery($query, $params)->getRows();

	        if (null != $result && null != $result['e']) {
	            return $result['e'];
	        } else{
	        	return null;
	        }
	 
	        return null;
		}

		/**************************** USER/FOLLOW ************************************/

		public function createFollowUser($userId1, $userId2){
			$query = 'MATCH (user1:User {userId:{userId1}}), (user2:User {userId:{userId2}}) CREATE UNIQUE (user1)-[:FOLLOWS]->(user2)';
			$params = ['userId1' => $userId1, 'userId2' => $userId2];

			$result = $this->neoClient->sendCypherQuery($query, $params)->getResult();

			if(null !== $result){
				return true;
			} else{
				return false;
			}
		}

		public function destroyFollowUser($userId1, $userId2){
			$query = 'MATCH (user1:User {userId:{userId1}}), (user2:User {userId:{userId2}}) MATCH (user1)-[follows:FOLLOWS]->(user2) DELETE follows';
			$params = ['userId1' => $userId1, 'userId2' => $userId2];

			$result = $this->neoClient->sendCypherQuery($query, $params)->getResult();

			if(null !== $result){
				return true;
			} else{
				return false;
			}
		}

		public function getFollowsUser($userId){
			$query = 'MATCH (user:User) WHERE user.userId = {uId}
     					OPTIONAL MATCH (user)-[:FOLLOWS]->(f:User)
     					RETURN collect(f) as followed';
			$parameters = array('uId' => $userId);
			$result = $this->neoClient->sendCypherQuery($query, $parameters);
			$followed = $result->get('followed');
			return $followed;
		}

		public function getFollowingUser($userId){
			$query = 'MATCH (user:User) WHERE user.userId = {uId}
     					OPTIONAL MATCH (f:User)-[:FOLLOWS]->(user)
     					RETURN user, collect(f) as following';
			$parameters = array('uId' => $userId);
			$result = $this->neoClient->sendCypherQuery($query, $parameters);
			$followed = $result->get('following');
			return $followed;
		}

		/**************************** GENRE/FOLLOW ************************************/

		public function createFollowGenre($userId, $genreId){
			$query = 'MATCH (user:User {userId:{uid}}), (g:Genre id(g) = {gid}) CREATE UNIQUE (user)-[:FOLLOWS]->(g)';
			$params = ['uid' => $userId, 'gid' => intval($genreId)];

			$result = $this->neoClient->sendCypherQuery($query, $params)->getResult();

			if(null !== $result){
				return true;
			} else{
				return false;
			}
		}

		public function destroyFollowGenre($userId, $genreId){
			$query = 'MATCH (user:User {userId:{uid}}), (g:Genre id(g) = {gid}) MATCH (user)-[follows:FOLLOWS]->(g) DELETE follows';
			$params = ['uid' => $userId, 'gid' => intval($genreId)];

			$result = $this->neoClient->sendCypherQuery($query, $params)->getResult();

			if(null !== $result){
				return true;
			} else{
				return false;
			}
		}

		public function getFollowsGenre($userId){
			$query = 'MATCH (user:User) WHERE user.userId = {uId}
     					OPTIONAL MATCH (user)-[:FOLLOWS]->(f:Genre)
     					RETURN collect(f) as followed';
			$parameters = array('uId' => $userId);
			$result = $this->neoClient->sendCypherQuery($query, $parameters);
			$followed = $result->get('followed');
			return $followed;
		}

		/**************************** LOCATION/FOLLOW ************************************/

		public function createFollowLocation($userId, $locationId){
			$query = 'MATCH (user:User {userId:{uid}}), (loc:Location id(loc) = {lid}) CREATE UNIQUE (user)-[:FOLLOWS]->(loc)';
			$params = ['uid' => $userId, 'lid' => intval($locationId)];

			$result = $this->neoClient->sendCypherQuery($query, $params)->getResult();

			if(null !== $result){
				return true;
			} else{
				return false;
			}
		}

		public function destroyFollowLocation($userId, $locationId){
			$query = 'MATCH (user:User {userId:{uid}}), (loc:Location id(loc) = {lid}) MATCH (user)-[follows:FOLLOWS]->(loc) DELETE follows';
			$params = ['uid' => $userId, 'lid' => intval($locationId)];

			$result = $this->neoClient->sendCypherQuery($query, $params)->getResult();

			if(null !== $result){
				return true;
			} else{
				return false;
			}
		}

		public function getFollowsLocation($userId){
			$query = 'MATCH (user:User) WHERE user.userId = {uId}
     					OPTIONAL MATCH (user)-[:FOLLOWS]->(f:Location)
     					RETURN collect(f) as followed';
			$parameters = array('uId' => $userId);
			$result = $this->neoClient->sendCypherQuery($query, $parameters);
			$followed = $result->get('followed');
			return $followed;
		}

		/**************************** UTILITY ************************************/

		public function isNodeExistsById($nodeId){
			$query = 'MATCH (node) WHERE id(node) = {nid} RETURN node';
	        $params = [
	        	'nid' => intval($nodeId),
	            ];

	        $result = $this->neoClient->sendCypherQuery($query, $params);

	        if(count($result->getRows()) > 0){
	        	return true;
	        }
	        return false;
		}

		public function isUserExists($email, $username){
			$query = 'MATCH (n) WHERE n.email = {email} OR n.username = {username} RETURN n';
			$parameters = array('email' => $email, 'username' => $username);
			$result = $this->neoClient->sendCypherQuery($query, $parameters);
			return (count($result->getRows()) > 0);
		}

		public function isUserExistsByUserId($userId){
			$query = 'MATCH (n) WHERE n.userId = {userId} RETURN n';
			$parameters = array('userId' => $userId);
			$result = $this->neoClient->sendCypherQuery($query, $parameters);
			return (count($result->getRows()) > 0);
		}

		public function isEventExistsBySourceAndSourceId($sourceName, $sourceId){
			if($sourceName == 'internal'){
				$query = 'MATCH (n:Event) WHERE id(n) = {eid} RETURN n';
				$parameters = array('eid' => intval($sourceId));
				$result = $this->neoClient->sendCypherQuery($query, $parameters);
			} else{
				$query = 'MATCH (n:Event) WHERE n.external_id = {eid} AND n.datasource = {esource} RETURN n';
				$parameters = array('eid' => $sourceId, 'esource' => $sourceName);
				$result = $this->neoClient->sendCypherQuery($query, $parameters);
			}

			return (count($result->getRows()) > 0);
		}

		public function createNodeRelationship($nodeLabel1, $nodeId1, $relType, $nodeLabel2, $nodeId2){
			if($nodeLabel1 == "User"){
				$query = "MATCH (n1:$nodeLabel1) WHERE n1.userId = {nid1} MATCH (n2:$nodeLabel2) WHERE id(n2)= {nid2} CREATE UNIQUE (n1)-[:$relType]->(n2)";
				$params = ['nid1' => $nodeId1, 'nid2' => intval($nodeId2)];
			}
			else if($nodeLabel2 == "User"){
				$query = "MATCH (n1:$nodeLabel1) WHERE id(n1) = {nid1} MATCH (n2:$nodeLabel2) WHERE n2.userId = {nid2} CREATE UNIQUE (n1)-[:$relType]->(n2)";
				$params = ['nid1' => intval($nodeId1), 'nid2' => $nodeId2];
			}
			else if($nodeLabel1 == "User" && $nodeLabel2 == "User"){
				$query = "MATCH (n1:$nodeLabel1) WHERE n1.userId = {nid1} MATCH (n2:$nodeLabel2) WHERE n2.userId = {nid2} CREATE UNIQUE (n1)-[:$relType]->(n2)";
				$params = ['nid1' => $nodeId1, 'nid2' => $nodeId2];
			} else{
				$query = "MATCH (n1:$nodeLabel1) WHERE id(n1) = {nid1} MATCH (n2:$nodeLabel2) WHERE id(n2) = {nid2} CREATE UNIQUE (n1)-[:$relType]->(n2)";
				$params = ['nid1' => intval($nodeId1), 'nid2' => intval($nodeId2)];
			}

			$result = $this->neoClient->sendCypherQuery($query, $params)->getResult();

			if(null !== $result){
				return true;
			} else{
				return false;
			}
		}

		public function destroyNodeRelationship($nodeLabel1, $nodeId1, $relType, $nodeLabel2, $nodeId2){
			if($nodeLabel1 == "User"){
				$query = "MATCH (n1:$nodeLabel1) WHERE n1.userId = {nid1} MATCH (n2:$nodeLabel2) WHERE id(n2) = {nid2} MATCH (n1)-[r:$relType]->(n2) DELETE r";
				$params = ['nl1' => $nodeLabel1, 'nid1' => $nodeId1, 'nl2' => $nodeLabel2, 'nid2' => intval($nodeId2), 'rt' => $relType];
			}
			else if($nodeLabel2 == "User"){
				$query = "MATCH (n1:$nodeLabel1) WHERE id(n1) = {nid1} MATCH (n2:$nodeLabel2) WHERE n2.userId = {nid2} MATCH (n1)-[r:$relType]->(n2) DELETE r";
				$params = ['nl1' => $nodeLabel1, 'nid1' => intval($nodeId1), 'nl2' => $nodeLabel2, 'nid2' => $nodeId2, 'rt' => $relType];
			}
			else if($nodeLabel1 == "User" && $nodeLabel2 == "User"){
				$query = "MATCH (n1:$nodeLabel1) WHERE n1.userId = {nid1} MATCH (n2:$nodeLabel2) WHERE n2.userId = {nid2} MATCH (n1)-[r:$relType]->(n2) DELETE r";
				$params = ['nl1' => $nodeLabel1, 'nid1' => $nodeId1, 'nl2' => $nodeLabel2, 'nid2' => $nodeId2, 'rt' => $relType];
			} else{
				$query = "MATCH (n1:$nodeLabel1) WHERE id(n1) = {nid1} MATCH (n2:$nodeLabel2) WHERE id(n2) = {nid2} MATCH (n1)-[r:$relType]->(n2) DELETE r";
				$params = ['nl1' => $nodeLabel1, 'nid1' => intval($nodeId1), 'nl2' => $nodeLabel2, 'nid2' => intval($nodeId2), 'rt' => $relType];
			}

			$result = $this->neoClient->sendCypherQuery($query, $params)->getResult();

			if(null !== $result){
				return true;
			} else{
				return false;
			}
		}

		public function isEventSearchRequestExist($url){
			if(!isset($url)){
				return null;
			}

			$query = 'MATCH (esr:Event_Search_Request) WHERE esr.url = {url} RETURN id(esr)';
			$params = array('url' => $url);
			$result = $this->neoClient->sendCypherQuery($query, $params)->getRows();

			if(null != $result && null !== $result['id(esr)'][0]){
				return $result['id(esr)'][0];
			} else{
				return null;
			}
		}

		public function insertEventSearchRequest($url, $resultCount){
			if(!isset($url)){
				return null;
			}

			$timestamp = date("Y-m-d H:i:s"); 

			$query = 'CREATE (esr:Event_Search_Request {url: {url}, timestamp: {ts}}) RETURN id(esr)';
			$params = array('url' => $url, 'ts' => $timestamp);
			$result = $this->neoClient->sendCypherQuery($query, $params)->getRows();

			if(null !== $result['id(esr)'][0]){
				return $result['id(esr)'][0];
			} else{
				return null;
			}
		}

	}

?>