--  Comments here are associated with the test.
--  For test case examples, see: http://tsqlt.org/user-guide/tsqlt-tutorial/
ALTER PROCEDURE Date.[test ValidDateFormat]
AS
BEGIN
    -- Act: Query the table for dates with an incorrect format
    DECLARE @incorrectFormatDates INT;
    SELECT @incorrectFormatDates = COUNT(*)
    FROM [ecom_eval_DW].[dbo].[DateDim]
    WHERE TRY_CAST([date] AS DATETIME) IS NULL
    OR FORMAT([date], 'yyyy-MM-dd') != [date];

    -- Assert: Verify that there are no dates with an incorrect format
    EXEC tSQLt.AssertEquals 0, @incorrectFormatDates;

END;