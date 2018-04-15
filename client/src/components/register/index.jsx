import React, { Component } from "react";
import RegisterForm from "./register_form";
import CardWrapper from "../common/CardWrapper";

const RegisterPage = () => {
	return (
		<CardWrapper size="medium">
			<RegisterForm />
		</CardWrapper>
	);
};

export default RegisterPage;
