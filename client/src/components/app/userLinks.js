import { isEmpty } from "lodash";

export default function userLinks(auth) {
	const type = auth.isAuthenticated && !isEmpty(auth.type) ? auth.type : '';
	switch (type) {
		case "enduser":
		case "vendor":
			return [['' ,"Logout", "logout"]];
		default:
			return [
				["/login", "Login", ""],
				["/signup", "Singup", ""],
			];
	}
}
