import dayjs from 'dayjs/esm';
import { ITargetProductSchema } from 'app/entities/specification/target-product-schema/target-product-schema.model';
import { IResourceSpecificationRef } from 'app/entities/specification/resource-specification-ref/resource-specification-ref.model';
import { IAttachmentRefOrValue } from 'app/entities/specification/attachment-ref-or-value/attachment-ref-or-value.model';
import { IRelatedParty } from 'app/entities/specification/related-party/related-party.model';
import { IServiceSpecificationRef } from 'app/entities/specification/service-specification-ref/service-specification-ref.model';
import { IProductSpecificationRelationship } from 'app/entities/specification/product-specification-relationship/product-specification-relationship.model';
import { IBundledProductSpecification } from 'app/entities/specification/bundled-product-specification/bundled-product-specification.model';
import { IProductSpecificationCharacteristic } from 'app/entities/specification/product-specification-characteristic/product-specification-characteristic.model';

export interface IProductSpecification {
  brand?: string | null;
  description?: string | null;
  href?: string | null;
  id: string;
  name?: string | null;
  isBundle?: boolean | null;
  lastUpdate?: dayjs.Dayjs | null;
  lifecycleStatus?: string | null;
  productNumber?: string | null;
  validForFrom?: dayjs.Dayjs | null;
  validForTo?: dayjs.Dayjs | null;
  version?: string | null;
  schemaLocation?: string | null;
  type?: string | null;
  targetProductSchema?: Pick<ITargetProductSchema, 'id'> | null;
  resourceSpecificationRef?: Pick<IResourceSpecificationRef, 'id'> | null;
  attachmentRefOrValue?: Pick<IAttachmentRefOrValue, 'id'> | null;
  relatedParty?: Pick<IRelatedParty, 'id'> | null;
  serviceSpecificationRef?: Pick<IServiceSpecificationRef, 'id'> | null;
  productSpecificationRelationship?: Pick<IProductSpecificationRelationship, 'id'> | null;
  bundledProductSpecification?: Pick<IBundledProductSpecification, 'id'> | null;
  productSpecificationCharacteristic?: Pick<IProductSpecificationCharacteristic, 'id'> | null;
}

export type NewProductSpecification = Omit<IProductSpecification, 'id'> & { id: null };
