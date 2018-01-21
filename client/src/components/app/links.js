import { isEmpty } from "lodash";

export default function links(auth) {
	const type = auth.isAuthenticated && !isEmpty(auth.type) ? auth.type : '';
	switch (type) {
		case "enduser":
			return [
				["/devices", "Devices", "appstore-o"],
				["/scenarios", "Scenarios", "fork"]
			];
		case "vendor":
			return [["/products", "Products", "appstore-o"]];
		default:
			return [
				["/login", "Login", "login"],
				["/signup", "Singup", "user-add"],
				["/devices", "Devices", "appstore-o"],
				["/products", "Products", "appstore-o"],
				["/scenarios", "Scenarios", "fork"]
			];
	}
}
