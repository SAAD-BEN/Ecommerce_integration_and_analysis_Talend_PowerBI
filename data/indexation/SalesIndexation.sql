-- Index on ProductID
CREATE INDEX IX_FactSales_ProductID ON [ecom_eval_DW].[dbo].[FactSales] ([ProductID]);

-- Index on Date
CREATE INDEX IX_FactSales_Date ON [ecom_eval_DW].[dbo].[FactSales] ([Date]);

-- Index on CustomerID
CREATE INDEX IX_FactSales_CustomerID ON [ecom_eval_DW].[dbo].[FactSales] ([CustomerID]);

-- Index on ShipperID
CREATE INDEX IX_FactSales_ShipperID ON [ecom_eval_DW].[dbo].[FactSales] ([ShipperID]);
