package com.tokengeneration.enums;

/**
 * This enum represents the states a token can have in it's entire life cycle.<br/>
 * <br/>
 * CREATED: When a token is created and ready for the call from a counter.<br/><br/>
 * PROCESSING: When a token is assigned to a counter. A token that is in CREATED or WAITING state can be moved to this state.<br/><br/>
 * ON_HOLD: When serving of a token cannot be done because of any issues.<br/><br/>
 * WAITING: When the condition for the token to be set as ON_HOLD is nullified, the token can be moved to WAITING state.<br/><br/>
 * HELD_BY_COUNTER: When the counter is moved to Hold State while processing the token's request.<br/><br/>
 * CLOSED: When a token is closed.<br/><br/>
 * 
 * @author Naveen Saripalli
 * 
 */
public enum TokenStatus {
	
	CREATED,
	PROCESSING,
	ON_HOLD,
	WAITING,
	HELD_BY_COUNTER,
	CLOSED

}
