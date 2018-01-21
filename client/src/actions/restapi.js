const base 		= "http://localhost:9090/Scenario_Engine/webapi";
const customer 	= base+"/customer";
const vendor 	= base+"/vendor";

export const login 			= customer;
export const addCustomer 	= `${login}/new`;
export const addvendor 		= `${vendor}/new`"`;
export const device 		= `${customer}/device/`;
export const product 		= `${vendor}/product/`;
export const scenario 		= `${customer}/scenario/`;

