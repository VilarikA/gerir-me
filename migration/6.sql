ALTER TABLE inventorymovement ADD COLUMN unit BIGINT
ALTER TABLE inventorymovement ADD COLUMN updatedby BIGINT
ALTER TABLE inventorymovement ADD COLUMN createdby BIGINT
CREATE INDEX inventorymovement_unit ON inventorymovement ( unit )