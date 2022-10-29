import dayjs from 'dayjs/esm';
import { IProductSpecificationCharacteristicRelationship } from 'app/entities/specification/product-specification-characteristic-relationship/product-specification-characteristic-relationship.model';

export interface IProductSpecificationCharacteristic {
  configurable?: boolean | null;
  description?: string | null;
  extensible?: boolean | null;
  id: string;
  isUnique?: boolean | null;
  maxCardinality?: number | null;
  minCardinality?: number | null;
  name?: string | null;
  regex?: string | null;
  validForFrom?: dayjs.Dayjs | null;
  validForTo?: dayjs.Dayjs | null;
  valueType?: string | null;
  schemaLocation?: string | null;
  type?: string | null;
  valueSchemaLocation?: string | null;
  productSpecificationCharacteristicRelationship?: Pick<IProductSpecificationCharacteristicRelationship, 'id'> | null;
}

export type NewProductSpecificationCharacteristic = Omit<IProductSpecificationCharacteristic, 'id'> & { id: null };
