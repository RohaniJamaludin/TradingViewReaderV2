package com.jobpoint.controller;

import com.jobpoint.database.CRUDState;
import com.jobpoint.object.State;

public class StateController {
	
	
	public State getStateByForeignId(int productId) {
		CRUDState crudState = new CRUDState();
		State state = crudState.findByForeignId(productId);
		return state;
	}
	
	public void saveState(int productId) {
		State state = new State();
		state.setProductId(productId);
		state.setTradingId(0);
		
		CRUDState crudState = new CRUDState();
		crudState.insert(state);
	}
	
	public void updateState(State state) {
		CRUDState crudState = new CRUDState();
		crudState.update(state.getId(), state);
	}
	
	public void deleteState(int product_id) {
		CRUDState crudState = new CRUDState();
		crudState.delete("product", product_id);
	}

}
