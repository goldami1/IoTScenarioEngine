const address   = "192.168.1.17";
const port      = "8080";
const folder    = "Scenario_Engine/webapi";

const base 		= `http://${address}:${port}/${folder}`;
const customer 	= `${base}/customer`;
const vendor 	= `${base}/vendor`;

export const REST_LOGIN 	    = customer;
export const addCustomer 	    = `${customer}/new`;
export const addvendor 		    = `${vendor}/new`;
export const REST_DEVICES 	    = `${customer}/device`;
export const REST_VENDORS 	    = `${vendor}`;
export const REST_PRODUCTS      = `${vendor}/product`;
export const scenario 		    = `${customer}/scenario/`;
export const addScenario        = `${scenario}` 

