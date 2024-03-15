INSERT INTO `roles` (id, name) VALUES
	('157a6d1c-3e2a-446d-91ec-2c3c8cea9d99', 'ADMIN'),
	('28e920c8-c37a-4fc2-81d4-b9461cf45a09', 'ROLE_MANAGER'),
	('30b0f1d6-53c9-47a3-b759-e94d2fa583d8', 'IDENTITY_MANAGER');

INSERT INTO `role_allowed_operations` (role_id, operation_id) VALUES
	('28e920c8-c37a-4fc2-81d4-b9461cf45a09', 100),
	('28e920c8-c37a-4fc2-81d4-b9461cf45a09', 101),
	('28e920c8-c37a-4fc2-81d4-b9461cf45a09', 102),
	('28e920c8-c37a-4fc2-81d4-b9461cf45a09', 104),
	('30b0f1d6-53c9-47a3-b759-e94d2fa583d8', 200),
	('30b0f1d6-53c9-47a3-b759-e94d2fa583d8', 201),
	('30b0f1d6-53c9-47a3-b759-e94d2fa583d8', 202),
	('30b0f1d6-53c9-47a3-b759-e94d2fa583d8', 204);

INSERT INTO `identities` (id, username, password, email, first_name, last_name, enabled) VALUES
	('3634060f-a928-4916-82d0-d9c2fa80a962', 'admin', '$2a$10$.yJkPoYlqrBojn66QOMnyuwRk3n8DRybXsJjoDFShvuiwBZ1rWDLq', NULL, NULL, NULL, true),
	('f3284172-ed18-4d2c-a0d3-ee9995f80ed7', 'ned', '$2a$10$0rAQaMFJgjGUNPLbR1ptROd.9hYj7YttJ8eL6kPhjJw/yn8jWJF/q', 'ned@winterfell.com', 'Eddard', 'Stark', false),
	('12b935fd-1dd9-4a1e-b53c-d157b84c7a39', 'stoneheart', '$2a$10$NazDD6z4eHszT69GrL3veuePLsHNI1xZlDL2gApqyFjjscSCLLYfW', 'cat@riverrun.com', 'Catelyn', 'Tully Stark', true),
	('89b6de83-ccc8-4b55-93fa-ae44dbfac243', 'bastard', '$2a$10$W62IDgJ2e6tnyk0vmqN7WezCozUqohAYeWdoxzzeUzw6dZULcoW22', 'lord@nightswatch.org', 'Jon', 'Snow', true),
	('0f6abd7e-4cc4-4d11-a145-d76b9df95341', 'y0ung-wolf', '$2a$10$haXhRzgX3LvHtIQplgCr9elypzzDKRn1kRvOvIr7RYhBCQS2sf5RG', 'wasted@winterfell.com', 'Robb', 'Stark', false),
	('5f669b75-f594-4110-a082-0d79f50d625b', 'alayne.stone', '$2a$10$STYqm4JkLRDXeUMs9Npewe6F8NJJloR75hWaqTRix9CqpAtkPwgd2', 'sansa.s@winterfell.com', 'Sansa', 'Stark', true),
	('c5fc797e-4463-489c-bcff-80ef0f662ca7', 'stickboy', '$2a$10$vsXyL3gzipP4n0B2ZAmcaOCNKsoDe0E1oNKa3d2wELzf1d5bG6CGq', 'blind.beth@faceless.art', 'Arya', 'Stark', true),
	('8ef236e1-c52d-44bc-b874-b377cb41c0d7', 'BranTheBroken', '$2a$10$iTDa4mTg8HoXfpPrzZqDAeC5/KyaQJK5PIWjSxuf.3WXhfZ2pVKUK', 'three.eyed@greensight.art', 'Brandon', 'Stark', true),
	('e6133272-6589-45eb-90f1-e2ddbb9f2c63', 'shaggydog', '$2a$10$px..2BKrItbgMxtjp3wf8unHD5FUQqva2ybAXUlWPJERyD2KMmLZm', 'r.stark@winterfell.com', 'Rickon', 'Stark', true),
	('02ff0a1d-180c-4698-ac28-a189ff0be6b1', 'iron-reek', '$2a$10$hcArgAikD3uQ5ncqLj0uzeSq3gDFiiG7V2N4KuiRsHeTvIrLhR0Ue', 'prince@turncloak.io', 'Theon', 'Greyjoy', true),
	('3b6b2347-b2ad-48e6-8b23-ab64a7f73f2f', 'dragon_mother', '$2a$10$Py8oVWtHQoHGnYPJ9MqVt.AFmmBxdbvLQocO1fGJ9iiH7U4zSnImi', 'mum@dragon.zoo', 'Daenerys', 'Targaryen', true);

INSERT INTO `role_identity_junction` (role_id, identity_id) VALUES
	('157a6d1c-3e2a-446d-91ec-2c3c8cea9d99', '3634060f-a928-4916-82d0-d9c2fa80a962'),
	('28e920c8-c37a-4fc2-81d4-b9461cf45a09', 'f3284172-ed18-4d2c-a0d3-ee9995f80ed7'),
	('30b0f1d6-53c9-47a3-b759-e94d2fa583d8', 'f3284172-ed18-4d2c-a0d3-ee9995f80ed7'),
	('30b0f1d6-53c9-47a3-b759-e94d2fa583d8', '12b935fd-1dd9-4a1e-b53c-d157b84c7a39');
