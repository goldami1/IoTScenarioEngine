export function productInputReduction(oldProduct)
{
	
	var newProduct = {};
	newProduct.name 			= oldProduct.name;
	newProduct.description 		= oldProduct.description;
	newProduct.endpoint 		= oldProduct.endPoint;
	newProduct.image 			= oldProduct.picURL;
	newProduct.endPoint 		= oldProduct.endpoint;
	newProduct.actions			= [];
	newProduct.events 			= [];

	oldProduct.actionAndEventList.forEach((ae) => {
		var newAe = {
			name:ae.name,
			description:ae.description,
			endpoint:ae.productEP,
			properties:[]
		}

		var i = 0;
		
		for	( i ; i<ae.supportedParametersName.length ;i++)
		{
			newAe.properties[i] = {
				name:ae.supportedParametersName[i],
				description:ae.paramDesc[i],
				type:ae.types[i],
				max:ae.max[i],
				min:ae.min[i],
				options:ae.supportedValues[i]
			}

		}
		

		if (ae.isEvent) 
		{
			newProduct.events.push(newAe);
		}else
		{
			newProduct.actions.push(newAe);
		}
	});

	return newProduct;
}
