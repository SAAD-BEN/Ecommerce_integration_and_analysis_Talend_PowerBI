-- Index on ProductID
CREATE INDEX IX_FactInventory_ProductID ON [ecom_eval_inventory_DM].[dbo].[FactInventory] ([ProductID]);

-- Index on Date
CREATE INDEX IX_FactInventory_Date ON [ecom_eval_inventory_DM].[dbo].[FactInventory] ([Date]);

