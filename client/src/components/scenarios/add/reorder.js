import _ from 'lodash';

export const reorder = (list, startIndex, endIndex) => {
	const result = Array.from(list);
	const [removed] = result.splice(startIndex, 1);
	result.splice(endIndex, 0, removed);
	return result;
};

export const reorderQuoteMap = ({quoteMap,source ,destination}) => {
  console.log(quoteMap);
  const current: Quote[] = [...quoteMap[source.droppableId]];
  const next: Quote[] = [...quoteMap[destination.droppableId]];
  const target: Quote = current[source.index];

  // moving to same list
  if (source.droppableId === destination.droppableId) {
    const reordered: Quote[] = reorder(
      current,
      source.index,
      destination.index,
    );


    const result = {
      ...quoteMap,
      [source.droppableId]: reordered,
    };
    return {
      lists: Object.values(result),
      // not auto focusing in own list
      autoFocusQuoteId: null,
    };
  }

  // moving to different list

  // remove from original
  current.splice(source.index, 1);
  // insert into next
  next.splice(destination.index, 0, target);

  const result: QuoteMap = {
    ...quoteMap,
    [source.droppableId]: current,
    [destination.droppableId]: next,
  };

  return {
    lists: Object.values(result),
    autoFocusQuoteId: target.id,
  };
};
