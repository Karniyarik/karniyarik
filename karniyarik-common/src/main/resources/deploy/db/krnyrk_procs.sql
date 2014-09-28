DELIMITER $$

-- Begin Procedures

-- GetParserStatisticsBySourceId
DROP PROCEDURE IF EXISTS `karniyarik`.`prc_GetParserStatisticsBySourceId` $$
CREATE PROCEDURE `karniyarik`.`prc_GetParserStatisticsBySourceId`
(
	IN SourceId INT UNSIGNED
)

BEGIN
SELECT * FROM TBL_PARSER_STATISTICS WHERE FLD_SOURCE_ID = SourceId;
END $$

-- SaveParserStatisticsBySourceId
DROP PROCEDURE IF EXISTS `karniyarik`.`prc_SaveParserStatisticsBySourceId` $$
CREATE PROCEDURE `karniyarik`.`prc_SaveParserStatisticsBySourceId`
(
	IN SourceId 			INT UNSIGNED,
	IN State				INT UNSIGNED,
	IN TotalPageCount		INT UNSIGNED,
	IN ParsedPageCount		INT UNSIGNED,
	IN FileNotFoundCount	INT UNSIGNED,
	IN RemainingPageCount	INT UNSIGNED,
	IN ProductCount			INT UNSIGNED,
	IN ProductMissCount		INT UNSIGNED,
	IN BrandsParsedCount	INT UNSIGNED,
	IN BrandsMissedCount	INT UNSIGNED,
	IN BrandsSkippedCount	INT UNSIGNED,
	IN BreadcrumbsParsedCount	INT UNSIGNED,
	IN BreadcrumbsMissedCount	INT UNSIGNED,
	IN ImagesParsedCount	INT UNSIGNED,
	IN ImagesMissedCount	INT UNSIGNED
)

BEGIN
DELETE FROM TBL_PARSER_STATISTICS WHERE FLD_SOURCE_ID = SourceId;
INSERT INTO TBL_PARSER_STATISTICS
(
	FLD_SOURCE_ID,
	FLD_STATE,
	FLD_TOTAL_PAGE_COUNT,
	FLD_PARSED_PAGE_COUNT,
	FLD_FILE_NOT_FOUND_COUNT,
	FLD_REMAINING_PAGE_COUNT,
	FLD_PRODUCT_COUNT,
	FLD_PRODUCT_MISS_COUNT,
	FLD_BRANDS_PARSED_COUNT,
	FLD_BRANDS_MISSED_COUNT,
	FLD_BRANDS_SKIPPED_COUNT,
	FLD_BREADCRUMBS_PARSED_COUNT,
	FLD_BREADCRUMBS_MISSED_COUNT,
	FLD_IMAGES_PARSED_COUNT,
	FLD_IMAGES_MISSED_COUNT
)
VALUES
(
	SourceId,
	State,
	TotalPageCount,
	ParsedPageCount,
	FileNotFoundCount,
	RemainingPageCount,
	ProductCount,
	ProductMissCount,
	BrandsParsedCount,
	BrandsMissedCount,
	BrandsSkippedCount,
	BreadcrumbsParsedCount,
	BreadcrumbsMissedCount,
	ImagesParsedCount,
	ImagesMissedCount
);
END $$

-- GetFilesToParseBySourceId
DROP PROCEDURE IF EXISTS `karniyarik`.`prc_GetFilesToParseBySourceId` $$
CREATE PROCEDURE `karniyarik`.`prc_GetFilesToParseBySourceId`
(
	IN SourceId INT UNSIGNED
)

BEGIN
SELECT FLD_ID FROM TBL_LINKS WHERE FLD_SOURCE_ID = SourceId AND FLD_PARSED = 0;
END $$

-- DeleteProductsBySourceId
DROP PROCEDURE IF EXISTS `karniyarik`.`prc_DeleteProductsBySourceId` $$
CREATE PROCEDURE `karniyarik`.`prc_DeleteProductsBySourceId`
(
	IN SourceId INT UNSIGNED
)

BEGIN
DELETE product FROM TBL_PRODUCTS product WHERE SourceId = (SELECT FLD_SOURCE_ID FROM TBL_LINKS WHERE FLD_ID = product.FLD_LINK_ID);
UPDATE TBL_LINKS SET FLD_PARSED = 0 WHERE FLD_SOURCE_ID = SourceId;
END $$

-- MarkLinksAsUnparsedBySourceId
DROP PROCEDURE IF EXISTS `karniyarik`.`prc_MarkLinksAsUnparsedBySourceId` $$
CREATE PROCEDURE `karniyarik`.`prc_MarkLinksAsUnparsedBySourceId`
(
	IN SourceId INT UNSIGNED
)

BEGIN
UPDATE TBL_LINKS SET FLD_PARSED = 0 WHERE FLD_SOURCE_ID = SourceId;
END $$

-- GetTotalLinkCountBySourceId
DROP PROCEDURE IF EXISTS `karniyarik`.`prc_GetTotalLinkCountBySourceId` $$
CREATE PROCEDURE `karniyarik`.`prc_GetTotalLinkCountBySourceId`
(
	IN SourceId INT UNSIGNED,
	OUT LinkCount INT UNSIGNED
)

BEGIN
SELECT COUNT(*) INTO LinkCount FROM TBL_LINKS WHERE FLD_SOURCE_ID = SourceId;
END $$

-- GetParsedPageCountBySourceId
DROP PROCEDURE IF EXISTS `karniyarik`.`prc_GetParsedPageCountBySourceId` $$
CREATE PROCEDURE `karniyarik`.`prc_GetParsedPageCountBySourceId`
(
	IN SourceId INT UNSIGNED,
	OUT ParsedPagecount INT UNSIGNED
)

BEGIN
SELECT COUNT(*) INTO ParsedPageCount FROM TBL_LINKS WHERE FLD_SOURCE_ID = SourceId AND FLD_PARSED = 1;
END $$

-- GetSourceIdBySiteName
DROP PROCEDURE IF EXISTS `karniyarik`.`prc_GetSourceIdBySiteName` $$
CREATE PROCEDURE `karniyarik`.`prc_GetSourceIdBySiteName`
(
	IN SiteName VARCHAR(64),
	OUT SourceId INT UNSIGNED
)

BEGIN
SELECT FLD_ID INTO SourceId FROM TBL_SOURCES WHERE FLD_NAME = SiteName;
END $$

-- GetSiteNameBySourceId
DROP PROCEDURE IF EXISTS `karniyarik`.`prc_GetSourceIdBySiteName` $$
CREATE PROCEDURE `karniyarik`.`prc_GetSourceIdBySiteName`
(
	IN SourceId INT UNSIGNED,
	OUT SiteName VARCHAR(64)
)

BEGIN
SELECT FLD_NAME INTO SiteName FROM TBL_SOURCES WHERE FLD_ID = SourceId;
END $$


-- End Procedures

DELIMITER ;