const address   = "192.168.1.17";
const port      = "8080";
const folder    = "Scenario_Engine_roma/webapi";

const base 		= `http://${address}:${port}/${folder}`;
const customer 	= `${base}/customer`;
const vendor 	= `${base}/vendor`;

export const REST_LOGIN 	    = customer;
export const addCustomer 	    = `${REST_LOGIN}/new`;
export const addvendor 		    = `${vendor}/new`;
export const device 		    = `${customer}/device/`;
export const REST_VENDOR 	    = `${vendor}`;
export const scenario 		    = `${customer}/scenario/`;
export const addScenario        = `${scenario}` 

