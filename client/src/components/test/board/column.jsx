// @flow
import React, { Component } from 'react';
import styled from 'styled-components';
import { grid, colors, borderRadius } from '../constants';
import { Draggable } from 'react-beautiful-dnd';
import QuoteList from '../primatives/quote-list';
import Title from '../primatives/title';


const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
`;

const Container = styled.div`
  margin: ${grid}px;
  display: flex;
  flex-direction: column;
`;

const Header = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  border-top-left-radius: ${borderRadius}px;
  border-top-right-radius: ${borderRadius}px;
  background-color: ${({ isDragging }) => (isDragging ? colors.blue.lighter : colors.blue.light)};
  transition: background-color 0.1s ease;

  &:hover {
    background-color: ${colors.blue.lighter};
  }
`;


export default class Column extends Component<Props> {
  render() {
    const title = this.props.title;
    const quotes = this.props.quotes;
    return (
      <Draggable draggableId={title}>
        {(provided: DraggableProvided, snapshot: DraggableStateSnapshot) => (
          <Wrapper>
            <Container
              innerRef={provided.innerRef}
              style={provided.draggableStyle}
            >
              <Header isDragging={snapshot.isDragging}>
                <Title
                  isDragging={snapshot.isDragging}
                  {...provided.dragHandleProps}
                >
                  {title}
                </Title>
              </Header>
              <QuoteList
                listId={title}
                quotes={quotes}
                autoFocusQuoteId={this.props.autoFocusQuoteId}
              />
            </Container>
            {provided.placeholder}
          </Wrapper>
        )}

      </Draggable>
    );
  }
}
