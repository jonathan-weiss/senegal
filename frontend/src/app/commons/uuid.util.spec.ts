import { UuidUtil } from './uuid.util';

describe('UuidUtil', () => {

  it('should wrap a uuid string into a UuidTO', () => {
    const exampleUuid = 'd82e2672-2fe9-4f5a-9697-8297d3b81d3b';
    const uuidTO = UuidUtil.createFromString(exampleUuid);
    expect(uuidTO.uuid).toEqual(exampleUuid);
  });

  it('should generate different uuids ', () => {
    const uuidTO1 = UuidUtil.generateNewUuid();
    const uuidTO2 = UuidUtil.generateNewUuid();
    expect(uuidTO1.uuid).not.toEqual(uuidTO2.uuid);
  });

  it('should compare two uuids that are equally to true', () => {
    const exampleUuid1 = UuidUtil.createFromString('d82e2672-2fe9-4f5a-9697-8297d3b81d3b');
    const exampleUuid2 = UuidUtil.createFromString('d82e2672-2fe9-4f5a-9697-8297d3b81d3b');
    expect(UuidUtil.isEqual(exampleUuid1, exampleUuid2)).toBeTruthy();
  });

  it('should compare two uuids to falsy if they are not equal', () => {
    const exampleUuid1 = UuidUtil.createFromString('d82e2672-2fe9-4f5a-9697-8297d3b81d3b');
    const exampleUuid2 = UuidUtil.createFromString('c34e2672-2fe9-4f5a-9697-8297d3b81d3b');
    expect(UuidUtil.isEqual(exampleUuid1, exampleUuid2)).toBeFalse();
  });

});
