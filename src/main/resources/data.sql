insert into team_tag (tag_attribute, tag_name, is_deprecated)
values ('성별', '여성', false),
       ('성별', '남성', false),
       ('나이', '10대', true),
       ('나이', '20대', false),
       ('나이', '30대', false),
       ('나이', '40대', false),
       ('나이', '50대', false),
       ('운동강도', '강', false),
       ('운동강도', '중', false),
       ('운동강도', '약', false);

insert into product (image_url, product_url, name, price, store_name, view_count, is_deprecated)
values ('https://tinyurl.com/25ntsq9k', 'https://tinyurl.com/27sk7wrj', '런닝머신', 883700, 'coupang', 15, false),
       ('https://tinyurl.com/2ylb52aq', 'https://tinyurl.com/2cxw4zxa', '실내자전거', 148800, 'coupang', 5, false),
       ('https://tinyurl.com/28y6jl8d', 'https://tinyurl.com/29m67lsf', '폼롤러', 19800, 'adidas', 15, false),
       ('https://tinyurl.com/2954eoum', 'https://brand.naver.com/adidasfitness/products/10824318636', '요가매트', 24000, 'adidas', 8, false),
       ('https://tinyurl.com/2cpy86bd', 'https://tinyurl.com/2y9fzpw8', '짐볼', 9900, 'body crew', 20, false),
       ('https://tinyurl.com/23oej7er', 'https://tinyurl.com/2bkxjcyd', '요가 밴드', 6600, 'coupang', 11, false),
       ('https://tinyurl.com/28g7dayl', 'https://tinyurl.com/26czhyqs', '아몬드 브리즈 언스위트', 15550, 'coupang', 25, false),
       ('https://tinyurl.com/2cfhbtwo', 'https://tinyurl.com/2379qb7d', '친환경 무농약 샐러드 (정기배송)', 14900, '39파머스', 2, false),
       ('https://tinyurl.com/2b2f3too', 'https://tinyurl.com/2bqzt8gx', '하림 닭가슴살 블랙페퍼 (8매)', 15800, 'coupang', 5, false),
       ('https://tinyurl.com/23l6ngbs', 'https://tinyurl.com/25jjdl9m', '풀무원 순생 나또', 9900, 'coupang', 7, false),
       ('https://tinyurl.com/26hnkcq4', 'https://tinyurl.com/29yko8yd', '코코밥 초코 단백질 쉐이크', 25500, '리빙라이프', 10, false),
       ('https://tinyurl.com/2ye79qd6', 'https://tinyurl.com/2dpzjzzt', '햇반 현미 귀리 곤약밥', 22000, 'G마켓', 8, false);

insert into product_tag (tag_name, is_deprecated)
values ('운동 기구', false),
       ('건강 식품', false);

insert into product_tag_mapping (product_id, product_tag_id, is_deprecated)
values (1, 1, false),
       (2, 1, false),
       (3, 1, false),
       (4, 1, false),
       (5, 1, false),
       (6, 1, false),
       (7, 2, false),
       (8, 2, false),
       (9, 2, false),
       (10, 2, false),
       (11, 2, false),
       (12, 2, false);



