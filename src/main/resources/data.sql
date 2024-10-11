insert into tag (tag_name, tag_attribute)
values ('성별', '여성'),
       ('성별', '남성'),
       ('성별', '무관'),
       ('나이', '10대'),
       ('나이', '20대'),
       ('나이', '30대'),
       ('나이', '40대'),
       ('나이', '50대'),
       ('나이', '전연령'),
       ('운동강도', '강'),
       ('운동강도', '중'),
       ('운동강도', '약'),
       ('운동강도', '자유');

insert into member (email, nickname, created_at)
values ('test1@naver.com', 'test1', '2024-10-04T12:30:00'),
       ('test2@naver.com', 'test2', '2024-10-04T12:40:00');

insert into team (team_name, team_description, leader_member_id, max_participants, current_participants, password)
values ('test1 team', 'test1 team description', 1, 8, 2, null);

insert into team_member (member_member_id, team_id)
values (1, 1),
       (2, 1);


insert into exercise (exercise_name, is_deprecated, member_member_id)
values ('test1 User exercise1', false, 1),
       ('test1 User exercise2', false, 1),
       ('test2 User exercise1', false, 2),
       ('test2 User exercise2', false, 2);

insert into exercise_time (start_time, exercise_time, is_active, exercise_id)
values ('2024-10-10 10:10:00', 6600, false, 1),
       ('2024-10-10 10:10:00', 5400, false, 3);

insert into exercise_history (exercise_id, created_at, exercise_history_time)
values (1, '2024-10-09 03:00:10', 5400),
       (2, '2024-10-08 03:00:20', 3600),
       (3, '2024-10-09 03:00:10', 3600),
       (4, '2024-10-08 03:00:20', 5400);



