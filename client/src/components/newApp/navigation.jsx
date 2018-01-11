import PropTypes from 'prop-types';
import React from 'react';
import { Link } from 'react-router-dom';
import { Menu, Icon ,Badge} from 'antd';
const Navigation = props => {
	
	const links = props.links.map(link => {
			const [url, title, icon] = link;
			return (
				<Menu.Item key={url}>
							
					<Link key={url} to={url}>
						<Icon type={icon} />
						<span>{title}</span>
					</Link>
				
				</Menu.Item>	
			);
		}
	);

	return (
		<Menu 	
			theme="dark" 
			mode="inline" 
			selectedKeys={[props.selected]} 
			selectable={false}
		> 

			{links}		

		</Menu>
	);
};

export default Navigation;

