const base 		= "http://192.168.1.17:8080/Scenario_Engine_roma/webapi";
const customer 	= base+"/customer";
const vendor 	= base+"/vendor";

export const REST_LOGIN 	    = customer;
export const addCustomer 	    = `${REST_LOGIN}/new`;
export const addvendor 		    = `${vendor}/new`;
export const device 		    = `${customer}/device/`;
export const REST_VENDOR 	    = `${vendor}`;
export const scenario 		    = `${customer}/scenario/`;
export const addScenario        = `${scenario}` 

