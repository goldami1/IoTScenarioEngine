import { getFromApi } from "./appActions";


 const DEBUG = true;

export const endpoints = {
    addCustomer : "addCustomer",
    addVendor   : "addVendor",
    device      : "device",
    vendor      : "vendor",
    vendors     : "vendors",
    scenario    : "scenario",
    login       : "login",
    product     : "product"
}

const API_ = {
    // ip          : "192.168.1.12",
    ip          : "localhost",
    port        : "8080",
    folder      : "Scenario_Engine/webapi",   
}
const API = {
    login       : `http://${API_.ip}:${API_.port}/${API_.folder}/customer`,
    vendor      : `http://${API_.ip}:${API_.port}/${API_.folder}/vendor`,
    customer    : `http://${API_.ip}:${API_.port}/${API_.folder}/customer`,
    addCustomer : `http://${API_.ip}:${API_.port}/${API_.folder}/customer/new`,
    addVendor   : `http://${API_.ip}:${API_.port}/${API_.folder}/vendor/new`,
    device     : `http://${API_.ip}:${API_.port}/${API_.folder}/customer/device`,
    vendors     : `http://${API_.ip}:${API_.port}/${API_.folder}/customer/vendors`,
    product    : `http://${API_.ip}:${API_.port}/${API_.folder}/vendor/product`,
    scenario   : `http://${API_.ip}:${API_.port}/${API_.folder}/customer/scenario`
}


const _API_ = {
    ip          : "localhost",
    port        : "8086",
    folder      : "Scenario_Engine/webapi",   
}
const _API = {
    login       : `http://${_API_.ip}:${_API_.port}/${_API_.folder}/customer`,
    vendor      : `http://${_API_.ip}:${_API_.port}/${_API_.folder}/vendor`,
    customer    : `http://${_API_.ip}:${_API_.port}/${_API_.folder}/customer`,
    addCustomer : `http://${_API_.ip}:${_API_.port}/${_API_.folder}/customer/new`,
    addVendor   : `http://${_API_.ip}:${_API_.port}/${_API_.folder}/vendor/new`,
    device     : `http://${_API_.ip}:${_API_.port}/${_API_.folder}/customer/device`,
    vendors     : `http://${_API_.ip}:${_API_.port}/${_API_.folder}/customer/vendors`,
    product    : `http://${_API_.ip}:${_API_.port}/${_API_.folder}/vendor/product`,
    scenario   : `http://${_API_.ip}:${_API_.port}/${_API_.folder}/customer/scenario`
}

export function api(endpoint) {
    const api = ( typeof DEBUG === 'undefined' ) ? API : _API;
    return api[endpoint];
}
