USE ecom_eval_sales_DM;
GO

-- Drop the partitioned view
DROP VIEW IF EXISTS SalesFactWithDateBase;

-- Drop the partitioned table
DROP TABLE IF EXISTS SalesFactPartitioned;

-- Drop the partition scheme
DROP PARTITION SCHEME SalesPartitionScheme;

-- Drop the partition function
DROP PARTITION FUNCTION SalesDatePartitionFunction;

-------------------------------------------------------------------------------

-- Create a partition function for years 2021, 2022, and 2023
CREATE PARTITION FUNCTION SalesDatePartitionFunction (DATE)
AS RANGE RIGHT FOR VALUES ('2021-01-01', '2022-01-01', '2023-01-01');

-- Create Partition file groups
ALTER DATABASE ecom_eval_sales_DM ADD FILEGROUP [FG_sales_Archive];
ALTER DATABASE ecom_eval_sales_DM ADD FILEGROUP [FG_sales_2021];
ALTER DATABASE ecom_eval_sales_DM ADD FILEGROUP [FG_sales_2022];
ALTER DATABASE ecom_eval_sales_DM ADD FILEGROUP [FG_sales_2023];

-- Create Files for file groups
ALTER DATABASE ecom_eval_sales_DM ADD FILE
(NAME = N'Ventes_Archive',
FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\Ventes_Archive.ndf', SIZE = 2048KB) TO FILEGROUP [FG_sales_Archive];

ALTER DATABASE ecom_eval_sales_DM ADD FILE
(NAME = N'Ventes_2021',
FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\Ventes_2021.ndf', SIZE = 2048KB) TO FILEGROUP [FG_sales_2021];

ALTER DATABASE ecom_eval_sales_DM ADD FILE
(NAME = N'Ventes_2022',
FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\Ventes_2022.ndf', SIZE = 2048KB) TO FILEGROUP [FG_sales_2022];

ALTER DATABASE ecom_eval_sales_DM ADD FILE
(NAME = N'Ventes_2023',
FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\Ventes_2023.ndf', SIZE = 2048KB) TO FILEGROUP [FG_sales_2023];

-- Create a partition scheme
CREATE PARTITION SCHEME SalesPartitionScheme
AS PARTITION SalesDatePartitionFunction
TO ([FG_sales_Archive], [FG_sales_2021], [FG_sales_2022], [FG_sales_2023]);

-- Create the partitioned fact table directly from the source data
CREATE TABLE SalesFactPartitioned
(
    SalesID INT,
    Date DATE,
    ProductID INT,
    CustomerID INT,
    ShipperID INT,
    QuantitySold INT,
    TotalAmount DECIMAL(10, 2),
    DiscountAmount DECIMAL(10, 2),
    NetAmount DECIMAL(10, 2),
    PRIMARY KEY (SalesID, Date)  
)
ON SalesPartitionScheme(Date);

-- Insert data into the partitioned fact table
INSERT INTO SalesFactPartitioned (SalesID, Date, ProductID, CustomerID, ShipperID, QuantitySold, TotalAmount, DiscountAmount, NetAmount)
SELECT SalesID, Date, ProductID, CustomerID, ShipperID, QuantitySold, TotalAmount, DiscountAmount, NetAmount
FROM FactSales;

-- Query to check partitioning details
SELECT 
    p.partition_number AS partition_number,
    f.name AS file_group, 
    p.rows AS row_count
FROM sys.partitions p
JOIN sys.destination_data_spaces dds ON p.partition_number = dds.destination_id
JOIN sys.filegroups f ON dds.data_space_id = f.data_space_id
WHERE OBJECT_NAME(OBJECT_ID) = 'SalesFactPartitioned'
ORDER BY partition_number;

