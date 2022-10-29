export interface IBundledProductSpecification {
  href?: string | null;
  id: string;
  name?: string | null;
  lifecycleStatus?: string | null;
  schemaLocation?: string | null;
  type?: string | null;
}

export type NewBundledProductSpecification = Omit<IBundledProductSpecification, 'id'> & { id: null };
