// @flow
import React, { Component } from 'react';
import styled from 'styled-components';
import { Droppable, Draggable } from '../../../src';
import Author from '../primatives/author-item';
import { grid, colors } from '../constants';


const Wrapper = styled.div`
  background-color: ${({ isDraggingOver }) => (isDraggingOver ? 'red' : 'green')};
  display: flex;
  flex-direction: column;
  padding: ${grid}px;
  user-select: none;
  transition: background-color 0.1s ease;
  margin: ${grid}px 0;
`;

const DropZone = styled.div`
  display: flex;
  /*
    Needed to avoid growth in list due to lifting the first item
    Caused by display: inline-flex strangeness
  */
  align-items: start;

  /* stop the list collapsing when empty */
  min-width: 600px;

`;

const ScrollContainer = styled.div`
  overflow: auto;
`;

const Container = styled.div`
  /* flex child */
  flex-grow: 1;

  /* flex parent */
  /* needed to allow width to grow greater than body */
  display: inline-flex;
`;


export default class AuthorList extends Component<Props> {
  renderBoard = (dropProvided: DroppableProvided) => {
    const { listType, quotes } = this.props;

    return (
      <Container>
        <DropZone innerRef={dropProvided.innerRef}>
          {quotes.map((quote: Quote) => (
            <Draggable key={quote.id} draggableId={quote.id} type={listType}>
              {(dragProvided: DraggableProvided, dragSnapshot: DraggableStateSnapshot) => (
                <div>
                  <Author
                    author={quote.author}
                    provided={dragProvided}
                    snapshot={dragSnapshot}
                    autoFocus={this.props.autoFocusQuoteId === quote.id}
                  />
                  {dragProvided.placeholder}
                </div>
            )}
            </Draggable>
          ))}
          {dropProvided.placeholder}
        </DropZone>
      </Container>
    );
  }

  render() {
    const { listId, listType, internalScroll } = this.props;

    return (
      <Droppable droppableId={listId} type={listType} direction="horizontal">
        {(dropProvided: DroppableProvided, dropSnapshot: DroppableStateSnapshot) => (
          <Wrapper isDraggingOver={dropSnapshot.isDraggingOver}>
            {internalScroll ? (
              <ScrollContainer>
                {this.renderBoard(dropProvided)}
              </ScrollContainer>
            ) : (
              this.renderBoard(dropProvided)
            )}
          </Wrapper>
        )}
      </Droppable>
    );
  }
}
