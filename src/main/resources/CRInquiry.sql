SELECT top 1 Reference
  FROM [dbo].[ServiceLogs]
  where  [UserId] =1 and ServiceName='MOCFirmInfo'
  order by [CreatedOn] Desc