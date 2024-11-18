
insert into member (email, nickname, exercise_attendance_date, created_at, is_inactive)
values ('test1@naver.com', 'test1', '0', '2024-10-04T12:30:00', false),
       ('test2@naver.com', 'test2', '0', '2024-10-04T12:40:00', false),
       ('test3@naver.com', 'test3', '0', '2024-10-04T12:40:00', false);

insert into team (team_name, team_description, leader_id, max_participants, current_participants,
                  password)
values ('test1 team', 'test1 team description', 1, 8, 2, null),
       ('test2 team', 'test2 team description', 1, 8, 1, '1234'),
       ('test3 team', 'test3 team description', 3, 8, 1, '1234'),
       ('test4 team', 'test4 team description', 3, 8, 1, '1234');

insert into team_member_mapping (member_id, team_id, is_deprecated)
values (1, 1, false),
       (2, 1, false),
       (1, 3, false);

insert into chatting (team_member_mapping_id, message, created_at)
values (1, '채팅 테스트 1', '2024-10-04T12:30:00'),
       (1, '채팅 테스트 2', '2024-10-04T12:30:02');


insert into team_tag_mapping (team_tag_id, team_id, is_deprecated)
values (1, 1, false),
       (4, 1, false),
       (2, 2, false),
       (5, 2, false),
       (9, 3, false),
       (1, 3, false),
       (2, 3, false),
       (5, 4, false),
       (8, 4, false);


insert into exercise (exercise_name, is_deprecated, member_id)
values ('test1 User exercise1', false, 1),
       ('test1 User exercise2', false, 1),
       ('test2 User exercise1', false, 2),
       ('test2 User exercise2', false, 2);


insert into exercise_time (start_time, exercise_time, is_active, exercise_id)
values ('2024-10-10 10:10:00', 6600, false, 1),
       ('2024-10-10 10:10:00', 5400, false, 3);

insert into exercise_history (exercise_id, exercised_at, exercise_history_time)
values (1, '2024-10-09 03:00:10', 5400),
       (2, '2024-10-08 03:00:20', 3600),
       (3, '2024-10-09 03:00:10', 3600),
       (4, '2024-10-08 03:00:20', 5400);