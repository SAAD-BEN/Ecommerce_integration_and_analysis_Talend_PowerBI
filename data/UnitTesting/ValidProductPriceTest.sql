ALTER PROCEDURE Product.[test ValidPrice]
AS
BEGIN

    -- Act: Query the table for discrepancies in ProductPrice calculation
    DECLARE @incorrectCalculations INT;

    SELECT @incorrectCalculations = COUNT(*)
    FROM [ecom_eval_DW].[dbo].[FactSales]
    WHERE ROUND([TotalAmount] / [QuantitySold], 0) != [ProductPrice];

    -- Assert: Verify that there are no discrepancies in ProductPrice calculation
    EXEC tSQLt.AssertEquals 0, @incorrectCalculations;

END;