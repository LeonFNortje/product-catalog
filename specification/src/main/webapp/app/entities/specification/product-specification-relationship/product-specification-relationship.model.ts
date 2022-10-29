import dayjs from 'dayjs/esm';

export interface IProductSpecificationRelationship {
  href?: string | null;
  id: string;
  name?: string | null;
  relationshipType?: string | null;
  validForFrom?: dayjs.Dayjs | null;
  validForTo?: dayjs.Dayjs | null;
  schemaLocation?: string | null;
  type?: string | null;
}

export type NewProductSpecificationRelationship = Omit<IProductSpecificationRelationship, 'id'> & { id: null };
