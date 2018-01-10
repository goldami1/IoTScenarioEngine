import React from 'react';


const ContentWrapper = (props) => {
  const style = {
    margin: '40px',
    padding: 40,
    background: '#fff',
  }

  return (
  	<div style={style}>
		{props.children}
	</div>
  );
}

export default ContentWrapper;