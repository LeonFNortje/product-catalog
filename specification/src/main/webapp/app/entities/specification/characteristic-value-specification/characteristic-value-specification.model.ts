import dayjs from 'dayjs/esm';
import { IProductSpecificationCharacteristicRelationship } from 'app/entities/specification/product-specification-characteristic-relationship/product-specification-characteristic-relationship.model';

export interface ICharacteristicValueSpecification {
  id: number;
  isDefault?: boolean | null;
  rangeInterval?: string | null;
  regex?: string | null;
  unitOfMeasure?: string | null;
  validForFrom?: dayjs.Dayjs | null;
  validForTo?: dayjs.Dayjs | null;
  valueType?: string | null;
  schemaLocation?: string | null;
  type?: string | null;
  productSpecificationCharacteristicRelationship?: Pick<IProductSpecificationCharacteristicRelationship, 'id'> | null;
}

export type NewCharacteristicValueSpecification = Omit<ICharacteristicValueSpecification, 'id'> & { id: null };
