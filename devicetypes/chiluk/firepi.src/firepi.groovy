import org.apache.tools.ant.taskdefs.Definer

/**
 *  firepi
 *
 *  Copyright 2018 Dave Chiluk
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
def getIP() {
	return "192.168.1.61"
}

metadata {
	definition (name: "firepi", namespace: "chiluk", author: "Dave Chiluk") {
		capability "Health Check"
		capability "Indicator"
		capability "Refresh"
		capability "Switch"
	}


	simulator {
		// TODO: define status and reply messages here
	}

	tiles {
		// TODO: define your main and details tiles here
	}
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
	// TODO: handle 'checkInterval' attribute
	// TODO: handle 'DeviceWatch-DeviceStatus' attribute
	// TODO: handle 'indicatorStatus' attribute
	// TODO: handle 'switch' attribute

}

// handle commands
def ping() {
	log.debug "Executing 'ping'"
	// TODO: handle 'ping' command
}

def indicatorWhenOn() {
	log.debug "Executing 'indicatorWhenOn'"
	// TODO: handle 'indicatorWhenOn' command
}

def indicatorWhenOff() {
	log.debug "Executing 'indicatorWhenOff'"
	// TODO: handle 'indicatorWhenOff' command
}

def indicatorNever() {
	log.debug "Executing 'indicatorNever'"
	// TODO: handle 'indicatorNever' command
}

def refresh() {
	log.debug "Executing 'refresh'"
	// TODO: handle 'refresh' command
}

def on() {
	log.debug "Executing 'on'"

	def params = [
		uri: " http://" + IP + ":8080/",
		path "firepi/ON"
	]

	try {
		httpGet(params) { resp ->
			resp.headers.each {
				log.debug "$(it.name) : $(it.value)"
			}
			log.debug "response contentType: ${resp.contentType}"
        	log.debug "response data: ${resp.data}"
		}
	} catch (e) {
		log.error "something went wrong: $e"
	}
}

def off() {
	log.debug "Executing 'off'"
	def params = [
			uri: " http://" + IP + ":8080/",
			path "firepi/OFF"
	]

	try {
		httpGet(params) { resp ->
			resp.headers.each {
				log.debug "$(it.name) : $(it.value)"
			}
			log.debug "response contentType: ${resp.contentType}"
			log.debug "response data: ${resp.data}"
		}
	} catch (e) {
		log.error "something went wrong: $e"
	}
}