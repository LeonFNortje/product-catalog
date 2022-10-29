import dayjs from 'dayjs/esm';

export interface IProductSpecificationCharacteristicRelationship {
  href?: string | null;
  id: string;
  name?: string | null;
  relationshipType?: string | null;
  validForFrom?: dayjs.Dayjs | null;
  validForTo?: dayjs.Dayjs | null;
  schemaLocation?: string | null;
  type?: string | null;
}

export type NewProductSpecificationCharacteristicRelationship = Omit<IProductSpecificationCharacteristicRelationship, 'id'> & { id: null };
