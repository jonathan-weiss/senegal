import { CollectionsUtil } from './collections.util';

describe('CollectionsUtil', () => {
  const date = new Date(2002, 11, 31, 15, 37, 48);

  it('should remove all elements from a list', () => {
    const myArray = ['foo', 'bar', 'baz'];
    expect(myArray.length).toEqual(3);
    CollectionsUtil.clearAll(myArray);
    expect(myArray.length).toEqual(0);
  });

  it('should append the second array to the first', () => {
    const myArray = ['foo', 'bar', 'baz'];
    const mySecondArray = ['alpha', 'beta'];

    CollectionsUtil.addAll(myArray, mySecondArray);
    expect(myArray).toEqual(['foo', 'bar', 'baz', 'alpha', 'beta']);
  });

  it('should filter distinct elements from array', () => {
    const myArray = [
      { id: 0, name: 'Zero' },
      { id: 1, name: 'One' },
      { id: 2, name: 'Two' },
      { id: 0, name: 'Zero Two' },
      { id: 1, name: 'One Two' },
    ];

    const distinctArray = CollectionsUtil.distinct(myArray, (e) => e.id);
    expect(distinctArray).toEqual(
      [
        { id: 0, name: 'Zero' },
        { id: 1, name: 'One' },
        { id: 2, name: 'Two' },
      ]
    );
  });
});
