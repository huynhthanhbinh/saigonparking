USE [BOOKING]
GO
SET IDENTITY_INSERT [BOOKING_STATUS] ON 

INSERT [BOOKING_STATUS] ([ID], [STATUS], [VERSION]) VALUES (1, N'CREATED', 1)
INSERT [BOOKING_STATUS] ([ID], [STATUS], [VERSION]) VALUES (2, N'ACCEPTED', 1)
INSERT [BOOKING_STATUS] ([ID], [STATUS], [VERSION]) VALUES (3, N'REJECTED', 1)
INSERT [BOOKING_STATUS] ([ID], [STATUS], [VERSION]) VALUES (4, N'CANCELLED', 1)
INSERT [BOOKING_STATUS] ([ID], [STATUS], [VERSION]) VALUES (5, N'FINISHED', 1)
SET IDENTITY_INSERT [BOOKING_STATUS] OFF
GO
