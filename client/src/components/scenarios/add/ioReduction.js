import {productInputReduction} from "../../products/add/ioReduction";


var dev = {
	id:1,
	name:'clock',
	description:'show time',
	img:'some image',
	vendor:'the company',
	endpoint:'',
	

	events:[
		{
			id:1,
			name:'alarm',
			description:'rings on time',
			endpoint:'clock.com/alarm',
			properties:[
				{
					id:1,
					name:'hour',
					description:'enter time',
					type:'discrete',
					options:[
						{
							id:1,
							name:'1'
						},
						{
							id:2,
							name:'2'
						}
					],
					value:''
				},
				{
					id:1,
					name:'am / pm',
					description:'choose am or pm',
					type:'string',
					value:''
				}
			]
		}

	],
	actions:[]
}
export function deviceInputReduction(oldDevice)
{
	var devices = oldDevice.map((device)=> {
		return productInputReduction(device.protoDevice)
	})

	return devices;

}
